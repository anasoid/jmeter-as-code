package org.anasoid.jmc.core.wrapper.jmeter.extractor.json.jsonpath;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
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

class JSONPostProcessorWrapperXmlCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor/json/jsonpath";

  private static final String NODE_NAME = "JSONPostProcessor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JSONPostProcessorWrapper.builder()
                    .addReferenceName(new Variable("var1"))
                    .addJsonPathExpr("express")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonExtractor.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JSONPostProcessorWrapper.builder()
                    .withName("JSON Extractor inverse")
                    .addReferenceName(new Variable("var1"))
                    .addJsonPathExpr("express1")
                    .addDefaultValue("d1")
                    .addReferenceName(new Variable("var2"))
                    .addJsonPathExpr("express2")
                    .addDefaultValue("d2")
                    .withMatchNumber("10")
                    .withComputeConcat(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/jsonExtractor.inverse.jmx", NODE_NAME);
  }
}
