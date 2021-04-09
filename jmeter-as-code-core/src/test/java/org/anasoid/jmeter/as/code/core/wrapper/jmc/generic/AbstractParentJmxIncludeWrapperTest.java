package org.anasoid.jmeter.as.code.core.wrapper.jmc.generic;

import java.io.IOException;
import java.util.Arrays;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.AttributesFilterManager;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control.HeaderManagerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.SamplerJmxIncludeWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
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
 * Date :   07-Apr-2021
 */

class AbstractParentJmxIncludeWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/jmc/generic";

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
                            .withPath(PARENT_PATH + "/node.http.sampler.simple.jmx")
                            .addConfig(
                                HeaderManagerWrapper.builder()
                                    .withName("HTTP Header Manager")
                                    .addHeader("head", "arg")
                                    .build())
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpsampler_");
    String expectedContent = readFile(PARENT_PATH + "/main.one.node.jmx");
    Diff diff =
        JmcXmlComparator.compare(
            expectedContent,
            wrapperContent,
            null,
            Arrays.asList(AttributesFilterManager.getCommentFilter()));
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpsampler  not identical " + diff);
  }
}
