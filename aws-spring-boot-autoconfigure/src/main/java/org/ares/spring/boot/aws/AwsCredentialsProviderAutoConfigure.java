package org.ares.spring.boot.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
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

    private AwsProperties awsProperties;

    @Autowired
    public AwsCredentialsProviderAutoConfigure(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public AWSCredentials awsCredentials() throws AwsBeanCreationException {
        String accessKey = awsProperties.getAccessKey();
        String secretKey = awsProperties.getSecretKey();

        if(accessKey == null || accessKey.isEmpty()) {
            throw new AwsBeanCreationException("AWS accessKey is missing");
        }
        if(secretKey == null || secretKey.isEmpty()) {
            throw new AwsBeanCreationException("AWS secretKey is missing");
        }

        return new BasicAWSCredentials(accessKey, secretKey);
    }

    @Bean
    @ConditionalOnMissingBean
    public AWSCredentialsProvider awsCredentialsProvider() throws AwsBeanCreationException {
        return new AWSStaticCredentialsProvider(awsCredentials());
    }
}
