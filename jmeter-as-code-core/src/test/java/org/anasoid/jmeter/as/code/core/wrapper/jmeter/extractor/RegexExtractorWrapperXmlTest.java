package org.anasoid.jmeter.as.code.core.wrapper.jmeter.extractor;

import java.io.IOException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.extractor.FieldToCheck;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
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
 * Date :   28-Apr-2021
 */

class RegexExtractorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "RegexExtractor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                RegexExtractorWrapper.builder()
                    .withName("Regular Expression Extractor")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withTemplate("template")
                    .withRegex("regex")
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/regularExpressionExtractor.default.jmx", NODE_NAME);
  }

  /** Test with empty default value. */
  @Test
  void testDefaultEmpty() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                RegexExtractorWrapper.builder()
                    .withName("Regular Expression Extractor")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withTemplate("template")
                    .withRegex("regex")
                    .withDefaultEmpty(true)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/regularExpressionExtractor.default.empty.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                RegexExtractorWrapper.builder()
                    .withName("Regular Expression Extractor inverse")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withTemplate("template")
                    .withRegex("regex")
                    .withDefaultValue("d")
                    .withFieldToCheck(FieldToCheck.RESPONSE_CODE)
                    .withScope(Scope.CHILDREN)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/regularExpressionExtractor.inverse.jmx", NODE_NAME);
  }
}
