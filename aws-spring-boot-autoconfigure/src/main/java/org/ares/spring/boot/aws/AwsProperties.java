package org.ares.spring.boot.aws;

import com.amazonaws.regions.Regions;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "aws.spring")
public class AwsProperties {

    private static final Regions DEFAULT_REGIONS = Regions.US_EAST_1;

    private String accessKey;
    private String secretKey;
    private Regions region;

    @PostConstruct
    private void initDefaults() {
        region = region != null ? region : DEFAULT_REGIONS;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Regions getRegion() {
        return region;
    }

    public void setRegion(Regions region) {
        this.region = region;
    }
}
