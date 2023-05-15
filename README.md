# README File

## Project Description

This project is focused on the implementation of a single test named `aws_Mass_Test`. The test performs data processing operations on JSON files stored in AWS S3 buckets. The goal is to fetch data from multiple S3 JSON files, consolidate the mass/year data, calculate averages for each date, and output the results to a CSV file.

## Test Operations

1. Data Retrieval:
   - The test fetches data from various S3 JSON files. These files contain mass/year data for different dates and times.
   - The data retrieval process involves accessing the S3 bucket, downloading the JSON files, and extracting the necessary information for further processing.

2. Data Consolidation:
   - The test consolidates the mass/year data into a single `HashMap`. This data structure is used to store the values associated with each date.
   - The consolidation process involves iterating through the retrieved JSON files, extracting the mass/year data, and populating the `HashMap` accordingly.

3. Average Calculation:
   - The test calculates the average for each date in the consolidated `HashMap`.
   - For example, if the test retrieves 1514 values from multiple JSON files for the date `2009-01-01`, it calculates the average as `1505.205`.
   - The average calculation is performed by summing up all the values for a specific date and dividing the sum by the total count of values.

4. Output Generation:
   - All dates and their respective averages are written to a CSV file named `output.csv`.
   - The CSV file contains two columns: "Date/Time" and "Average".
   - Each row represents a date/time entry along with its calculated average.

## Identified Anomalies

During the testing process, the following anomalies were identified:

1. Year tag as `1975` only:
   - Some JSON files have their year tag set to `1975` consistently, indicating a potential data issue.

2. Year tag as `-35`:
   - Certain JSON files have their year tag set to `-35`, which is an invalid value and requires investigation.

3. Year tag as `January`:
   - In some JSON files, the year tag is incorrectly set as `January`, suggesting a data entry error or formatting issue.

4. Missing year tag:
   - There are instances where the year tag is missing from the JSON files, which hampers the accurate processing of the data.

5. Missing mass tag:
   - In some cases, the mass tag is not present in the JSON files, making it impossible to retrieve the required information.

6. Malformed JSON File (`0042.json`):
   - The JSON file `0042.json` is malformed due to the absence of the string ending character.
   - This issue needs to be addressed to ensure proper parsing and processing of the JSON data.

## To be Implemented

To enhance the testing process and handle the identified anomalies, the following steps should be implemented:

1. Schema Validation:
   - Implement schema validation to ensure that the data contained in the JSON files adheres to the expected structure and format.
   - Schema validation helps detect and report any inconsistencies, missing fields, or incorrect data types in the JSON files.

2. Separate Test for Data Validation:
   - Create a separate test to validate the data against the set standards and requirements.
   - This test should check if the JSON files contain all the necessary tags, ensure the values are within acceptable ranges, and identify any outliers or inconsistencies.

## Execution Instructions

To execute the `aws_Mass_Test` test, please follow the steps outlined below:

### Prerequisites

1. Ensure that you have Maven installed on your machine. If Maven is not installed, follow the official Maven installation guide for your operating system.

2. Make sure you have set up the AWS CLI and configured it with valid credentials to access the S3 bucket containing the JSON files. If you haven't done this yet, refer to the AWS CLI documentation for instructions on installation and configuration.

### Test Execution Steps

1. Clone the project repository from the designated source or download the project files to your local machine.

2. Open IntelliJ IDEA or your preferred IDE.

3. Import the project into your IDE by selecting the project's root directory.

4. The IDE will automatically resolve the project dependencies based on the information provided in the `POM.xml` file. Wait for the dependencies to be downloaded and resolved.

5. Once the dependencies are successfully resolved, navigate to the test case file `aws_Mass_Test.java` located under the `src/test/java` directory.

6. Right-click on the test case file and select the "Run" option to execute the test.

   - Alternatively, you can execute the test from the command line using Maven:
     - Open a command prompt or terminal.
     - Navigate to the root directory of the project where the `pom.xml` file is located.
     - Run the following command:
       ```
       mvn clean test
       ```

7. During the test execution, the test will fetch data from the S3 JSON files, consolidate the mass/year data, calculate averages for each date, and generate the output in the form of a CSV file named `output.csv`.

8. After the test execution completes, you can find the generated `output.csv` file in the project directory.

   - The `output.csv` file will contain two columns: "Date/Time" and "Average".
   - Each row represents a date/time entry along with its calculated average.

9. Review the test execution results, including any anomalies or errors that were identified during the test run.
