package org.anasoid.jmc.plugins.wrapper.java.processor;

import java.io.IOException;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.DebugSamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJmcPluginJavaTest;
import org.anasoid.jmc.plugins.wrapper.java.processor.executor.TestJavaPreProcessorWrapper;
import org.anasoid.jmc.plugins.wrapper.java.processor.executor.TestJavaPreProcessorWrapperWithField;
import org.anasoid.jmc.test.log.LogMonitor;
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
 * Date :   21-Jun-2021
 */

class AbstractJavaPreProcessorWrapperTest extends AbstractJmcPluginJavaTest {
  private static final Logger LOG =
      LoggerFactory.getLogger(AbstractJavaPreProcessorWrapperTest.class);

  @Test
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
            .addPreProcessor(TestJavaPreProcessorWrapper.builder().withName("testDefault").build())
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
            .addPreProcessor(
                TestJavaPreProcessorWrapperWithField.builder().withName("testWithField").build())
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
            .addPreProcessor(
                TestJavaPreProcessorWrapperWithField.builder()
                    .withIncrement(100)
                    .withName("testWithField100")
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "testWithFieldInit");
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
            .addPreProcessor(
                TestJavaPreProcessorWrapperWithField.builder()
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

  @Test
  void testParametersInvalid() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(DebugSamplerWrapper.builder().build())
                    .build())
            .addPreProcessor(
                TestJavaPreProcessorWrapperWithField.builder()
                    .addParameter("me", "you")
                    .addParameter("key", "you1")
                    .withName("testWithParameters")
                    .build())
            .build();
    try {
      ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Success
    }
  }
}
