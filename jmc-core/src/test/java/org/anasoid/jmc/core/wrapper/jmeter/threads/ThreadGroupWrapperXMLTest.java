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
 * Date :   14-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.threads;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.apache.jmeter.control.LoopController;
import org.junit.jupiter.api.Test;

class ThreadGroupWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/threads";

  private static final String NODE_NAME = "ThreadGroup";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addThread(ThreadGroupWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/threadgroup.default.jmx", NODE_NAME);
  }

  @Test
  void testFullDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addThread(ThreadGroupWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/threadgroup.default.jmx");
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName("Thread Group Reverse")
                    .withOnSampleError(OnSampleError.ON_SAMPLE_ERROR_STOPTEST)
                    .withContinueForever(true)
                    .withNumThreads(2)
                    .withRampUp(2)
                    .withScheduler(true)
                    .withDelayedStartup(true)
                    .withDuration(5)
                    .withDelay(7)
                    .withIsSameUserOnNextIteration(false)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/threadgroup.reverse.jmx", NODE_NAME, LoopController.LOOPS);
  }
}
