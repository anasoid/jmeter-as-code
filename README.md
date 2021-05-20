# Write Jmeter tests as code.

An API that give access to full Jmeter feature as code, All designed object in GUI can be written as code.

![Build & Test](https://github.com/anasoid/jmeter-as-code/actions/workflows/main.yml/badge.svg)
![Audit](https://github.com/anasoid/jmeter-as-code/actions/workflows/audit.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=alert_status)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=coverage)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=ncloc)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=security_rating)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=anasoid_jmeter-as-code&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=anasoid_jmeter-as-code)


######A basic script example:

````java
    TestPlanWrapper testPlan = TestPlanWrapper.builder()
        .addThread(ThreadGroupWrapper.builder()
            .addSampler(
                HTTPSamplerProxyWrapper.builder()
                    .withName("Home")
                    .withDomain("https://github.com")
                    .withProtocol("https")
                    .withPath("/anasoid")
                    .build())
            .build())
        .build();

            
  ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
 
  applicationTest.run();
  //OR
  applicationTest.toJmx(new File("mytest.jmx"));
````

A basic script example using template:
````java
    TestPlanWrapper testPlan = TestPlanWrapper.builder()
        .addThread(ThreadGroupWrapper.builder()
            .addSampler(new HomePage())
            .build())
        .build();

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);

    applicationTest.run();
    //OR
    applicationTest.toJmx(new File("mytest.jmx"));
    
class HomePage extends
    AbstractJmcTemplate<HTTPSamplerProxyWrapper, HTTPSamplerProxyWrapperBuilder<?, ?>> {

  @Override
  protected void prepareBuilder(HTTPSamplerProxyWrapperBuilder<?, ?> builder) {
    super.prepareBuilder(builder);
    builder.withName("Home")
        .withDomain("https://github.com")
        .withProtocol("https")
        .withPath("/anasoid");
  }

  @Override
  protected JmcWrapperBuilder<?> init() {
    return HTTPSamplerProxyWrapper.builder();
  }
}

            

  
````
