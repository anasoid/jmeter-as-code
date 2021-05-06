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
 * Date :   06-May-2021
 */

package org.anasoid.jmeter.as.code.core.util.converters;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.SampleSaveConfigurationWrapper;
import org.apache.jmeter.samplers.SampleSaveConfiguration;

/** SampleSaveConfiguration converter. */
public class SampleSaveConfigurationConverter
    implements Converter<SampleSaveConfigurationWrapper, SampleSaveConfiguration> {

  @Override
  public SampleSaveConfiguration convert(
      SampleSaveConfigurationWrapper sampleSaveConfigurationWrapper) {

    SampleSaveConfiguration saveConfig = new SampleSaveConfiguration();

    saveConfig.setTime(sampleSaveConfigurationWrapper.isTime());
    saveConfig.setLatency(sampleSaveConfigurationWrapper.isLatency());
    saveConfig.setTimestamp(sampleSaveConfigurationWrapper.isTimestamp());
    saveConfig.setSuccess(sampleSaveConfigurationWrapper.isSuccess());
    saveConfig.setLabel(sampleSaveConfigurationWrapper.isLabel());
    saveConfig.setCode(sampleSaveConfigurationWrapper.isCode());
    saveConfig.setMessage(sampleSaveConfigurationWrapper.isMessage());
    saveConfig.setThreadName(sampleSaveConfigurationWrapper.isThreadName());
    saveConfig.setDataType(sampleSaveConfigurationWrapper.isDataType());
    saveConfig.setEncoding(sampleSaveConfigurationWrapper.isEncoding());
    saveConfig.setAssertions(sampleSaveConfigurationWrapper.isAssertions());
    saveConfig.setSubresults(sampleSaveConfigurationWrapper.isSubresults());
    saveConfig.setResponseData(sampleSaveConfigurationWrapper.isResponseData());
    saveConfig.setSamplerData(sampleSaveConfigurationWrapper.isSamplerData());
    saveConfig.setAsXml(sampleSaveConfigurationWrapper.isXml());
    saveConfig.setFieldNames(sampleSaveConfigurationWrapper.isFieldNames());
    saveConfig.setResponseHeaders(sampleSaveConfigurationWrapper.isResponseHeaders());
    saveConfig.setRequestHeaders(sampleSaveConfigurationWrapper.isRequestHeaders());
    saveConfig.setAssertionResultsFailureMessage(
        sampleSaveConfigurationWrapper.isAssertionResultsFailureMessage());
    saveConfig.setBytes(sampleSaveConfigurationWrapper.isBytes());
    saveConfig.setSentBytes(sampleSaveConfigurationWrapper.isSentBytes());
    saveConfig.setUrl(sampleSaveConfigurationWrapper.isUrl());
    saveConfig.setThreadCounts(sampleSaveConfigurationWrapper.isThreadCounts());
    saveConfig.setIdleTime(sampleSaveConfigurationWrapper.isIdleTime());
    saveConfig.setConnectTime(sampleSaveConfigurationWrapper.isConnectTime());
    saveConfig.setFileName(sampleSaveConfigurationWrapper.isFileName());
    saveConfig.setHostname(sampleSaveConfigurationWrapper.isHostname());
    saveConfig.setSampleCount(sampleSaveConfigurationWrapper.isSampleCount());

    return saveConfig;
  }
}
