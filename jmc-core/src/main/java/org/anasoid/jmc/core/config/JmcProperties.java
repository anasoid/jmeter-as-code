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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.anasoid.jmc.core.xstream.exceptions.ConversionConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Jmc properties Containers. */
class JmcProperties {

  private static final Logger LOG = LoggerFactory.getLogger(JmcProperties.class);
  private static final String MAIN_CONFIG_FILE = "org/anasoid/jmc/core/config/jmc.properties";
  private static final String USER_CONFIG_FILE = "jmc-user.properties";
  private static final String SYSTEM_CONFIG_FILES_KEY = "jmc.configs";
  private static final String ENV_CONFIG_FILES_KEY = "JMC_CONFIGS";
  private static final String FILE_SEPARATOR = ":";

  private final Map<String, String> properties;

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

  @SuppressWarnings("PMD.PreserveStackTrace")
  private static Properties getJmcProperties(String file, boolean quite) {
    Properties p = new Properties();
    try (InputStream is = Files.newInputStream(Paths.get(file))) {
      p.load(is);
      LOG.info("Loading properties file : {}", file);
    } catch (IOException e) {
      LOG.warn("File : {} Not found try loading resource", file);
      try (InputStream is = ClassLoader.getSystemResourceAsStream(file)) { // $NON-NLS-1$
        if (is == null) {
          if (quite) {
            LOG.info("Properties file : {} Not found", file);
            return p;
          } else {
            throw new ConversionConfigException(
                MessageFormat.format("Config File not found : {0}", file));
          }
        }
        p.load(is);
        LOG.info("Loading resource file : {}", file);
      } catch (IOException ex) {
        // Not found will return null InputStream.
        throw new ConversionConfigException(
            MessageFormat.format("ERROR : Config File not found : {0}", file));
      }
    }
    return p;
  }

  private Map<String, String> loadJmcProperties(
      JmcProperties jmcProperties, Map<String, String> params, String... paths) {

    LOG.info("Loading properties");
    List<String> defaultFiles = new ArrayList<>();

    defaultFiles.add(MAIN_CONFIG_FILE);
    defaultFiles.add(USER_CONFIG_FILE);
    defaultFiles.add(
        System.getProperties().getProperty("user.home") + File.separator + USER_CONFIG_FILE);
    List<String> files = new ArrayList<>();
    files.addAll(getJmcFiles(System.getenv(ENV_CONFIG_FILES_KEY)));
    files.addAll(getJmcFiles(System.getProperties().getProperty(SYSTEM_CONFIG_FILES_KEY)));

    Properties p = new Properties();
    if (jmcProperties != null) {
      jmcProperties.properties.entrySet().forEach(c -> p.put(c.getKey(), c.getValue()));
    }
    defaultFiles.forEach(c -> p.putAll(getJmcProperties(c, true)));
    files.forEach(c -> p.putAll(getJmcProperties(c, false)));
    p.putAll(System.getProperties());
    if (paths != null) {

      Arrays.stream(paths).forEach(c -> p.putAll(getJmcProperties(c, false)));
    }
    if (params != null) {
      params.entrySet().stream().forEach(c -> p.put(c.getKey(), c.getValue()));
    }
    return new HashMap(p);
  }

  private static List<String> getJmcFiles(String files) {
    List<String> results = new ArrayList<>();
    if (files == null) {
      return results;
    }
    results.addAll(Arrays.asList(files.split(FILE_SEPARATOR)));
    return results;
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @return null or value.
   */
  protected String getProperty(String key) {

    return properties.get(key);
  }

  /**
   * get property from config.
   *
   * @param key key.
   * @param defaultValue defaultValue, return if key not found.
   * @return value or defaultValue.
   */
  protected String getProperty(String key, String defaultValue) {
    String value = properties.get(key);
    return value != null ? value : defaultValue;
  }

  /**
   * set property to config.
   *
   * @param key key.
   * @param value value.
   */
  protected void setProperty(String key, String value) {
    properties.put(key, value);
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
        .filter(c -> (c.getKey()).startsWith(fullPrefix))
        .forEach(c -> result.put(c.getKey(), c.getValue()));
    return result;
  }
}
