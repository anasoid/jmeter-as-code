package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
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

class XPathAssertionWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/assertions";

  private static final String NODE_NAME = "XPathAssertion";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addAssertion(
                XPathAssertionWrapper.builder().withName("XPath Assertion").withXpath("/").build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/XPathAssertion.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultTidy() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addAssertion(
                XPathAssertionWrapper.builder()
                    .withName("XPath Assertion")
                    .withXpath("tidy")
                    .withUseTidy(true)
                    .withNegate(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/XPathAssertion.tidy.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseTidy() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addAssertion(
                XPathAssertionWrapper.builder()
                    .withName("XPath Assertion inverse")
                    .withXpath("tidy")
                    .withUseTidy(true)
                    .withTidyQuiet(false)
                    .withTidyReportErrors(true)
                    .withTidyShowWarnings(true)
                    .withNegate(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/XPathAssertion.tidy.inverse.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addAssertion(
                XPathAssertionWrapper.builder()
                    .withName("XPath Assertion inverse")
                    .withXpath("inverse")
                    .withNamespace(true)
                    .withValidate(true)
                    .withDownloadDtds(true)
                    .withWhitespace(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/XPathAssertion.inverse.jmx", NODE_NAME);
  }
}
