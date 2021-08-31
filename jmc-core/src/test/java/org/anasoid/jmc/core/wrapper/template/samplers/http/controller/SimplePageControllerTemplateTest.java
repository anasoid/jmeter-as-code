package org.anasoid.jmc.core.wrapper.template.samplers.http.controller;

import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
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
 * Date :   17-May-2021
 */

class SimplePageControllerTemplateTest extends AbstractJmcCoreTest {

  private static String NAME = "name";
  private static String PATH = "path";
  private static String DOMAIN = "domain";

  @Test
  void testParam() {

    JmcTemplate<GenericControllerWrapper<?, ?>> simplePageControllerTemplate =
        new SimplePageControllerTemplate(NAME, PATH, DOMAIN, HttpMethod.POST);
    GenericControllerWrapper<?, ?> controller = simplePageControllerTemplate.generate();
    HTTPSamplerProxyWrapper sampler =
        (HTTPSamplerProxyWrapper) simplePageControllerTemplate.generate().getChilds().get(0);

    Assertions.assertEquals(NAME + " CTRL", controller.getName());
    Assertions.assertEquals(NAME, sampler.getName());
    Assertions.assertEquals(PATH, sampler.getPath());
    Assertions.assertEquals(DOMAIN, sampler.getDomain());
    Assertions.assertEquals(HttpMethod.POST, sampler.getMethod());
  }

  @Test
  void testMinParam() {

    JmcTemplate<GenericControllerWrapper<?, ?>> simplePageControllerTemplate =
        new SimplePageControllerTemplate(NAME, PATH);
    GenericControllerWrapper<?, ?> controller = simplePageControllerTemplate.generate();
    HTTPSamplerProxyWrapper sampler =
        (HTTPSamplerProxyWrapper) simplePageControllerTemplate.generate().getChilds().get(0);

    Assertions.assertEquals(NAME + " CTRL", controller.getName());
    Assertions.assertEquals(NAME, sampler.getName());
    Assertions.assertEquals(PATH, sampler.getPath());
    Assertions.assertEquals(null, sampler.getDomain());
    Assertions.assertEquals(HttpMethod.GET, sampler.getMethod());
  }

  @Test
  void testThreeParam() {

    JmcTemplate<GenericControllerWrapper<?, ?>> simplePageControllerTemplate =
        new SimplePageControllerTemplate(NAME, PATH, DOMAIN);
    GenericControllerWrapper<?, ?> controller = simplePageControllerTemplate.generate();
    HTTPSamplerProxyWrapper sampler =
        (HTTPSamplerProxyWrapper) simplePageControllerTemplate.generate().getChilds().get(0);

    Assertions.assertEquals(NAME + " CTRL", controller.getName());
    Assertions.assertEquals(NAME, sampler.getName());
    Assertions.assertEquals(PATH, sampler.getPath());
    Assertions.assertEquals(DOMAIN, sampler.getDomain());
    Assertions.assertEquals(HttpMethod.GET, sampler.getMethod());
  }
}
