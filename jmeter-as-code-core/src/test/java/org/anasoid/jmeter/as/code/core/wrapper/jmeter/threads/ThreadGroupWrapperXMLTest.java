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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.apache.jmeter.control.LoopController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xmlunit.diff.Diff;

class ThreadGroupWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/threads";

  private static final String FRAGMENT_XPATH = "/jmeterTestPlan/hashTree";
  private static final String NODE_NAME = "ThreadGroup";

  @Test
  void testDefault()
      throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addChild(ThreadGroupWrapper.builder().withName("Thread Group").build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "testplan_");
    String wrapperContentFragement = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/threadgroup.default.jmx");
    String expectedContentFragement = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragement, wrapperContentFragement);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff, LoopController.LOOPS),
        "theadGroup  not identical " + diff);
  }

  @Test
  void testReverse()
      throws IOException, XPathExpressionException, SAXException, ParserConfigurationException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addChild(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group Reverse")
                    .withOnSampleError(OnSampleError.ON_SAMPLE_ERROR_STOPTEST)
                    .withContinueForever(true)
                    .withNumThreads(2)
                    .withRampUp(2)
                    .withScheduler(true)
                    .withDelayedStartup(true)
                    .withDuration(5)
                    .withDelay(7)
                    .withIsSameUserOnNextIteration(false)
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "testplan_");
    String wrapperContentFragement = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/threadgroup.reverse.jmx");
    String expectedContentFragement = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragement, wrapperContentFragement);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff, LoopController.LOOPS),
        "theadGroup  not identical " + diff);
  }


}
