/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author : anas
 * Date :   08-Jan-2021
 */

package org.anasoid.jmeter.as.code.core;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@SuppressWarnings("PMD.SystemPrintln")
class FirstTest {

  @Test
  void testWrapper() throws Exception {

    // Construct Test Plan from previously initialized elements
    // JMeter Test Plan, basically JOrphan HashTree
    File destination = new File(System.getProperty("user.dir") + "/build/jmeter/xtream.jmx");
    FileUtils.forceMkdirParent(destination);

    System.out.println(destination);
    Writer wr = new FileWriter(destination); // NOPMD

    // First HTTP Sampler - open example.com
    HTTPSamplerProxyWrapper examplecomSamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("example.com")
            .withPort(80)
            .withPath("/")
            .withMethod(HttpMethod.GET)
            .withName("Open example.com")
            .build();

    // Second HTTP Sampler - open https://github.com
    HTTPSamplerProxyWrapper githubcomSamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("github.com")
            .withPort(80)
            .withPath("/")
            .withMethod(HttpMethod.GET)
            .withImplementation(Implementation.HttpClient4)
            .withIpSourceType(IpSourceType.DEVICE_IPV4)
            .withName("Open github.com")
            .build();

    // Second HTTP Sampler - open github.com
    HTTPSamplerProxyWrapper bodySamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("github.com")
            .withPort(80)
            .withPath("/")
            .withBody("BBBBBBBBBBBB")
            .withName("body github.com")
            .build();

    HTTPSamplerProxyWrapper argSamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("github.com")
            .withPort(80)
            .withPath("/")
            .addArgument("ppppp", "vvvvvvvvv")
            .withName("argument github.com")
            .build();

    // Thread Group
    ThreadGroupWrapper threadGroupWrapper =
        ThreadGroupWrapper.builder()
            .withName("Example Thread Group")
            .withNumThreads(1)
            .withDelay(9L)
            .withRampUp(7)
            .withLoops(7)
            .withOnSampleError(OnSampleError.ON_SAMPLE_ERROR_STOPTHREAD)
            .withContinueForever(false)
            .addChild(examplecomSamplerWrapper)
            .addChild(githubcomSamplerWrapper)
            .addChild(githubcomSamplerWrapper)
            .addChild(bodySamplerWrapper)
            .addChild(argSamplerWrapper)
            .build();

    // Test Plan
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Create JMeter Script From Java Code")
            // .addArgument("testplanArg1", "valuearg1")
            // .addArgument("testplanArg2", "valuearg2")
            .addChild(threadGroupWrapper)
            .build();

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
    applicationTest.toJmx(wr);
    applicationTest.run();
    Assertions.assertTrue(true);
  }
}
