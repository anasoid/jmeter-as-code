package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.ThroughputControllerWrapper.ExecutionStyle;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
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
 * Date :   02-May-2021
 */

class ThroughputControllerWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/control";

  private static final String NODE_NAME = "ThroughputController";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ThroughputControllerWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group")
                    .addController(
                        ThroughputControllerWrapper.builder()
                            .withName("Throughput Controller")
                            .withMaxThroughput(1)
                            .build())
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/throughputController.adapted.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group")
                    .addController(
                        ThroughputControllerWrapper.builder()
                            .withName("Throughput Controller")
                            .withMaxThroughput(new Variable("thr"))
                            .withPerUser(true)
                            .withExecutionStyle(ExecutionStyle.TotalExecutions)
                            .build())
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/throughputController.adapted.inverse.jmx", NODE_NAME);
  }
}
