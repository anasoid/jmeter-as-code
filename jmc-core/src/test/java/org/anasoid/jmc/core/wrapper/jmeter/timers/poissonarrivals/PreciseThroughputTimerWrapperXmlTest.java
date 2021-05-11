package org.anasoid.jmc.core.wrapper.jmeter.timers.poissonarrivals;

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
 * Date :   02-May-2021
 */

class PreciseThroughputTimerWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/timers/poissonarrivals";

  private static final String NODE_NAME = "PreciseThroughputTimer";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(PreciseThroughputTimerWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addTimer(PreciseThroughputTimerWrapper.builder().build()).build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/preciseThroughputTimer.adapted.default.jmx", NODE_NAME);
  }

  @Test
  void testVar() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addTimer(
                PreciseThroughputTimerWrapper.builder()
                    .withBatchSize(new Variable("nb"))
                    .withBatchThreadDelay(new Variable("delay"))
                    .withDuration(new Variable("d"))
                    .withThroughput(new Variable("t"))
                    .withThroughputPeriod(new Variable("t2"))
                    .withRandomSeed(new Variable("random"))
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/preciseThroughputTimer.adapted.var.jmx", NODE_NAME);
  }
}
