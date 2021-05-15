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

package org.anasoid.jmc.core.xstream.converters;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.data.DataTesting;
import org.anasoid.jmc.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmc.core.xstream.exceptions.ConversionMandatoryException;
import org.anasoid.jmc.test.asserts.JmcAsserts;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Main Test TestElementConverter.
 *
 * @see TestElementConverter
 */
class TestElementConverterCoreTest extends AbstractJmcCoreTest {

  @Test
  void testProp() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautApplicationTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    println("content :" + content);

    JmcAsserts.get().assertXPathValue(content, "/parent/boolProp/@name", "Parent.bb1");
    String parentKey = "/parent";
    JmcAsserts.get().assertXPathPropInt(content, parentKey, "Parent.ii1", "1");
    JmcAsserts.get().assertXPathPropBool(content, parentKey, "Parent.bb1", "false");
    JmcAsserts.get().assertXPathPropLong(content, parentKey, "Parent.ll1", "2");
    JmcAsserts.get().assertXPathPropFloat(content, parentKey, "Parent.ff1", "10.0");
    JmcAsserts.get().assertXPathPropDouble(content, parentKey, "Parent.dd1", "10.0");
  }

  @Test
  void testCollection() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautApplicationTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    String elementPropPath = "/parent/elementProp[@name='Parent.arguments']";
    JmcAsserts.get().assertXPathValue(content, elementPropPath + "/@elementType", "Arguments");
    JmcAsserts.get().assertXPathValue(content, elementPropPath + "/@guiclass", "ArgumentsPanel");
    JmcAsserts.get().assertXPathValue(content, elementPropPath + "/@testclass", "Arguments");
  }

  @Test
  void testAttribute() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautApplicationTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    String elementPropPath = "/parent/elementProp[@name='Parent.child']";
    JmcAsserts.get().assertXPathPropBool(content, elementPropPath, "subChild.bb1", "false");
    JmcAsserts.get().assertXPathPropString(content, elementPropPath, "child.field", "subSuper");
    JmcAsserts.get()
        .assertXPathPropString(content, elementPropPath, "child.onSampleError", "continue");
    JmcAsserts.get().assertXPathPropString(content, elementPropPath, "subChild.method", "me");
    JmcAsserts.get().assertXPathValue(content, elementPropPath + "/fieldChild", "super");
    JmcAsserts.get().assertXPathValue(content, elementPropPath + "/onSampleError2", "stoptest");
  }

  @Test
  void firstTest() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautApplicationTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    println("content :" + content);
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
