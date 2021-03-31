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
 * Date :   09-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.dsl.control;

import static org.anasoid.jmeter.as.code.core.dsl.control.SimpleControllerDsl.simpleControllerBuilder;

import org.anasoid.jmeter.as.code.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.SimpleControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.template.AbstractJmcTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleControllerDslTest {
  @Test
  void testArgument() {

    SimpleControllerWrapper.SimpleControllerWrapperBuilder controller =
        simpleControllerBuilder("name");

    Assertions.assertEquals("name", controller.build().getName());
  }

  @Test
  void testAddSampler() {

    // Thread Group
    SimpleControllerWrapper simpleControllerWrapper =
        SimpleControllerWrapper.builder()
            .withName("Parent")
            .addSampler(HTTPSamplerProxyWrapper.builder().withName("sampler").build())
            .build();

    Assertions.assertEquals(
        "sampler",
        ((HTTPSamplerProxyWrapper) simpleControllerWrapper.getChilds().get(0)).getName());
  }

  @Test
  void testAddSamplerTemplate() {

    // Thread Group
    SimpleControllerWrapper simpleControllerWrapper =
        SimpleControllerWrapper.builder().withName("Parent").addSampler(new MySampler()).build();

    Assertions.assertEquals(
        "sampler",
        ((HTTPSamplerProxyWrapper) simpleControllerWrapper.getChilds().get(0)).getName());
  }

  class MySampler extends AbstractJmcTemplate<HTTPSamplerProxyWrapper> {

    @Override
    protected JmcWrapperBuilder<HTTPSamplerProxyWrapper> init() {
      return (JmcWrapperBuilder<HTTPSamplerProxyWrapper>)
          HTTPSamplerProxyWrapper.builder().withName("sampler");
    }
  }
}
