package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
 * Date :   01-May-2021
 */

class XPathAssertionWrapperTest extends AbstractJmcTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(XPathAssertionWrapper.builder().build());
  }

  @Test
  void testWithTidy() throws IOException {

    try {
      XPathAssertionWrapper wrapper =
          XPathAssertionWrapper.builder()
              .withName("XPath Extractor")
              .withXpath("query")
              .withUseTidy(true)
              .withWhitespace(true)
              .build();
      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter(); // NOPMD
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testWithoutTidy() throws IOException {

    try {
      XPathAssertionWrapper wrapper =
          XPathAssertionWrapper.builder().withXpath("query").withTidyShowWarnings(true).build();
      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter(); // NOPMD
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }
}
