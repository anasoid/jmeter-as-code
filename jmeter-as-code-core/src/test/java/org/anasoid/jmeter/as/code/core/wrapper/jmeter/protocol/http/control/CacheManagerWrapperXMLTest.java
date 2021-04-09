package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions.ResponseAssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;
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
 * Date :   09-Apr-2021
 */

class CacheManagerWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/protocol/http/control";

  private static final String NODE_NAME = "CacheManager";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ResponseAssertionWrapper.builder().build());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(CacheManagerWrapper.builder().withName("HTTP Cache Manager").build())
            .build();

    String wrapperContent = toTmpFile(testPlanWrapper, "httpcachemanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpcachemanager.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpcachemanager  not identical " + diff);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(
                CacheManagerWrapper.builder()
                    .withName("HTTP Cache Manager Reverse")
                    .withMaxSize(7000)
                    .withControlledByThread(true)
                    .withUseExpires(false)
                    .build())
            .build();

    String wrapperContent = toTmpFile(testPlanWrapper, "httpcachemanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpcachemanager.reverse.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpcachemanager  not identical " + diff);
  }
}
