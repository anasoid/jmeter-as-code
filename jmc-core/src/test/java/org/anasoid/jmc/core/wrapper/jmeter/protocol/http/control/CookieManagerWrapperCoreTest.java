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

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;
import org.anasoid.jmc.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmc.core.xstream.exceptions.ConversionMandatoryException;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CookieManagerWrapperCoreTest extends AbstractJmcCoreTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(CookieManagerWrapper.builder());
  }

  @Test
  void testMandatoryName() throws IOException {

    CookieManagerWrapper cookieManagerWrapper =
        CookieManagerWrapper.builder()
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
            .addCookie(CookieWrapper.builder().withValue("val").build())
            .build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(cookieManagerWrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    try {
      applicationTest.toJmx(wr);
      Assertions.fail();
    } catch (ConversionMandatoryException e) {
      // NothingCookieManagerWrapper
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

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);
    StringWriter wr = new StringWriter(); // NOPMD

    parentTestElementWrapperTesting = (ParentTestElementWrapperTesting) applicationTest.toJmx(wr);

    Assertions.assertEquals(
        "HTTP Cookie Manager",
        ((CookieManagerWrapper) parentTestElementWrapperTesting.getChildren().get(0)).getName());
  }

  class MyConfig
      extends AbstractJmcTemplate<
          CookieManagerWrapper, CookieManagerWrapper.CookieManagerWrapperBuilder> {

    @Override
    protected JmcWrapperBuilder<?> init() {
      return CookieManagerWrapper.builder();
    }
  }
}
