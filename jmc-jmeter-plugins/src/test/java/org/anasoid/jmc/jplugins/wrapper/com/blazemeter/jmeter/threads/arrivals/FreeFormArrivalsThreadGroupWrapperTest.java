package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.arrivals;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.anasoid.jmc.test.utils.SetterTestUtils;
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
 * Date :   10-Jun-2021
 */

class FreeFormArrivalsThreadGroupWrapperTest extends AbstractJmcJmeterPluginTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(FreeFormArrivalsThreadGroupWrapper.builder());
  }

  @Test
  void testSuccessDefault() throws IOException {

    FreeFormArrivalsThreadGroupWrapper wrapper =
        FreeFormArrivalsThreadGroupWrapper.builder().build();

    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
    StringWriter wr = new StringWriter();
    applicationTest.toJmx(wr);
  }

  @Test
  void testHiddenHold() throws IOException {

    try {
      FreeFormArrivalsThreadGroupWrapper wrapper =
          FreeFormArrivalsThreadGroupWrapper.builder().withHold(1).build();

      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter();
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testHiddenRampUp() throws IOException {

    try {
      FreeFormArrivalsThreadGroupWrapper wrapper =
          FreeFormArrivalsThreadGroupWrapper.builder().withRampUp(1).build();

      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter();
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testHiddenSteps() throws IOException {

    try {
      FreeFormArrivalsThreadGroupWrapper wrapper =
          FreeFormArrivalsThreadGroupWrapper.builder().withSteps(1).build();

      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter();
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }
}
