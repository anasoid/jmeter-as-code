# Write Jmeter tests as code.

An API that give access t full Jmeter feature as code, All designed object in GUI can be written as code.

![Build & Test](https://github.com/anasoid/jmeter-as-code/actions/workflows/main.yml/badge.svg)
![Audit](https://github.com/anasoid/jmeter-as-code/actions/workflows/audit.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=alert_status)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=coverage)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=ncloc)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=security_rating)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)

### Usage example
A build script example:
````java
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addChild(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group")
                    .addChild(
                        HTTPSamplerProxyWrapper.builder()
                            .withName("HTTP Request")
                            .withDomain("www.anasoid.org")
                            .withPath("")
                            .build())
                    .build())
            .build();
            
  ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
 
  applicationTest.run();
  //OR
  applicationTest.toJmx(new File("mytest.jmx"));
````
