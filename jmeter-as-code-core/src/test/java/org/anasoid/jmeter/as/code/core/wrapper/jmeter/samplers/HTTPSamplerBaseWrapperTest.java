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

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HTTPSamplerBaseWrapperTest extends AbstractJmcTest {

  @Test
  void testFollowRedirectsDefaultTrue() throws IOException {
    HTTPSamplerProxyWrapper httpSampler =
        HTTPSamplerProxyWrapper.builder().withName("param").withPath("").build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(httpSampler);
    StringWriter wr = new StringWriter(); // NOPMD
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    Assertions.assertTrue(httpSampler.getFollowRedirects());
  }

  @Test
  void testFollowRedirectsDefaultFalsewithAuto() throws IOException {
    HTTPSamplerProxyWrapper httpSampler =
        HTTPSamplerProxyWrapper.builder()
            .withName("param")
            .withAutoRedirects(true)
            .withPath("")
            .build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(httpSampler);
    StringWriter wr = new StringWriter(); // NOPMD
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    Assertions.assertFalse(httpSampler.getFollowRedirects());
  }

  @Test
  void testBodyWithArgsFail() throws IOException {
    try {
      HTTPSamplerProxyWrapper httpSampler =
          HTTPSamplerProxyWrapper.builder()
              .withName("param")
              .withPath("")
              .withBody("body")
              .addArgument("arg", "value")
              .build();
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      Assertions.assertTrue(e.getMessage().contains("body"));
    }
  }

  @Test
  void testAddArgument() throws IOException {
    HTTPSamplerProxyWrapper httpsampler =
        HTTPSamplerProxyWrapper.builder().withPath("").addArgument("arg", "value").build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(httpsampler);
    StringWriter wr = new StringWriter(); // NOPMD
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    HTTPArgumentWrapper argument = httpsampler.getArguments().get(0);
    Assertions.assertEquals("arg", argument.getName());
    Assertions.assertEquals("value", argument.getValue());
  }
}
