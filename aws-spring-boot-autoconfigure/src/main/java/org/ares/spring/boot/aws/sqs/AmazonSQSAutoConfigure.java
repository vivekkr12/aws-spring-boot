package org.ares.spring.boot.aws.sqs;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
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
@ConditionalOnClass(AmazonSQS.class)
@EnableConfigurationProperties(AwsProperties.class)
public class AmazonSQSAutoConfigure {

    private static final Log logger = LogFactory.getLog(AmazonSQSAutoConfigure.class);

    private AwsProperties awsProperties;
    private AWSCredentialsProvider awsCredentialsProvider;

    @Autowired
    public AmazonSQSAutoConfigure(AwsProperties awsProperties, AWSCredentialsProvider awsCredentialsProvider) {
        this.awsProperties = awsProperties;
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSQS amazonSQS() {
        logger.info("Creating bean AmazonSQS in the region: " + awsProperties.getRegion());
        return AmazonSQSClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSQSAsync amazonSQSAsync() {
        logger.info("Creating bean AmazonSQSAsync in the region: " + awsProperties.getRegion());
        return AmazonSQSAsyncClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }
}
