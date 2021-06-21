package org.example;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class App
{
    private static String bucketName = "2021-06-21";
    private static String keyName = "GoogleGnome";
    private static String uploadFileName = "D:\\aiconix\\docs\\Google_Gnome.wav";

    private static String accessKey = "REPLACE_THIS";
    private static String secretKey = "REPLACE_THIS";
    private static String namespace = "REPLACE_THIS";
    private static String region = "eu-frankfurt-1";

    public static void main( String[] args ) {
        AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(new BasicAWSCredentials(
                accessKey,
                secretKey));

        String endpoint = String.format("%s.compat.objectstorage.%s.oraclecloud.com",namespace,region);
        AwsClientBuilder.EndpointConfiguration endpointConfiguration = new AwsClientBuilder.EndpointConfiguration(endpoint, region);
        AmazonS3 client = AmazonS3Client.builder()
                .standard()
                .withCredentials(credentials)
                .withEndpointConfiguration(endpointConfiguration)
                .disableChunkedEncoding()
                .enablePathStyleAccess()
                .build();

        try {
            System.out.println("Uploading a new object to Oracle from a file\n");
            File file = new File(uploadFileName);
            // Upload file
            client.putObject(new PutObjectRequest(bucketName, keyName, file));

            client.listBuckets();

            System.out.println("File uploaded to Oracle\n");
        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " + "means your request made it "
                    + "to Oracle Cloud, but was rejected with an error response" + " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());

        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " + "means the client encountered " + "an internal error while trying to "
                    + "communicate with Oracle Cloud, " + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}
