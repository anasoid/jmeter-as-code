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

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper.ThreadGroupWrapperBuilder;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestPlanWrapperTest extends AbstractJmcTest {

  private static final String ARG_NAME = "uservar1";
  private static final String ARG_VALUE = "uservar1-value";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(TestPlanWrapper.builder());
  }

  @Test
  void testArgumentsAddOne() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addArgument(ArgumentWrapper.builder().withName(ARG_NAME).withValue(ARG_VALUE).build())
            .build();
    Assertions.assertEquals(ARG_NAME, testPlanWrapper.getArguments().get(0).getName());
    Assertions.assertEquals(ARG_VALUE, testPlanWrapper.getArguments().get(0).getValue());
  }

  @Test
  void testArgumentsAddList() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addArguments(
                Arrays.asList(
                    ArgumentWrapper.builder().withName(ARG_NAME).withValue(ARG_VALUE).build()))
            .build();
    Assertions.assertEquals(ARG_NAME, testPlanWrapper.getArguments().get(0).getName());
    Assertions.assertEquals(ARG_VALUE, testPlanWrapper.getArguments().get(0).getValue());
  }

  @Test
  void testArgumentsAddKeyValue() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addArgument(ARG_NAME, ARG_VALUE).build();
    Assertions.assertEquals(ARG_NAME, testPlanWrapper.getArguments().get(0).getName());
    Assertions.assertEquals(ARG_VALUE, testPlanWrapper.getArguments().get(0).getValue());
  }

  @Test
  void testAddThreadTemplate() throws IOException {

    // Thread Group
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().withName("Parent").addThread(new MyThread()).build();

    Assertions.assertEquals(
        "thread", ((ThreadGroupWrapper) testPlanWrapper.getChilds().get(0)).getName());
    Assertions.assertEquals(
        "100", ((ThreadGroupWrapper) testPlanWrapper.getChilds().get(0)).getDelay());
    Assertions.assertEquals(
        "50", ((ThreadGroupWrapper) testPlanWrapper.getChilds().get(0)).getLoops());
  }

  class MyThread extends AbstractJmcTemplate<ThreadGroupWrapper> {

    @Override
    protected JmcWrapperBuilder<ThreadGroupWrapper> init() {
      return (JmcWrapperBuilder<ThreadGroupWrapper>)
          ThreadGroupWrapper.builder().withName("thread");
    }

    @Override
    protected void prepareBuilder(JmcWrapperBuilder<ThreadGroupWrapper> builder) {
      ThreadGroupWrapperBuilder threadGroupWrapperBuilder = (ThreadGroupWrapperBuilder) builder;
      threadGroupWrapperBuilder.withDelay(100);
    }

    @Override
    protected void prepareWrapper(ThreadGroupWrapper threadGroupWrapper) {
      threadGroupWrapper.setLoops("50");
    }
  }
}
