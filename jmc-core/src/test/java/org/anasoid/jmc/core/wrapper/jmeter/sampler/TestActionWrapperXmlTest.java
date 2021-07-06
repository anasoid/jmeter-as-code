package org.anasoid.jmc.core.wrapper.jmeter.sampler;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.sampler.TestActionWrapper.Action;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
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

class TestActionWrapperXmlTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/sampler";

  private static final String NODE_NAME = "TestAction";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(TestActionWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/flowControlAction.default.jmx", NODE_NAME);
  }

  @Test
  void testDuration() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(TestActionWrapper.builder().withDuration(10).build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/flowControlAction.pause.jmx", NODE_NAME);
  }

  @Test
  void testStart() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(
                        TestActionWrapper.builder()
                            .withAction(Action.RESTART_NEXT_LOOP)
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/flowControlAction.start.jmx", NODE_NAME);
  }

}
