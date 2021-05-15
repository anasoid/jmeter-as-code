package org.anasoid.jmc.core.wrapper.jmeter.extractor.json.jmespath;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.test.utils.SetterTestUtils;
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

class JMESPathExtractorWrapperXmlCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor/json/jmespath";

  private static final String NODE_NAME = "JMESPathExtractor";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(JMESPathExtractorWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JMESPathExtractorWrapper.builder()
                    .withRefName("var")
                    .withJmesPathExpr("expres")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonJMESPathExtractor.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JMESPathExtractorWrapper.builder()
                    .withName("JSON JMESPath Extractor inverse")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withJmesPathExpr("expres")
                    .withDefaultValue("d")
                    .withScope(Scope.CHILDREN)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonJMESPathExtractor.inverse.jmx", NODE_NAME);
  }
}
