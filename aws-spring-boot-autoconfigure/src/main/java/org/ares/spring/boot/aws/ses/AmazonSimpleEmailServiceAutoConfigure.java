package org.ares.spring.boot.aws.ses;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
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
@ConditionalOnClass(AmazonSimpleEmailService.class)
@EnableConfigurationProperties(AwsProperties.class)
public class AmazonSimpleEmailServiceAutoConfigure {

    private static final Log logger = LogFactory.getLog(AmazonSimpleEmailServiceAutoConfigure.class);

    private AwsProperties awsProperties;
    private AWSCredentialsProvider awsCredentialsProvider;

    @Autowired
    public AmazonSimpleEmailServiceAutoConfigure(AwsProperties awsProperties, AWSCredentialsProvider awsCredentialsProvider) {
        this.awsProperties = awsProperties;
        this.awsCredentialsProvider = awsCredentialsProvider;
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSimpleEmailService amazonSimpleEmailService() {
        logger.info("Creating bean AmazonSimpleEmailService in the region: " + awsProperties.getRegion());
        return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSimpleEmailServiceAsync amazonSimpleEmailServiceAsync() {
        logger.info("Creating bean AmazonSimpleEmailServiceAsync in the region: " + awsProperties.getRegion());
        return AmazonSimpleEmailServiceAsyncClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }
}
