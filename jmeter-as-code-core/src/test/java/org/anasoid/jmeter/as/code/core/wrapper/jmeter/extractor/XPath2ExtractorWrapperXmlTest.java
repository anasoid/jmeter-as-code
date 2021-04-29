package org.anasoid.jmeter.as.code.core.wrapper.jmeter.extractor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
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
 * Date :   29-Apr-2021
 */

class XPath2ExtractorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "XPath2Extractor";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(XPath2ExtractorWrapper.builder().build());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                XPath2ExtractorWrapper.builder()
                    .withName("XPath2 Extractor")
                    .withRefName("var")
                    .withXpathQuery("query")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpath2Extractor.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                XPath2ExtractorWrapper.builder()
                    .withName("XPath2 Extractor inverse")
                    .withRefName("var")
                    .withMatchNumber("10")
                    .withXpathQuery("query")
                    .withDefaultValue("d")
                    .addNamespace("alias1")
                    .addNamespace("alias2")
                    .withFragment(true)
                    .withScope(Scope.CHILDREN)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/xpath2Extractor.inverse.jmx", NODE_NAME);
  }
}
