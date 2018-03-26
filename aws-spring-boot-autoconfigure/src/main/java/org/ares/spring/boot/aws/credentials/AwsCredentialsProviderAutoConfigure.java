package org.ares.spring.boot.aws.credentials;

import com.amazonaws.auth.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ares.spring.boot.aws.AwsBeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass({AWSCredentials.class, AWSCredentialsProvider.class})
@EnableConfigurationProperties(AwsProperties.class)
public class AwsCredentialsProviderAutoConfigure {

    private static final Log logger = LogFactory.getLog(AwsCredentialsProviderAutoConfigure.class);

    private AwsProperties awsProperties;

    @Autowired
    public AwsCredentialsProviderAutoConfigure(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AWSCredentialsProvider awsCredentialsProvider() throws AwsBeanCreationException {

        String accessKey = awsProperties.getAccessKey();
        String secretKey = awsProperties.getSecretKey();

        if(accessKey != null && secretKey == null) {
            throw new AwsBeanCreationException("AWS secretKet must be provided if accessKey is provided");
        }

        if(accessKey == null && secretKey != null) {
            throw new AwsBeanCreationException("AWS accessKey must be provided if secretKey is provided");
        }

        if(accessKey !=null && secretKey != null) {
            logger.info("Using the AWS Credentials found in application.[properties/yml]");
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
            return new AWSStaticCredentialsProvider(credentials);
        }
        return new DefaultAWSCredentialsProviderChain();
    }
}
