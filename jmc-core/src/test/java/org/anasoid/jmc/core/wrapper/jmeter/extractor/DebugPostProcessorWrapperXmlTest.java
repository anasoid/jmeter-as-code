package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
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
 * Date :   28-Apr-2021
 */

class DebugPostProcessorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor";

  private static final String NODE_NAME = "DebugPostProcessor";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(DebugPostProcessorWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(DebugPostProcessorWrapper.builder().build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/debug.postProcessor.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                DebugPostProcessorWrapper.builder()
                    .withName("Debug PostProcessor inverse")
                    .withDisplayJMeterProperties(true)
                    .withDisplaySystemProperties(true)
                    .withDisplayJMeterVariables(false)
                    .withDisplaySamplerProperties(false)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/debug.postProcessor.inverse.jmx", NODE_NAME);
  }
}
