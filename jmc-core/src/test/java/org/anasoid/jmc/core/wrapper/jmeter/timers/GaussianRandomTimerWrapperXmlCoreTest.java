package org.anasoid.jmc.core.wrapper.jmeter.timers;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
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
 * Date :   02-May-2021
 */

class GaussianRandomTimerWrapperXmlCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/timers";

  private static final String NODE_NAME = "GaussianRandomTimer";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(GaussianRandomTimerWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addTimer(GaussianRandomTimerWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/gaussianRandomTimer.default.jmx", NODE_NAME);
  }

  @Test
  void testVar() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addTimer(
                GaussianRandomTimerWrapper.builder()
                    .withDelay(new Variable("delay"))
                    .withDeviation(new Variable("rnd"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/gaussianRandomTimer.var.jmx", NODE_NAME);
  }
}
