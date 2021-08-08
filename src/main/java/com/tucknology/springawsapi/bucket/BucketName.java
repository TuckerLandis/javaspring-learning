package com.tucknology.springawsapi.bucket;

public enum BucketName {

    PROFILE_IMAGE("aws-spring-bucket");

    private final String bucketName;


    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
