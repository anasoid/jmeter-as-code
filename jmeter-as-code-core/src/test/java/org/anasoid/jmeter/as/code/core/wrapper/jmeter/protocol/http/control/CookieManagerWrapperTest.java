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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
import org.anasoid.jmeter.as.code.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.template.AbstractJmcTemplate;
import org.anasoid.jmeter.as.code.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionMandatoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CookieManagerWrapperTest extends AbstractJmcTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(CookieManagerWrapper.builder().build());
  }

  @Test
  void testMandatoryName() throws IOException {

    CookieManagerWrapper cookieManagerWrapper =
        CookieManagerWrapper.builder()
            .withName("HTTP Cookie Manager")
            .addCookie(CookieWrapper.builder().withName("name").build())
            .build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(cookieManagerWrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    try {
      applicationTest.toJmx(wr);
      Assertions.fail();
    } catch (ConversionMandatoryException e) {
      // Nothing
    }
  }

  @Test
  void testMandatoryValue() throws IOException {

    CookieManagerWrapper cookieManagerWrapper =
        CookieManagerWrapper.builder()
            .withName("HTTP Cookie Manager")
            .addCookie(CookieWrapper.builder().withValue("val").build())
            .build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(cookieManagerWrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    try {
      applicationTest.toJmx(wr);
      Assertions.fail();
    } catch (ConversionMandatoryException e) {
      // Nothing
    }
  }

  @Test
  void testAddConfigTemplate() throws IOException {

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withField("field")
            .addConfig(new MyConfig())
            .build();

    Assertions.assertEquals(
        "Cookie Manager",
        ((CookieManagerWrapper) parentTestElementWrapperTesting.getChilds().get(0)).getName());
  }

  class MyConfig extends AbstractJmcTemplate<CookieManagerWrapper> {

    @Override
    protected JmcWrapperBuilder<CookieManagerWrapper> init() {
      return (JmcWrapperBuilder<CookieManagerWrapper>)
          CookieManagerWrapper.builder().withName("Cookie Manager");
    }
  }
}
