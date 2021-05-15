package org.anasoid.jmc.core.wrapper.jmeter.visualizers;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SampleSaveConfigurationWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
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
 * Date :   05-May-2021
 */

class AggregateReportWrapperXmlCoreTest extends AbstractJmcCoreTest {
  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/visualizers";

  private static final String NODE_NAME = "ResultCollector";

  @Test
  void testDefault() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addListener(AggregateReportWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/aggregateReport.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultAllTrue() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(
                AggregateReportWrapper.builder()
                    .withLogSuccess(true)
                    .withSaveConfiguration(
                        SampleSaveConfigurationWrapper.builder()
                            .withTime(true)
                            .withLatency(true)
                            .withTimestamp(true)
                            .withSuccess(true)
                            .withLabel(true)
                            .withCode(true)
                            .withMessage(true)
                            .withThreadName(true)
                            .withDataType(true)
                            .withEncoding(true)
                            .withAssertions(true)
                            .withSubresults(true)
                            .withResponseData(true)
                            .withSamplerData(true)
                            .withXml(true)
                            .withFieldNames(true)
                            .withResponseHeaders(true)
                            .withRequestHeaders(true)
                            .withAssertionResultsFailureMessage(true)
                            .withBytes(true)
                            .withSentBytes(true)
                            .withUrl(true)
                            .withFileName(true)
                            .withHostname(true)
                            .withThreadCounts(true)
                            .withSampleCount(true)
                            .withIdleTime(true)
                            .withConnectTime(true)
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/aggregateReport.alltrue.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseAllFalse() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(
                AggregateReportWrapper.builder()
                    .withLogError(true)
                    .withFilename("/result.jtl")
                    .withSaveHeaders(false)
                    .withUseGroupName(true)
                    .withSaveConfiguration(
                        SampleSaveConfigurationWrapper.builder()
                            .withTime(false)
                            .withLatency(false)
                            .withTimestamp(false)
                            .withSuccess(false)
                            .withLabel(false)
                            .withCode(false)
                            .withMessage(false)
                            .withThreadName(false)
                            .withDataType(false)
                            .withEncoding(false)
                            .withAssertions(false)
                            .withSubresults(false)
                            .withResponseData(false)
                            .withSamplerData(false)
                            .withXml(false)
                            .withFieldNames(false)
                            .withResponseHeaders(false)
                            .withRequestHeaders(false)
                            .withAssertionResultsFailureMessage(false)
                            .withBytes(false)
                            .withSentBytes(false)
                            .withUrl(false)
                            .withFileName(false)
                            .withHostname(false)
                            .withThreadCounts(false)
                            .withSampleCount(false)
                            .withIdleTime(false)
                            .withConnectTime(false)
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/aggregateReport.allfalse.inverse.jmx", NODE_NAME);
  }
}
