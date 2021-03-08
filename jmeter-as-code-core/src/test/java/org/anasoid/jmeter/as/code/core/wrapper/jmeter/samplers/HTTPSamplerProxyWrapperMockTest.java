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
 * Date :   14-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;

import java.io.IOException;
import org.anasoid.jmeter.as.code.core.AbstractHttpMockJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Test;

/**
 * Test for HTTPSamplerProxyWrapper based on Real CALL to mock server.
 *
 * @see HTTPSamplerProxyWrapper
 */
class HTTPSamplerProxyWrapperMockTest extends AbstractHttpMockJmcTest {

  @Test
  void testDefault() throws IOException {

    // First HTTP Sampler - open example.com
    HTTPSamplerProxyWrapper sampler =
        HTTPSamplerProxyWrapper.builder().withPath(getWiremockUri()).withName("sampler").build();

    // Thread Group
    ThreadGroupWrapper threadGroup =
        ThreadGroupWrapper.builder()
            .withName("Example Thread Group")
            .addSampler(sampler)
            .addSampler(sampler)
            .build();

    // Test Plan
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Create JMeter Script From Java Code")
            .addThread(threadGroup)
            .build();

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
    applicationTest.run();
    getWiremockServer().verify(2, getRequestedFor(anyUrl()));
  }
}
