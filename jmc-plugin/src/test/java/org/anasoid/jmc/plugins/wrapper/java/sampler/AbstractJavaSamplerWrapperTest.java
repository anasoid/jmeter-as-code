package org.anasoid.jmc.plugins.wrapper.java.sampler;

import java.io.IOException;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.plugins.component.java.sampler.JavaSampler;
import org.anasoid.jmc.plugins.utils.ExecutorUtils;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJmcPluginJavaTest;
import org.anasoid.jmc.plugins.wrapper.java.sampler.executor.TestJavaPostProcessorCheckWrapper;
import org.anasoid.jmc.plugins.wrapper.java.sampler.executor.TestJavaSamplerOnStartEndWrapper;
import org.anasoid.jmc.plugins.wrapper.java.sampler.executor.TestJavaSamplerWrapper;
import org.anasoid.jmc.plugins.wrapper.java.sampler.executor.TestJavaSamplerWrapperWithField;
import org.anasoid.jmc.test.log.LogMonitor;
import org.apache.jmeter.config.Arguments;
import org.apache.jorphan.collections.HashTree;
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

class AbstractJavaSamplerWrapperTest extends AbstractJmcPluginJavaTest {

  private static final Logger LOG = LoggerFactory.getLogger(AbstractJavaSamplerWrapperTest.class);

  // @Test
  void testDefault() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(TestJavaSamplerWrapper.builder().withName("testDefault").build())
                    .build())
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
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder().withName("testWithField").build())
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
  void testWithFieldInit() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(2)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder()
                            .withIncrement(100)
                            .withName("testWithField100")
                            .build())
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
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder()
                            .addParameter("me", "you")
                            .addParameter("me1", "you1")
                            .withName("testWithParameters")
                            .build())
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
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder()
                            .addParameter("me", "you")
                            .addParameter("key", "you1")
                            .withName("testWithParameters")
                            .build())
                    .build())
            .build();
    try {
      ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "javaPost");
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Success
    }
  }

  @Test
  void testFilterProperties() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder()
                            .addParameter("me", "you")
                            .addParameter("me1", "you1")
                            .withName("testFilterProperties")
                            .withIncrement(199)
                            .build())
                    .build())
            .build();

    HashTree applicationTest = toHashTree(testPlanWrapper, "javaPost");

    JavaSampler javaSampler =
        (JavaSampler)
            applicationTest
                .get(applicationTest.getArray()[0])
                .get(applicationTest.get(applicationTest.getArray()[0]).getArray()[0])
                .getArray()[0];
    Arguments arguments = ExecutorUtils.extractAttributeArguments(javaSampler);
    Assertions.assertEquals(1, arguments.getArgumentCount());
    Assertions.assertEquals("199", arguments.getArgument(0).getValue());
  }

  @Test
  void testCheckContent() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(1)
                    .withDuration(1)
                    .withNumThreads(1)
                    .addSampler(
                        TestJavaSamplerWrapperWithField.builder()
                            .withName("testCheckContent")
                            .addPostProcessor(TestJavaPostProcessorCheckWrapper.builder().build())
                            .build())
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "testCheckContent");
    applicationTest.run();
    Assertions.assertEquals(
        0, LogMonitor.getErrors().size(), "Errors : " + LogMonitor.getErrors().toString());
    Assertions.assertTrue(
        2 <= LogMonitor.getLogs().size(), "Errors : " + LogMonitor.getLogs().toString());
    Assertions.assertTrue(
        LogMonitor.getLogs().stream()
            .filter(c -> c.getMessage().equals("TestJavaPostProcessorCheckWrapper"))
            .findFirst()
            .isPresent(),
        "Errors : " + LogMonitor.getLogs().toString());
    Assertions.assertTrue(
        LogMonitor.getLogs().stream()
            .filter(c -> c.getMessage().equals("TestJavaPostProcessorCheckWrapper:success"))
            .findFirst()
            .isPresent(),
        "Errors : " + LogMonitor.getLogs().toString());
  }

  @Test
  void testOnStartOnEnd() throws IOException {
    TestJavaSamplerOnStartEndWrapper.initialize();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(2)
                    .withDuration(1)
                    .withNumThreads(2)
                    .addSampler(
                        TestJavaSamplerOnStartEndWrapper.builder()
                            .withName("testOnStartOnEnd")
                            .build())
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "testOnStartOnEnd");
    applicationTest.run();
    Assertions.assertTrue(
        TestJavaSamplerOnStartEndWrapper.ON_END);
    Assertions.assertTrue(
        TestJavaSamplerOnStartEndWrapper.ON_START);
  }

  @Test
  void testOnStartOnEndMulti() throws IOException {
    TestJavaSamplerOnStartEndWrapper.initialize();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .withLoops(2)
                    .withDuration(1)
                    .withNumThreads(2)
                    .addSampler(
                        TestJavaSamplerOnStartEndWrapper.builder()
                            .withName("testOnStartOnEnd1")
                            .withNumberInstance(2)
                            .build())
                    .addSampler(
                        TestJavaSamplerOnStartEndWrapper.builder()
                            .withName("testOnStartOnEnd2")
                            .withNumberInstance(2)
                            .build())
                    .build())
            .build();
    ApplicationTest applicationTest = toApplicationTest(testPlanWrapper, "testOnStartOnEnd");
    applicationTest.run();
    Assertions.assertTrue(
        TestJavaSamplerOnStartEndWrapper.ON_END);
    Assertions.assertTrue(
        TestJavaSamplerOnStartEndWrapper.ON_START);
  }
}
