package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import kg.apc.jmeter.threads.UltimateThreadGroup;
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
 * Date :   09-Jun-2021
 */

class UltimateThreadGroupWrapperXmlTest extends AbstractJmcJmeterPluginTest {
  private static final String PARENT_PATH = ROOT_PATH + "/com/blazemeter/jmeter/threads";

  private static final String NODE_NAME = UltimateThreadGroup.class.getName();

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(UltimateThreadGroupWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addThread(UltimateThreadGroupWrapper.builder().build()).build();

    checkWrapper(
        testPlanWrapper,
        PARENT_PATH + "/jp@gcUltimateThreadGroup.default.jmx",
        NODE_NAME,
        "LoopController.loops");
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                UltimateThreadGroupWrapper.builder()
                    .addThreadsSchedule(100, 0, 30, 60, 10)
                    .addThreadsSchedule(50, 10, 15, 40, 5)
                    .build())
            .build();

    // Ignore first line , name are different from others.
    checkWrapper(
        testPlanWrapper,
        PARENT_PATH + "/jp@gcUltimateThreadGroup.inverse.adapt.jmx",
        NODE_NAME,
        "LoopController.loops");
  }
}
