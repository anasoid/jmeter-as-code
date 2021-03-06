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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.config;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for HTTPSamplerBaseWrapper.
 *
 * @see HttpDefaultsWrapper
 */
class HTTPDefaultsWrapperTest extends AbstractJmcTest {

  private static final String ARG = "arg";
  private static final String VAL = "value";

  @Test
  void testBodyWithArgsFail() throws IOException {
    try {

      HttpDefaultsWrapper.builder()
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

      HttpDefaultsWrapper.builder()
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
  void testAddArgument() throws IOException {
    // ADD DIRECT
    HttpDefaultsWrapper httpconfig =
        HttpDefaultsWrapper.builder().withPath("").addArgument(ARG, VAL).build();
    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(httpconfig);
    StringWriter wr = new StringWriter(); // NOPMD
    HttpDefaultsWrapper httpSamplerAfter = (HttpDefaultsWrapper) applicationTest.toJmx(wr);
    HTTPArgumentWrapper argument = httpSamplerAfter.getArguments().get(0);
    Assertions.assertEquals(ARG, argument.getName());
    Assertions.assertEquals(VAL, argument.getValue());
  }
}
