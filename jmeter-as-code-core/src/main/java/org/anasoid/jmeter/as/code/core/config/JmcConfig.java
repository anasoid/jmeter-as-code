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
 * Date :   11-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.config;

import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;

public final class JmcConfig {

  private JmcConfig() {}

  /**
   * Gets the Version (from Jmeter).
   *
   * @return the version string
   */
  public static String getVersion() {
    return SaveService.getVERSION();
  }

  /**
   * Gets the JMeter Version (from Jmeter).
   *
   * @return the JMeter version string
   */
  public static String getJMeterVersion() {
    return JMeterUtils.getJMeterVersion();
  }

  /**
   * Gets the JMeter Properties Version.
   *
   * @return the JMeter Properties version string
   */
  public static String getPropertiesVersion() {
    return "5.0"; // NOSONAR
  }
}
