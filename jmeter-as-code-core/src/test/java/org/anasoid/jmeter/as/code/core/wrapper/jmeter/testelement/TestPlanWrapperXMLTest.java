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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement;

import java.io.IOException;
import java.util.Arrays;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.AttributesFilterManager;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;

class TestPlanWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/testelement/testplan";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper = TestPlanWrapper.builder().withName("Test Plan").build();
    String wrapperContent = toTmpFile(testPlanWrapper, "testplan_");
    String expectedContent = readFile(PARENT_PATH + "/testplan.default.jmx");
    Diff myDiff =
        JmcXmlComparator.compare(
            expectedContent,
            wrapperContent,
            null,
            Arrays.asList(AttributesFilterManager.getCommentFilter()));
    Assertions.assertFalse(myDiff.hasDifferences(), "Testplan not identical " + myDiff);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Reverse Plan")
            .withComment("Comments")
            .withFunctionalMode(true)
            .withSerialized(true)
            .withTearDownOnShutdown(false)
            .withTestPlanClasspath("/classpath.jar")
            .addArgument(
                ArgumentWrapper.builder().withName("uservar1").withValue("uservar1-value").build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "testplan_reverse_");
    String expectedContent = readFile(PARENT_PATH + "/testplan.reverse.jmx");
    Diff myDiff = JmcXmlComparator.compare(expectedContent, wrapperContent);
    Assertions.assertFalse(myDiff.hasDifferences(), "Testplan not identical " + myDiff);
  }
}
