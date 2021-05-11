package org.anasoid.jmc.core.wrapper.jmeter.extractor;

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
 * Date :   29-Apr-2021
 */

class XPathExtractorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "XPathExtractor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                XPathExtractorWrapper.builder()
                    .withName("XPath Extractor")
                    .withRefName("var")
                    .withXpathQuery("query")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpathExtractor.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultTidy() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                XPathExtractorWrapper.builder()
                    .withName("XPath Extractor")
                    .withRefName("var")
                    .withXpathQuery("query")
                    .withUseTidy(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpathExtractor.tidy.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseTidy() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                XPathExtractorWrapper.builder()
                    .withName("XPath Extractor inverse")
                    .withRefName("var")
                    .withXpathQuery("query")
                    .withFragment(true)
                    .withDefaultValue("d")
                    .withUseTidy(true)
                    .withTidyQuiet(true)
                    .withTidyReportErrors(true)
                    .withTidyShowWarnings(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpathExtractor.tidy.inverse.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                XPathExtractorWrapper.builder()
                    .withName("XPath Extractor inverse")
                    .withRefName("var")
                    .withXpathQuery("query")
                    .withFragment(true)
                    .withDefaultValue("d")
                    .withNamespace(true)
                    .withValidate(true)
                    .withDownloadDtds(true)
                    .withWhitespace(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpathExtractor.inverse.jmx", NODE_NAME);
  }
}
