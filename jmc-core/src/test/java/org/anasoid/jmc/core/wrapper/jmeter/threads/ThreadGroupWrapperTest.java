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

import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.SimpleControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThreadGroupWrapperTest extends AbstractJmcTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ThreadGroupWrapper.builder());
  }

  @Test
  void testParam() {

    ThreadGroupWrapper threadGroupWrapper =
        ThreadGroupWrapper.builder().withLoops(10).withContinueForever(true).build();
    Assertions.assertTrue(threadGroupWrapper.getContinueForever());
    Assertions.assertEquals("10", threadGroupWrapper.getLoops());
  }

  @Test
  void testAddSamplerTemplate() {

    // Thread Group
    ThreadGroupWrapper threadGroupWrapper =
        ThreadGroupWrapper.builder().withName("Parent").addSampler(new MySampler()).build();

    Assertions.assertEquals(
        "sampler", ((HTTPSamplerProxyWrapper) threadGroupWrapper.getChilds().get(0)).getName());
  }

  @Test
  void testAddControllerTemplate() {

    // Thread Group
    ThreadGroupWrapper threadGroupWrapper =
        ThreadGroupWrapper.builder().withName("Parent").addController(new MyController()).build();

    Assertions.assertEquals(
        "controller", ((SimpleControllerWrapper) threadGroupWrapper.getChilds().get(0)).getName());
  }

  class MySampler extends AbstractJmcTemplate<HTTPSamplerProxyWrapper> {

    @Override
    protected JmcWrapperBuilder<HTTPSamplerProxyWrapper> init() {
      return (JmcWrapperBuilder<HTTPSamplerProxyWrapper>)
          HTTPSamplerProxyWrapper.builder().withName("sampler");
    }
  }

  class MyController extends AbstractJmcTemplate<SimpleControllerWrapper> {

    @Override
    protected JmcWrapperBuilder<SimpleControllerWrapper> init() {
      return (JmcWrapperBuilder<SimpleControllerWrapper>)
          SimpleControllerWrapper.builder().withName("controller");
    }
  }
}
