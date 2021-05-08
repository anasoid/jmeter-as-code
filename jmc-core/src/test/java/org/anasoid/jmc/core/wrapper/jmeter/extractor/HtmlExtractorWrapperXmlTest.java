package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.wrapper.jmeter.extractor.HtmlExtractorWrapper.ExtractorImpl;
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
 * Date :   29-Apr-2021
 */

class HtmlExtractorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "HtmlExtractor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                HtmlExtractorWrapper.builder()
                    .withName("CSS Selector Extractor")
                    .withQueryExpression("expres")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/cssSelectorExtractor.default.jmx", NODE_NAME);
  }

  /** Test with empty default value. */
  @Test
  void testDefaultEmpty() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                HtmlExtractorWrapper.builder()
                    .withName("CSS Selector Extractor")
                    .withQueryExpression("expres")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withDefaultEmpty(true)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/cssSelectorExtractor.default.empty.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                HtmlExtractorWrapper.builder()
                    .withName("CSS Selector Extractor inverse")
                    .withQueryExpression("expres")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withAttribute("a")
                    .withDefaultValue("d")
                    .withExtractorImplementation(ExtractorImpl.JSOUP)
                    .withScope(Scope.VARIABLE)
                    .withScopeVariable(new Variable("myvar"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/cssSelectorExtractor.inverse.jmx", NODE_NAME);
  }
}
