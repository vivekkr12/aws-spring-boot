package org.ares.spring.boot.aws.s3;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AmazonS3AutoConfigureTest {

    private static final String PREFIX = "aws.spring.";

    private final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    @Before
    public void init() {
        EnvironmentTestUtils.addEnvironment(this.context, PREFIX + "initialize:false");
    }

    @After
    public void restore() {

    }

    @Test
    public void testAmazonS3Exists() {
        this.context.register(AmazonS3AutoConfigure.class);
        EnvironmentTestUtils.addEnvironment(this.context, PREFIX + "url:jdbc:h2:mem:testdb");
    }

}