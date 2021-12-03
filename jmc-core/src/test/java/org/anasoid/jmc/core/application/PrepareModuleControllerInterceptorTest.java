package org.anasoid.jmc.core.application;

import java.util.ArrayList;
import java.util.List;
import org.anasoid.jmc.core.wrapper.jmeter.control.RecordingControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.SimpleControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.TestFragmentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
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
 * Date :   16-Jul-2021
 */

class PrepareModuleControllerInterceptorTest {

  @Test
  void testSimple() {
    SimpleControllerWrapper c1 = SimpleControllerWrapper.builder().withName("c1").build();
    SimpleControllerWrapper c2 = SimpleControllerWrapper.builder().withName("c2").build();

    TestFragmentWrapper testFragmentWrapper =
        TestFragmentWrapper.builder()
            .withName("Test Fragment")
            .addController(RecordingControllerWrapper.builder().addController(c1).build())
            .addController(c2)
            .build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addTestFragment(TestFragmentWrapper.builder().build())
            .addTestFragment(testFragmentWrapper)
            .build();

    PrepareModuleControllerInterceptor prepareModuleControllerInterceptor =
        new PrepareModuleControllerInterceptor();
    List<List<AbstractTestElementWrapper<?>>> result =
        prepareModuleControllerInterceptor.findTarget(
            testPlanWrapper, testFragmentWrapper.getName(), c1, null, new ArrayList<>());

    Assertions.assertFalse(result.isEmpty());
  }
}
