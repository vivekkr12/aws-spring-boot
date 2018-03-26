package org.ares.spring.boot.aws.s3;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ares.spring.boot.aws.credentials.AwsProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(AmazonS3.class)
@EnableConfigurationProperties(AwsProperties.class)
public class AmazonS3AutoConfigure {

    private static final Log logger = LogFactory.getLog(AmazonS3AutoConfigure.class);

    private AWSCredentialsProvider awsCredentialsProvider;
    private AwsProperties awsProperties;

    @Autowired
    public AmazonS3AutoConfigure(AWSCredentialsProvider awsCredentialsProvider, AwsProperties awsProperties) {
        this.awsCredentialsProvider = awsCredentialsProvider;
        this.awsProperties = awsProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonS3 amazonS3() {
        logger.info("Creating bean AmazonS3 in the region: " + awsProperties.getRegion());
        return AmazonS3ClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }
}
