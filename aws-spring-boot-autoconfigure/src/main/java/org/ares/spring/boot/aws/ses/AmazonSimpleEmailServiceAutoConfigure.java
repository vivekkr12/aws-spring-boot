package org.ares.spring.boot.aws.ses;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsync;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceAsyncClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.ares.spring.boot.aws.AwsProperties;
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
        return AmazonSimpleEmailServiceClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    @ConditionalOnMissingBean
    public AmazonSimpleEmailServiceAsync amazonSimpleEmailServiceAsync() {
        return AmazonSimpleEmailServiceAsyncClientBuilder.standard().withRegion(awsProperties.getRegion())
                .withCredentials(awsCredentialsProvider).build();
    }
}
