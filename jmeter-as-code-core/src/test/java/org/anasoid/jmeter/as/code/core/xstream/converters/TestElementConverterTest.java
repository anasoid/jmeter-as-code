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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.xstream.converters;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.asserts.JmcAsserts;
import org.anasoid.jmeter.as.code.core.data.DataTesting;
import org.anasoid.jmeter.as.code.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionMandatoryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Main Test TestElementConverter.
 *
 * @see TestElementConverter
 */
class TestElementConverterTest extends AbstractJmcTest {

  @Test
  void firstTest() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautApplicationTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    println("content :" + content);
    JmcAsserts.get().assertXPathPropInt(content, "/parent", "Parent.ii1", "1");
    JmcAsserts.get().assertXPathValue(content, "/parent/boolProp/@name", "Parent.bb1");
  }

  @Test
  void defaultValueTest() throws IOException {

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withBb1(false)
            .withIi1(1)
            .withLl1(2L)
            .build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    println("content :" + content);
    JmcAsserts.get().assertNotHasXPathPropLong(content, "/parent", "Parent.defaultLong");

    ApplicationTest applicationTest2 =
        ApplicationTestUtilsForTesting.getApplicationTest(
            parentTestElementWrapperTesting.toBuilder().withDefaultLong(20L).build());
    StringWriter wr2 = new StringWriter(); // NOPMD
    applicationTest2.toJmx(wr2);
    String content2 = wr2.toString();
    println("content2 :" + content2);
    JmcAsserts.get().assertHasXPathPropLong(content2, "/parent", "Parent.defaultLong");
  }

  @Test
  void mandatoryTest() throws IOException {

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder().withBb1(false).withIi1(1).withLl1(2L).build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);
    StringWriter wr = new StringWriter(); // NOPMD
    try {
      applicationTest.toJmx(wr);
      Assertions.fail("Should fail need mandatory field name");
    } catch (ConversionMandatoryException e) {
      // Nothing
    }
  }
}
