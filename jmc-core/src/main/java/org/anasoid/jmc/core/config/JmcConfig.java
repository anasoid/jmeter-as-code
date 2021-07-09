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

package org.anasoid.jmc.core.config;

import java.util.List;
import java.util.Map;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jmc Config to have access to all JMC configuration and Jmeter configuration.
 *
 * <pre>
 * Load properties by the following order resource than file:
 *
 * 1 -org/anasoid/jmc/core/config/jmc.properties
 * 2- jmc-user.properties
 * 3- {user.home}/jmc-user.properties
 * 4- System properties
 * </pre>
 */
public final class JmcConfig {

  private static final Logger LOG = LoggerFactory.getLogger(JmcConfig.class);

  private static final JmcPropertiesManager jmcPropertiesManager = new JmcPropertiesManager();

  private JmcConfig() {}

  /** Get Data root folder. */
  public static String getDataRootFolder() {
    return getJmcProperties().getProperty("jmc.data.root.folder");
  }

  /** Get Script root folder. */
  public static String getScriptRootFolder() {
    return getJmcProperties().getProperty("jmc.script.root.folder");
  }

  /** Get Result root folder. */
  public static String getResultRootFolder() {
    return getJmcProperties().getProperty("jmc.result.root.folder");
  }

  /** Get Data csv fom resource, use only when executing from code source. */
  public static boolean isDataResource() {
    return "true".equalsIgnoreCase(getJmcProperties().getProperty("jmc.data.resource"));
  }

  /** Get Script root folder. */
  public static boolean isScriptResource() {
    return "true".equalsIgnoreCase(getJmcProperties().getProperty("jmc.script.resource"));
  }

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
    return getJmcProperties().getProperty("jmc.jmeter.properties.version");
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @return null or value.
   */
  public static String getProperty(String key) {

    return getJmcProperties().getProperty(key);
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @param defaultValue defaultValue, return if key not found.
   * @return value or defaultValue.
   */
  public static String getProperty(String key, String defaultValue) {
    return getJmcProperties().getProperty(key, defaultValue);
  }

  /**
   * set property to config.
   *
   * @param key key.
   * @param value value.
   */
  public static void setProperty(String key, String value) {
    getJmcProperties().setProperty(key, value);
  }

  /**
   * get property from config.
   *
   * @param prefix prefix, keys start with "prefix." .
   * @return null or value.
   */
  public static Map<String, String> getPropertyPrefix(String prefix) {

    return getJmcProperties().getPropertyPrefix(prefix);
  }

  public static JmcProperties getJmcProperties() {
    return jmcPropertiesManager.getCurrentJmcProperties();
  }

  public static void reset() {
    jmcPropertiesManager.reset();
  }

  /**
   * Create local Jmc Config, local config is related to thread. Clone old config and add additional
   * properties.
   *
   * @param properties additional properties.
   * @param paths additional properties files, try to find file on resource than on file system.
   * @return AutoCloseable to close the local config.
   */
  public static AutoCloseable createLocalJmcConfig(
      Map<String, String> properties, String... paths) {
    return new JmcPropertiesManager(properties, paths);
  }

  public static AutoCloseable createLocalJmcConfig(String... paths) {
    return createLocalJmcConfig(null, paths);
  }

  public static AutoCloseable createLocalJmcConfig(Map<String, String> params, List<String> paths) {
    if (paths == null) {
      return createLocalJmcConfig();
    } else {
      return createLocalJmcConfig(params, paths.toArray(new String[0]));
    }
  }
}
