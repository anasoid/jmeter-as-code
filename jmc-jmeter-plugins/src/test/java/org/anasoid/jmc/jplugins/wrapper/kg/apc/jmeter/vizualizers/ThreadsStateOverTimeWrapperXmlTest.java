package org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.vizualizers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import kg.apc.jmeter.vizualizers.CorrectedResultCollector;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
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
 * Date :   06-Jul-2021
 */

class ThreadsStateOverTimeWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH = ROOT_PATH + "/kg/apc/jmeter/vizualizers";

  private static final String NODE_NAME = CorrectedResultCollector.class.getName();

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ThreadsStateOverTimeWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(ThreadsStateOverTimeWrapper.builder().build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/activeThreadsOverTime.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(
                ThreadsStateOverTimeWrapper.builder()
                    .withInterval(2000)
                    .withGraphAggregate(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/activeThreadsOverTime.inverse.jmx", NODE_NAME);
  }
}
