package org.anasoid.jmc.core.wrapper.jmeter.modifiers;

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
 * Date :   10-Apr-2021
 */

class CounterConfigXmlWrapperCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/modifiers";

  private static final String NODE_NAME = "CounterConfig";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addConfig(CounterConfigWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/counter.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                CounterConfigWrapper.builder()
                    .withName("Counter reverse")
                    .withStart(10)
                    .withEnd(200)
                    .withIncrement(5)
                    .withVariable(new Variable("var"))
                    .withPerUser(true)
                    .withResetOnEachIteration(true)
                    .withFormat("format")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/counter.default.reverse.jmx", NODE_NAME);
  }
}
