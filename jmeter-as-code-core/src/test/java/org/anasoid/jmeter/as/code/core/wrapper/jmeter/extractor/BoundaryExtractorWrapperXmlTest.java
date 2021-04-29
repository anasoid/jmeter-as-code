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

class BoundaryExtractorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "BoundaryExtractor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                BoundaryExtractorWrapper.builder()
                    .withName("Boundary Extractor")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/boundaryExtractor.default.jmx", NODE_NAME);
  }

  /** Test with empty default value. */
  @Test
  void testDefaultEmpty() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                BoundaryExtractorWrapper.builder()
                    .withName("Boundary Extractor")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withDefaultEmpty(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/boundaryExtractor.default.empty.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPostProcessor(
                BoundaryExtractorWrapper.builder()
                    .withName("Boundary Extractor inverse")
                    .withRefName("var")
                    .withMatchNumber("0")
                    .withLeftBoundary("l")
                    .withRightBoundary("r")
                    .withDefaultValue("d")
                    .withFieldToCheck(FieldToCheck.BODY_AS_DOCUMENT)
                    .withScope(Scope.ALL)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/boundaryExtractor.inverse.jmx", NODE_NAME);
  }
}
