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
 * Date :   07-Jul-2021
 */

package org.anasoid.jmc.core.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Jmc properties Containers. */
class JmcProperties {

  private static final Logger LOG = LoggerFactory.getLogger(JmcConfig.class);
  private static final String MAIN_CONFIG_FILE = "org/anasoid/jmc/core/config/jmc.properties";
  private static final String USER_CONFIG_FILE = "jmc-user.properties";
  private static final String SYSTEM_CONFIG_FILES_KEY = "jmc.configs";
  private static final String ENV_CONFIG_FILES_KEY = "JMC_CONFIGS";
  private static final String FILE_SEPARATOR = ":";

  private final Properties properties;

  protected JmcProperties() {
    this(null);
  }

  protected JmcProperties(JmcProperties jmcProperties, String... paths) {
    this(jmcProperties, null, paths);
  }

  protected JmcProperties(
      JmcProperties jmcProperties, Map<String, String> params, String... paths) {
    properties = loadJmcProperties(jmcProperties, params, paths);
  }

  private Properties loadJmcProperties(
      JmcProperties jmcProperties, Map<String, String> params, String... paths) {

    LOG.info("Loading properties");
    List<String> files = new ArrayList<>();
    files.add(MAIN_CONFIG_FILE);
    files.add(USER_CONFIG_FILE);
    files.add(System.getProperties().getProperty("user.home") + File.separator + USER_CONFIG_FILE);
    files.addAll(getJmcFiles(System.getProperties().getProperty(SYSTEM_CONFIG_FILES_KEY)));
    files.addAll(getJmcFiles(System.getenv(ENV_CONFIG_FILES_KEY)));

    Properties p = new Properties();
    if (jmcProperties != null) {
      jmcProperties.properties.entrySet().forEach(c -> p.put(c.getKey(), c.getValue()));
    }
    files.forEach(c -> p.putAll(getJmcProperties(c)));
    p.putAll(System.getProperties());
    if (paths != null) {

      Arrays.stream(paths).forEach(c -> p.putAll(getJmcProperties(c)));
    }
    if (params != null) {
      params.entrySet().stream().forEach(c -> p.put(c.getKey(), c.getValue()));
    }
    return p;
  }

  private static Properties getJmcProperties(String file) {
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

  private static List<String> getJmcFiles(String files) {
    List<String> results = new ArrayList<>();
    if (files == null) {
      return results;
    }
    Arrays.stream(files.split(FILE_SEPARATOR)).forEach(c -> results.add(c));
    return results;
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @return null or value.
   */
  protected String getProperty(String key) {

    return properties.getProperty(key);
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @param defaultValue defaultValue, return if key not found.
   * @return value or defaultValue.
   */
  protected String getProperty(String key, String defaultValue) {
    return properties.getProperty(key, defaultValue);
  }

  /**
   * set property to config.
   *
   * @param key key.
   * @param value value.
   */
  protected void setProperty(String key, String value) {
    properties.setProperty(key, value);
  }

  /**
   * get property from config.
   *
   * @param prefix prefix, keys start with "prefix." .
   * @return null or value.
   */
  protected Map<String, String> getPropertyPrefix(String prefix) {
    Map<String, String> result = new HashMap<>();
    String fullPrefix = prefix + ".";
    properties.entrySet().stream()
        .filter(c -> ((String) c.getKey()).startsWith(fullPrefix))
        .forEach(c -> result.put((String) c.getKey(), (String) c.getValue()));
    return result;
  }

  private Properties getProperties() {
    return properties;
  }
}
