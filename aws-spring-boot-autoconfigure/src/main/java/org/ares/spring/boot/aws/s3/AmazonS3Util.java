package org.ares.spring.boot.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
@ConditionalOnBean(AmazonS3.class)
public class AmazonS3Util {

    private AmazonS3 amazonS3;

    @Autowired
    AmazonS3Util(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    /**
     * Downloads the file from S3 with the given s3 key in current directory.
     * File name is resolved from the S3 key and same as is present in the bucket.
     * Replaces the file in current directory if already exists
     * S3 key: bar.txt -> File name: bar.txt
     * S3 key: foo/bar.txt -> File Name: bar.txt
     * @param bucket
     * @param key
     * @throws IOException
     */
    public void downloadFileFromS3(String bucket, String key) throws IOException {
        File downloadedFile = new File(key.substring(0, key.lastIndexOf("/") + 1));
        downloadFileFromS3(bucket, key, downloadedFile);
    }

    /**
     * Downloads the file from S3 with the given S3 key.
     * @param bucket
     * @param key
     * @param outFile
     * @throws IOException
     */
    public void downloadFileFromS3(String bucket, String key, File outFile) throws IOException {
        S3Object object = amazonS3.getObject(bucket, key);
        try(S3ObjectInputStream objectContent = object.getObjectContent()) {
            Files.copy(objectContent, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void uploadMultiPartFile(String bucket, String key, File file, boolean waitForCompletion) throws InterruptedException {
        TransferManager transferManager = TransferManagerBuilder.standard().withS3Client(amazonS3).build();
        Upload upload = transferManager.upload(bucket, key, file);
        if(waitForCompletion) {
            upload.waitForCompletion();
        }
    }

}
