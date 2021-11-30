package org.anasoid.jmc.core.wrapper.jmeter.threads;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.wrapper.jmeter.control.ForeachControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.DebugSamplerWrapper;
import org.anasoid.jmc.core.wrapper.template.samplers.http.SimplePageTemplate;
import org.anasoid.jmc.core.wrapper.template.samplers.http.controller.SimplePageControllerTemplate;
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
 * Date :   19-May-2021
 */

class ThreadGroupJmxIncludeWrapperTest extends AbstractJmcCoreTest {

  @Test
  void testAddController() throws IOException {

    ThreadGroupJmxIncludeWrapper wrapper =
        ThreadGroupJmxIncludeWrapper.builder()
            .addController(ForeachControllerWrapper.builder().withName("ctrl").build())
            .build();
    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    wrapper = (ThreadGroupJmxIncludeWrapper) applicationTest.toJmx(wr);
    Assertions.assertEquals(
        "ctrl", ((ForeachControllerWrapper) wrapper.getChildren().get(0)).getName());
  }

  @Test
  void testAddControllerTemplate() throws IOException {

    ThreadGroupJmxIncludeWrapper wrapper =
        ThreadGroupJmxIncludeWrapper.builder()
            .addController(new SimplePageControllerTemplate("page", "/"))
            .build();
    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    wrapper = (ThreadGroupJmxIncludeWrapper) applicationTest.toJmx(wr);
    Assertions.assertTrue(
        ((GenericControllerWrapper) wrapper.getChildren().get(0)).getName().startsWith("page"));
  }

  @Test
  void testAddSampler() throws IOException {

    ThreadGroupJmxIncludeWrapper wrapper =
        ThreadGroupJmxIncludeWrapper.builder()
            .addSampler(DebugSamplerWrapper.builder().withName("sampler").build())
            .build();
    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    wrapper = (ThreadGroupJmxIncludeWrapper) applicationTest.toJmx(wr);
    Assertions.assertEquals(
        "sampler", ((DebugSamplerWrapper) wrapper.getChildren().get(0)).getName());
  }

  @Test
  void testAddSamplerTemplate() throws IOException {

    ThreadGroupJmxIncludeWrapper wrapper =
        ThreadGroupJmxIncludeWrapper.builder()
            .addSampler(new SimplePageTemplate("page", "/"))
            .build();
    ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    wrapper = (ThreadGroupJmxIncludeWrapper) applicationTest.toJmx(wr);
    Assertions.assertTrue(
        ((AbstractSamplerWrapper) wrapper.getChildren().get(0)).getName().startsWith("page"));
  }
}
