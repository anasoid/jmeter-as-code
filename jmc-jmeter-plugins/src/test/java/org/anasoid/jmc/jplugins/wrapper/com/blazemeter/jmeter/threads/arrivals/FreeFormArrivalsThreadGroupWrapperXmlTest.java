package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.arrivals;

import com.blazemeter.jmeter.threads.arrivals.FreeFormArrivalsThreadGroup;
import java.io.IOException;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.AbstractDynamicThreadGroupWrapper.UNIT;
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

class FreeFormArrivalsThreadGroupWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH = ROOT_PATH + "/com/blazemeter/jmeter/threads/arrivals";

  private static final String NODE_NAME = FreeFormArrivalsThreadGroup.class.getName();

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(FreeFormArrivalsThreadGroupWrapper.builder().build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/bzmFreeFormArrivalsThreadGroup.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                FreeFormArrivalsThreadGroupWrapper.builder()
                    .withIterations(100)
                    .withLogFilename("file.log")
                    .withConcurrencyLimit(2)
                    .withUnit(UNIT.SECOND)
                    .addSchedule(1, 10, 60)
                    .addSchedule(2, 20, 30)
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/bzmFreeFormArrivalsThreadGroup.inverse.jmx", NODE_NAME);
  }
}
