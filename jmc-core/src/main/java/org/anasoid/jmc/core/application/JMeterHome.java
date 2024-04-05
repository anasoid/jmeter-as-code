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
 * Date :   22-Jun-2021
 */

package org.anasoid.jmc.core.application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import org.anasoid.jmc.core.config.JmcConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Utils Class to setup Jmeter Home. */
public final class JMeterHome {

  private static final Logger LOG = LoggerFactory.getLogger(JMeterHome.class);
  protected static final String SLASH = System.getProperty("file.separator");
  protected static final String JMETER_HOME = "JMETER_HOME";
  protected static final String JMETER_HOME_PROPERTY = "jmeter.home";

  protected static final String JMETER_BIN_PATH = "/bin";

  private boolean initialized;

  private static JMeterHome instance;

  private JMeterHome() {}

  /** get instance. */
  public static JMeterHome getInstance() {
    synchronized (JMeterHome.class) {
      if (instance == null) {
        instance = new JMeterHome();
      }
    }
    return instance;
  }

  /** Init Jmeter Home. */
  protected boolean init() {

    String jmeterHomePath = getJmeterHome();

    if (StringUtils.isBlank(jmeterHomePath)) {
      LOG.info(
          "Jmeter is not configured by env variable {} or system variable {}",
          JMETER_HOME,
          JMETER_HOME_PROPERTY);
      jmeterHomePath = initLocalJmeter();
    }
    if (isValidJmeterHome(jmeterHomePath)) {
      // JMeter initialization (properties, log levels, locale, etc)
      LOG.info("Jmeter Home used from {}", jmeterHomePath);
      JMeterUtils.setJMeterHome(new File(jmeterHomePath).getPath());
      // loadJMeterProperties
      JMeterUtils.getProperties(new File(getJmeterProperties(jmeterHomePath)).getPath());
      loadJmcPropertiesToJmeter();
      initialized = true;
    } else {
      initialized = false;
      LOG.error(
          "Jmeter is not correctly configured,$JMETER_HOME is not correct"
              + " or missing jmeter.properties, on : {} -> {}",
          jmeterHomePath,
          getJmeterProperties(jmeterHomePath));
    }

    return initialized;
  }

  @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
  private String initLocalJmeter() {
    try {
      File homeDir = Files.createTempDirectory("jmc").toFile();
      LOG.info("Creating temporary Jmeter home at : {}", homeDir.getPath());

      File binDir = new File(homeDir, "bin");
      try (FileSystem fs =
          FileSystems.newFileSystem(
              getClass().getResource("/bin/jmeter.properties").toURI(), Collections.emptyMap())) {
        Path configBinDir = fs.getPath(JMETER_BIN_PATH);
        for (Path p : (Iterable<Path>) Files.walk(configBinDir)::iterator) {
          String parent = p.getParent().toString();
          if (JMETER_BIN_PATH.startsWith(parent)) {
            Path targetPath = binDir.toPath().resolve(configBinDir.relativize(p).toString());
            Files.copy(p, targetPath);
            targetPath.toFile().deleteOnExit();
          }
        }
      }

      return homeDir.getPath();
    } catch (IOException | URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  protected boolean isValidJmeterHome(String jmeterHomePath) {
    if (jmeterHomePath != null && new File(jmeterHomePath).exists()) {

      File jmeterProperties = new File(getJmeterProperties(jmeterHomePath));
      if (jmeterProperties.exists()) {
        return true;
      }
    }
    return false;
  }

  private static String getJmeterProperties(String jmeterHomePath) {
    return jmeterHomePath + SLASH + "bin" + SLASH + "jmeter.properties";
  }

  protected String getJmeterHome() {
    String jmeterHomePath = JmcConfig.getProperty(JMETER_HOME_PROPERTY);
    if (StringUtils.isBlank(jmeterHomePath)) {
      jmeterHomePath = System.getProperty(JMETER_HOME);
      if (jmeterHomePath == null) {
        jmeterHomePath = System.getenv(JMETER_HOME);
      }
    }
    return jmeterHomePath;
  }

  public void addProperties(Map<String, String> mapProperties) {
    mapProperties.forEach(JMeterUtils::setProperty);
  }

  private void loadJmcPropertiesToJmeter() {
    Map<String, String> jmcProperties = JmcConfig.getPropertyPrefix("jmeter");
    jmcProperties.remove(JMETER_HOME_PROPERTY);
    addProperties(jmcProperties);
  }

  /** is Jmeter Home initialized. */
  protected boolean isInit() {
    return initialized;
  }
}
