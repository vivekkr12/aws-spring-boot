# AWS Spring Boot Autoconfigure and Starter

Often when working with AWS and Spring using the [aws-java-sdk](https://aws.amazon.com/sdk-for-java/), we need to create 
clients for different services. This autoconfigure module when included in the pom, will create the required clients for
the AWS services and put it in bean factory ready for injection.

There is already a project [spring-cloud-aws](https://github.com/spring-cloud/spring-cloud-aws) from Spring under the
Spring Cloud umbrella. However, for some people that can be an overwhelming amount of abstraction and would like to 
work with  the native SDK. This can be useful to those people.

AWS credential management works the same way as the 
[native SDK](https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html).

Region selection takes place with the following precedence:
1. Region configured in application.properties / yml file
2. If running on AWS (EC2 / Fargate) then, the region of the EC2 instance / ECS Cluster
3. Default [AWS Region](https://docs.aws.amazon.com/AWSJavaSDK/latest/javadoc/com/amazonaws/regions/Regions.html#DEFAULT_REGION)
