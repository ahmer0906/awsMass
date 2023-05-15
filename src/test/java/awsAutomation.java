
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


public class awsAutomation {
    private Map<String, List<Double>> listData = new HashMap<>();


    @Test
    public void aws_Mass_Test() throws IOException {
        String indFile = "";
        //Setup AWS Bucket Access
        String bucket_name = "majorly-meteoric";
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.DEFAULT_REGION).build();


        ListObjectsV2Result result = s3.listObjectsV2(bucket_name);
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        //Traverse all files in S3 bucket
        for (S3ObjectSummary os : objects) {
            S3Object o = s3.getObject(bucket_name, os.getKey());

            //read individual S3 file from bucket
            indFile = processIndividualFile(o);
            if (!os.getKey().endsWith(".csv")) {
                try {
                    Object jsonData = new JSONParser().parse(indFile);
                    addFileToDataStore(jsonData);
                }
                catch (Exception ex) {
                    System.out.println(os.getKey() + " file data invalid for json Parsing");
                }
            }


        }
        calculateAvgAndPrint();
    }

    //***********************************************
    //**************Function to read individual file from S3
    //***********************************************
    private static String processIndividualFile(S3Object o) {
        StringBuilder sb = new StringBuilder();
        String line = "";
        S3ObjectInputStream s3is = o.getObjectContent();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(s3is, StandardCharsets.UTF_8));
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            s3is.close();
        }
        catch (Exception ex) {
            System.out.println("Error in reading File");
        }
        return sb.toString();
    }

    //***********************************************
    //**************Function to add individual file's jsonData into consolidated data hashMap
    //***********************************************
    private void addFileToDataStore(Object jsonData) {
        String mass = "";
        String year = "";

        if (jsonData!=null) {
            //Converts file data to JSON
            JSONArray jsonDataArray = (JSONArray) jsonData;

            //Traverse all containers within the JSONObject
            for (int i=0;i<jsonDataArray.size();i++) {
                if (((JSONObject)jsonDataArray.get(i)).get("mass")!=null)
                    mass = ((JSONObject)jsonDataArray.get(i)).get("mass").toString();
                else
                    mass = null;
                if (((JSONObject)jsonDataArray.get(i)).get("year")!=null)
                    year = ((JSONObject)jsonDataArray.get(i)).get("year").toString();
                else
                    year = null;

                //IF both mass and date exist, add into dataSet Hashmap
                if (mass!=null && year != null) {
                    List<Double> singleEntry = listData.get(year);
                    if (singleEntry==null)
                        singleEntry = new ArrayList<>();
                    singleEntry.add(Double.parseDouble(mass));
                    listData.put(year,singleEntry);
                }
                System.out.println(mass +"," +year);

            }
        }
    }

    //***********************************************
    //**************Function to calculate Average and write to output file
    //***********************************************
    public void calculateAvgAndPrint() throws IOException {
        //Ouput File setup
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.csv"));
        //Header in output file
        writer.write("Time,Mass(Average)");
        writer.newLine();
        for (Map.Entry<String,List<Double>> mapElement : listData.entrySet()) {
            //Write each day's average to output file
            System.out.println(mapElement.getKey()+","+mapElement.getValue().stream().mapToDouble(a -> a).average().getAsDouble());
            writer.write(mapElement.getKey().replace(","," ")+","+mapElement.getValue().stream().mapToDouble(a -> a).average().getAsDouble());
            writer.newLine();
        }
        writer.close();
    }
}

