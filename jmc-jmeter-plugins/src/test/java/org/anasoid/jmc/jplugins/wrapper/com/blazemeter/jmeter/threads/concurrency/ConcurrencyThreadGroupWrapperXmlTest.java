package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.concurrency;

import com.blazemeter.jmeter.threads.concurrency.ConcurrencyThreadGroup;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.AbstractDynamicThreadGroupWrapper.UNIT;
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
 * Date :   08-Jun-2021
 */

class ConcurrencyThreadGroupWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH =
      ROOT_PATH + "/com/blazemeter/jmeter/threads/concurrency";

  private static final String NODE_NAME = ConcurrencyThreadGroup.class.getName();

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ConcurrencyThreadGroupWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ConcurrencyThreadGroupWrapper.builder()
                    .withHold(1)
                    .withTargetConcurrency(1)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/bzmConcurrencyThreadGroup.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ConcurrencyThreadGroupWrapper.builder()
                    .withTargetConcurrency(10)
                    .withRampUp(20)
                    .withSteps(5)
                    .withHold(10)
                    .withIterations(100)
                    .withUnit(UNIT.SECOND)
                    .withLogFilename("file.log")
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/bzmConcurrencyThreadGroup.inverse.jmx", NODE_NAME);
  }
}
