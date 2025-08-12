//package com.modernadev.Moderna.Home.service;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//@Service
//@Slf4j
//public class AwsS3Service {
//
//    private final String bucketName = "";
//
//    @Value("${aws.s3.access}")
//    private String awsS3AccessKey;
//
//    @Value("${aws.s3.secrete}")
//    private String awsS3SecreteKey;
//
//    public String saveImageToS3(MultipartFile photo){
//        try {
//            String s3FileName = photo.getOriginalFilename();
//
//            //Create a credentials using the access and secrete key
//            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);
//
//            // Create an S3 Client with config credentials and regions
//            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
//                    .withRegion(Regions.US_EAST_2)
//                    .build();
//
//            // Get input stream from photo
//            InputStream inputStream = photo.getInputStream();
//            // Set metadata for the object
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(photo.getContentType());
//            // Create a put request to upload the image s3
//            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, objectMetadata);
//            s3Client.putObject(putObjectRequest);
//
//            return "https://" + bucketName + ".s3.us-east-2.amazonaws/" + s3FileName;
//        }catch (IOException ex){
//            ex.printStackTrace();
//            throw  new RuntimeException("Unable to save image:" +ex.getMessage());
//        }
//    }
//}
