package org.ares.spring.boot.aws.sns;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSAsync;
import com.amazonaws.services.sns.AmazonSNSAsyncClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
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
@ConditionalOnClass(AmazonSNS.class)
@EnableConfigurationProperties(AwsProperties.class)
public class AmazonSNSAutoConfigure {

    private static final Log logger = LogFactory.getLog(AmazonSNSAutoConfigure.class);

    private AwsProperties awsProperties;
    private AWSCredentialsProvider awsCredentialsProvider;

    @Autowired
    public AmazonSNSAutoConfigure(AwsProperties awsProperties, AWSCredentialsProvider awsCredentialsProvider) {
        this.awsProperties = awsProperties;
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSNS amazonSNS() {
        logger.info("Creating bean AmazonSNS in the region: " + awsProperties.getRegion());
        return AmazonSNSClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSNSAsync amazonSNSAsync() {
        logger.info("Creating bean AmazonSNSAsync in the region: " + awsProperties.getRegion());
        return AmazonSNSAsyncClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }
}
