package org.anasoid.jmc.core.wrapper.jmeter.assertions.jmespath;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
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

class JMESPathAssertionWrapperXmlCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/assertions/jmespath";

  private static final String NODE_NAME = "JMESPathAssertion";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(JMESPathAssertionWrapper.builder().withJmesPath("jsonPath").build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonJMESPathAssertion.default.jmx", NODE_NAME);
  }

  @Test
  void testNullDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                JMESPathAssertionWrapper.builder()
                    .withJmesPath("jsonPath")
                    .withExpectNull(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonJMESPathAssertion.null.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                JMESPathAssertionWrapper.builder()
                    .withName("JSON JMESPath Assertion inverse")
                    .withJmesPath("path")
                    .withAdditionallyAssertValue(true)
                    .withInvert(true)
                    .withIsRegex(false)
                    .withExpectedValue("expected")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonJMESPathAssertion.inverse.jmx", NODE_NAME);
  }
}
