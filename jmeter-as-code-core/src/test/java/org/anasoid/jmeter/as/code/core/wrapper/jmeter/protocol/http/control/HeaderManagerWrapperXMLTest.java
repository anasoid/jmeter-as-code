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
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;

class HeaderManagerWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/protocol/http/control";

  private static final String NODE_NAME = "HeaderManager";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addChild(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group")
                    .addChild(
                        HTTPSamplerProxyWrapper.builder()
                            .withName("HTTP Request")
                            .withPath("")
                            .addChild(
                                HeaderManagerWrapper.builder()
                                    .withName("HTTP Header Manager")
                                    .build())
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpheadermanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpheadermanager.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpheadermanager  not identical " + diff);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addChild(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group")
                    .addChild(
                        HTTPSamplerProxyWrapper.builder()
                            .withName("HTTP Request")
                            .withPath("")
                            .addChild(
                                HeaderManagerWrapper.builder()
                                    .withName("HTTP Header Manager")
                                    .addHeader("param", "value")
                                    .build())
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpheadermanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpheadermanager.reverse.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpheadermanager  not identical " + diff);
  }
}