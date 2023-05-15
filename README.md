README File:

This project contains a single test named aws_Mass_Test. This test:
1. fetches data from all S3 JSON files
2. consolidates all mass/year data into single HashMap
3 calculates Average for each date i.e. for 2009-01-01 we found 1514 values (from multiple json files) and calculated the average as 1505.205

All dates/Times and their respective averages are written into a csv file named output.csv



Analomalies Identified in this test:
1. Year tag as 1975 only
2. Year tag as -35
3. Year tag as January
4. No year tag
5. No mass tag
6. malformed JSON File (0042.json) due to string ending character not found.

TO BE IMPLEMENTED
To have automation tests for above scenarios as well, we will need to implement Schema validation and have a seperate test to make sure that data contained in containers is according to the set standards


TO Execute:

This is a maven / TestNG Project. Simply load the project in InteliJ (dependencies will be resolved by POM.xml). Run the aws_autoamtion_test test case available under src/test/java folder OR run command maven clean test from command line at the root of directory (Maven needs to be installed on machine). Also, aws configure must be done first so that files can be fetched during the execution.
