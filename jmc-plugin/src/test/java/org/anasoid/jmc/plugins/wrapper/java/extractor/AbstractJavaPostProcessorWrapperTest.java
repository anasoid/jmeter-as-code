package org.anasoid.jmc.plugins.wrapper.java.extractor;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.DebugSamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJmcPluginJavaTest;
import org.anasoid.jmc.test.AbstractJmcTest;
import org.anasoid.jmc.test.log.LogMonitor;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Date :   15-Jun-2021
 */

class AbstractJavaPostProcessorWrapperTest extends AbstractJmcPluginJavaTest {
  private static final Logger LOG = LoggerFactory.getLogger(AbstractJmcTest.class);

  // @Test
  void testDefault() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(DebugSamplerWrapper.builder().build())
                    .build())
            .addPostProcessor(
                TestJavaPostProcessorWrapper.builder().withName("testDefault").build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
    applicationTest.run();
    Assertions.assertEquals(
        0, LogMonitor.getErrors().size(), "Errors : " + LogMonitor.getErrors().toString());
  }

  @Test
  void testWithField() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(2)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(DebugSamplerWrapper.builder().build())
                    .build())
            .addPostProcessor(
                TestJavaPostProcessorWrapperWithField.builder().withName("testWithField").build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
    applicationTest.run();
    Assertions.assertEquals(
        1, LogMonitor.getErrors().size(), "Errors : " + LogMonitor.getErrors().toString());
    Assertions.assertEquals(
        "increment",
        LogMonitor.getErrors().get(0).getMessage(),
        "Errors : " + LogMonitor.getErrors().toString());
  }

  @Test
  void testWithFieldInit() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(2)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(DebugSamplerWrapper.builder().build())
                    .build())
            .addPostProcessor(
                TestJavaPostProcessorWrapperWithField.builder()
                    .withIncrement(100)
                    .withName("testWithField100")
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
    applicationTest.run();
    Assertions.assertEquals(
        1, LogMonitor.getErrors().size(), "Errors : " + LogMonitor.getErrors().toString());
    Assertions.assertEquals(
        "increment",
        LogMonitor.getErrors().get(0).getMessage(),
        "Errors : " + LogMonitor.getErrors().toString());
  }

  @Test
  void testParameters() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(DebugSamplerWrapper.builder().build())
                    .build())
            .addPostProcessor(
                TestJavaPostProcessorWrapperWithField.builder()
                    .addParameter("me", "you")
                    .addParameter("me1", "you1")
                    .withName("testWithParameters")
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
    applicationTest.run();
    Assertions.assertEquals(
        1, LogMonitor.getErrors().size(), "Errors : " + LogMonitor.getErrors().toString());
    Assertions.assertEquals(
        "me",
        LogMonitor.getErrors().get(0).getMessage(),
        "Errors : " + LogMonitor.getErrors().toString());
  }

  @SuperBuilder(setterPrefix = "with", toBuilder = true)
  static class TestJavaPostProcessorWrapper extends AbstractJavaPostProcessorWrapper {

    @Override
    public void execute(
        String label,
        JMeterContext ctx,
        JMeterVariables vars,
        Properties props,
        Map<String, String> parameters,
        Logger log,
        Sampler sampler,
        SampleResult prev) {

      log.info("TestJavaPostProcessorWrapper : ######################ME#####################");
      Assertions.assertNotNull(label);
      Assertions.assertNotNull(ctx);
      Assertions.assertNotNull(vars);
      Assertions.assertNotNull(props);
      Assertions.assertNotNull(parameters);
      Assertions.assertNotNull(log);
      Assertions.assertNotNull(sampler);
      Assertions.assertNotNull(prev);
    }
  }

  @SuperBuilder(setterPrefix = "with", toBuilder = true)
  static class TestJavaPostProcessorWrapperWithField extends AbstractJavaPostProcessorWrapper {

    private int increment;
    @XStreamOmitField @Default private Map myMap = new HashMap();

    @Override
    public void execute(
        String label,
        JMeterContext ctx,
        JMeterVariables vars,
        Properties props,
        Map<String, String> parameters,
        Logger log,
        Sampler sampler,
        SampleResult prev) {

      increment++;
      if (increment == 2 || increment == 102) {
        log.error("increment");
      }
      if (parameters.size() > 0) {
        log.error(parameters.keySet().iterator().next());
      }
    }
  }
}
