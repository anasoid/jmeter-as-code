package org.anasoid.jmc.core.wrapper.jmeter.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
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
 * Date :   11-Apr-2021
 */

class RandomVariableConfigWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/config";

  private static final String NODE_NAME = "RandomVariableConfig";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(RandomVariableConfigWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addConfig(RandomVariableConfigWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/randomvariable.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                RandomVariableConfigWrapper.builder()
                    .withName("Random Variable inverse")
                    .withMinimumValue(10)
                    .withMaximumValue(20)
                    .withFormat("format")
                    .withPerThread(true)
                    .withRandomSeed(100)
                    .withVariable(new Variable("myVar"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/randomvariable.inverse.default.jmx", NODE_NAME);
  }
}
