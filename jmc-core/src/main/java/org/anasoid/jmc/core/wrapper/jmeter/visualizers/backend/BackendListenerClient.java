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
 * Date :   07-May-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.visualizers.backend;

import java.util.HashMap;
import java.util.Map;

/** Backend Listener Client Implementation. */
@SuppressWarnings("PMD.DoubleBraceInitialization")
public enum BackendListenerClient {
  InfluxDBRawBackendListenerClient(
      "org.apache.jmeter.visualizers.backend.influxdb.InfluxDBRawBackendListenerClient",
      new HashMap<>() {
        {
          put(
              "influxdbMetricsSender",
              "org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender");
          put("influxdbUrl", "http://host_to_change:8086/write?db=jmeter");
          put("influxdbToken", "");
          put("measurement", "jmeter");
        }
      }),
  GraphiteBackendListenerClient(
      "org.apache.jmeter.visualizers.backend.graphite.GraphiteBackendListenerClient",
      new HashMap<>() {
        {
          put(
              "graphiteMetricsSender",
              "org.apache.jmeter.visualizers.backend.graphite.TextGraphiteMetricsSender");
          put("graphiteHost", "");
          put("graphitePort", "2003");
          put("rootMetricsPrefix", "jmeter.");
          put("summaryOnly", "true");
          put("samplersList", "");
          put("useRegexpForSamplersList", "false");
          put("percentiles", "90;95;99");
        }
      }),
  InfluxdbBackendListenerClient(
      "org.apache.jmeter.visualizers.backend.influxdb.InfluxdbBackendListenerClient",
      new HashMap<>() {
        {
          put(
              "influxdbMetricsSender",
              "org.apache.jmeter.visualizers.backend.influxdb.HttpMetricsSender");
          put("influxdbUrl", "http://host_to_change:8086/write?db=jmeter");
          put("application", "application name");
          put("measurement", "jmeter");
          put("summaryOnly", "false");
          put("samplersRegex", ".*");
          put("percentiles", "90;95;99");
          put("testTitle", "Test name");
          put("eventTags", "");
        }
      });

  private final String value;
  private final Map<String, String> defaultValue;

  BackendListenerClient(String value, Map<String, String> defaultValue) {
    this.value = value;
    this.defaultValue = defaultValue;
  }

  public String getValue() {
    return value;
  }

  public Map<String, String> getDefaultValue() {
    return defaultValue;
  }

  @Override
  public String toString() {
    return value;
  }
}
