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

package org.anasoid.jmc.core.wrapper.jmeter.samplers;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for HTTPSamplerBaseWrapper.
 *
 * @see HTTPSamplerBaseWrapper
 */
class HTTPSamplerBaseWrapperCoreTest extends AbstractJmcCoreTest {

  private static final String ARG = "arg";
  private static final String VAL = "value";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(HTTPSamplerProxyWrapper.builder());
  }

  @Test
  void testFollowRedirectsDefaultTrue() throws IOException {
    HTTPSamplerProxyWrapper httpSampler =
        HTTPSamplerProxyWrapper.builder().withName("param").withPath("").build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(httpSampler);
    StringWriter wr = new StringWriter();
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    Assertions.assertTrue(httpSamplerAfter.getFollowRedirects());

    httpSampler =
        HTTPSamplerProxyWrapper.builder()
            .withPath("")
            .withFollowRedirects(false)
            .withAutoRedirects(false)
            .build();
    Assertions.assertFalse(httpSampler.getFollowRedirects());
    Assertions.assertFalse(httpSampler.getAutoRedirects());
    httpSampler =
        HTTPSamplerProxyWrapper.builder()
            .withPath("")
            .withFollowRedirects(false)
            .withAutoRedirects(true)
            .build();
    Assertions.assertFalse(httpSampler.getFollowRedirects());
    Assertions.assertTrue(httpSampler.getAutoRedirects());
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
    StringWriter wr = new StringWriter();
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    Assertions.assertFalse(httpSamplerAfter.getFollowRedirects());
  }

  @Test
  void testBodyWithArgsFail() throws IOException {
    try {

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
  void testargsWithBodyFail() throws IOException {
    try {

      HTTPSamplerProxyWrapper.builder()
          .withName("param")
          .withPath("")
          .addArgument(ARG, VAL)
          .withBody("body")
          .build();
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      Assertions.assertTrue(e.getMessage().contains("body"));
    }
  }

  @Test
  void testFolowRedirectAndAutoRedirectFails() throws IOException {
    try {

      HTTPSamplerProxyWrapper.builder()
          .withPath("")
          .withAutoRedirects(true)
          .withFollowRedirects(true)
          .build();
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      Assertions.assertTrue(e.getMessage().contains("followRedirects  and autoRedirects"));
    }
    try {

      HTTPSamplerProxyWrapper.builder()
          .withPath("")
          .withFollowRedirects(true)
          .withAutoRedirects(true)
          .build();
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      Assertions.assertTrue(e.getMessage().contains("followRedirects  and autoRedirects"));
    }
  }

  @Test
  void testAddArgument() throws IOException {
    // ADD DIRECT
    HTTPSamplerProxyWrapper httpsampler =
        HTTPSamplerProxyWrapper.builder().withPath("").addArgument(ARG, VAL).build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(httpsampler);
    StringWriter wr = new StringWriter();
    HTTPSamplerProxyWrapper httpSamplerAfter = (HTTPSamplerProxyWrapper) applicationTest.toJmx(wr);
    HTTPArgumentWrapper argument = httpSamplerAfter.getArguments().get(0);
    Assertions.assertEquals(ARG, argument.getName());
    Assertions.assertEquals(VAL, argument.getValue());
  }
}
