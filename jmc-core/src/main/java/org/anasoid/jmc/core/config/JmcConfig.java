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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
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
  private static final String MAIN_CONFIG_FILE = "org/anasoid/jmc/core/config/jmc.properties";
  private static final String USER_CONFIG_FILE = "jmc-user.properties";
  private static final Properties properties = loadJmcProperties();

  private JmcConfig() {}

  /** Get Data root folder. */
  public static String getDataRootFolder() {
    return properties.getProperty("jmc.data.root.folder");
  }

  /** Get Script root folder. */
  public static String getScriptRootFolder() {
    return properties.getProperty("jmc.script.root.folder");
  }

  /** Get Result root folder. */
  public static String getResultRootFolder() {
    return properties.getProperty("jmc.result.root.folder");
  }

  /** Get Data csv fom resource, use only when executing from code source. */
  public static boolean isDataResource() {
    return "true".equalsIgnoreCase(properties.getProperty("jmc.data.resource"));
  }

  /** Get Script root folder. */
  public static boolean isScriptResource() {
    return "true".equalsIgnoreCase(properties.getProperty("jmc.script.resource"));
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
    return properties.getProperty("jmc.jmeter.properties.version");
  }

  private static Properties loadJmcProperties() {
    Properties p = new Properties();
    LOG.info("Loading properties");
    p.putAll(loadJmcProperties(MAIN_CONFIG_FILE));
    p.putAll(loadJmcProperties(USER_CONFIG_FILE));
    p.putAll(
        loadJmcProperties(
            System.getProperties().getProperty("user.home") + File.separator + USER_CONFIG_FILE));
    p.putAll(System.getProperties());
    return p;
  }

  private static Properties loadJmcProperties(String file) {
    Properties p = new Properties();
    try (InputStream is = Files.newInputStream(Paths.get(file))) {
      p.load(is);
      LOG.info("Loading properties file : {}", file);
    } catch (IOException e) {
      try (InputStream is = ClassLoader.getSystemResourceAsStream(file)) { // $NON-NLS-1$
        if (is == null) {
          LOG.info("Properties file : {} Not found", file);
          return p;
        }
        p.load(is);
        LOG.info("Loading resource file : {}", file);
      } catch (IOException ex) {
        LOG.info("Resource file : {} Not found", file);
      }
    }
    return p;
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @return null or value.
   */
  public static String getProperty(String key) {

    return properties.getProperty(key);
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @param defaultValue defaultValue, return if key not found.
   * @return value or defaultValue.
   */
  public static String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }
}
