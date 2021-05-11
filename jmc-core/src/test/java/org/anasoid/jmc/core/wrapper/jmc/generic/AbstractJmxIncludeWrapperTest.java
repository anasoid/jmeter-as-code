package org.anasoid.jmc.core.wrapper.jmc.generic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmc.core.test.utils.xmlunit.filter.AttributesFilterManager;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.AssertionJmxIncludeWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SamplerJmxIncludeWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.wrapper.test.ParamSamplerJmxIncludeWrapperTesting;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;
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
 * Date :   03-Apr-2021
 */

class AbstractJmxIncludeWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/jmc/generic";

  @Test
  void testRegex() throws IOException {

    String content = readFile(PARENT_PATH + "/regex/regex.xml");

    AssertionJmxIncludeWrapper include =
        AssertionJmxIncludeWrapper.builder()
            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
            .build();

    String filtered = include.cleanup(content);

    Assertions.assertEquals("<Main/>", filtered);
  }

  @Test
  void testRegexFail() throws IOException {

    String content = readFile(PARENT_PATH + "/regex/regex.fail.xml");

    AssertionJmxIncludeWrapper include =
        AssertionJmxIncludeWrapper.builder()
            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
            .build();

    try {
      include.cleanup(content);
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("Format incorrect for node "));
    }
  }

  @Test
  void testOneNode() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        SamplerJmxIncludeWrapper.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/main.jmx");
  }

  @Test
  void testOneNodeParam() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        SamplerJmxIncludeWrapper.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.param.jmx")
                            .addParam("path", "mypath")
                            .build())
                    .build())
            .build();
    checkWrapper(testPlanWrapper, PARENT_PATH + "/main.jmx");
  }

  @Test
  void testOneNodeParamAttribute() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(ParamSamplerJmxIncludeWrapperTesting.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/main.jmx");
  }

  @Test
  void testOneNodeParamAttributeFailPath() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        ParamSamplerJmxIncludeWrapperTesting.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
                            .build())
                    .build())
            .build();
    try {
      toTmpFile(testPlanWrapper, "httpsampler_");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(
          e.getCause().getCause().getMessage().contains("is provided and  getDefaultPath "));
    }
  }

  @Test
  void testOneNodeParamFail() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        SamplerJmxIncludeWrapper.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.param.jmx")
                            .withParams(Map.of("path", "mypaths"))
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "node_");
    String expectedContent = readFile(PARENT_PATH + "/main.jmx");
    Diff diff =
        JmcXmlComparator.compare(
            expectedContent,
            wrapperContent,
            null,
            Arrays.asList(AttributesFilterManager.getCommentFilter()));
    Assertions.assertTrue(JmcXmlComparator.hasDifferences(diff), "node  not identical " + diff);
  }

  @Test
  void testMultiNode() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder().withName("first").withPath("").build())
                    .addSampler(
                        SamplerJmxIncludeWrapper.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/main.second.jmx");
  }

  @Test
  void testMultiNodeInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        SamplerJmxIncludeWrapper.builder()
                            .withPath(PARENT_PATH + "/node.http.sampler.jmx")
                            .build())
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder().withName("first").withPath("").build())
                    .build())
            .build();
    checkWrapper(testPlanWrapper, PARENT_PATH + "/main.second.inverse.jmx");
  }

  @Test
  void testTag() throws IOException {

    SamplerJmxIncludeWrapper sampler =
        SamplerJmxIncludeWrapper.builder()
            .withPath(PARENT_PATH + "/node.http.sampler.param.jmx")
            .addTags("tag")
            .build();

    Assertions.assertTrue(sampler.getTags().contains("tag"));
  }
}
