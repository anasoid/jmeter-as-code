package org.anasoid.jmc.core.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.anasoid.jmc.core.xstream.exceptions.ConversionConfigException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import uk.org.webcompere.systemstubs.environment.EnvironmentVariables;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

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
 * Date :   09-Jul-2021
 */
@ExtendWith(SystemStubsExtension.class)
class JmcConfigTest {

  @Test
  void testSetPropertyHierarchy() throws Exception {
    String key = "p15545455444";
    String key2 = "p145455548444";
    String key3 = "p145545654";
    JmcConfig.setProperty(key, "1");
    Assertions.assertEquals("1", JmcConfig.getProperty(key));
    try (AutoCloseable c = JmcConfig.createLocalJmcConfig()) {
      JmcConfig.setProperty(key2, "2");

      Assertions.assertEquals("1", JmcConfig.getProperty(key));
      Assertions.assertEquals("2", JmcConfig.getProperty(key2));
      try (AutoCloseable c2 = JmcConfig.createLocalJmcConfig()) {
        JmcConfig.setProperty(key3, "3");

        Assertions.assertEquals("1", JmcConfig.getProperty(key));
        Assertions.assertEquals("2", JmcConfig.getProperty(key2));
      }
      Assertions.assertNull(JmcConfig.getProperty(key3));
    }
    Assertions.assertEquals("1", JmcConfig.getProperty(key));
    Assertions.assertNull(JmcConfig.getProperty(key2));
    Assertions.assertNull(JmcConfig.getProperty(key3));
    JmcConfig.reset();
    Assertions.assertNull(JmcConfig.getProperty(key));
    Assertions.assertNull(JmcConfig.getProperty(key2));
  }

  @Test
  void testSysProperty() throws Exception {
    Assertions.assertEquals("p1", JmcConfig.getProperty("p"));
  }

  @Test
  void testSysEnvPropertyResource() throws Exception {

    new EnvironmentVariables(
            "JMC_CONFIGS",
            "org/anasoid/jmc/core/config/JmcConfigTest.env"
                + ":org/anasoid/jmc/core/config/JmcConfigTest1.env")
        .execute(
            () -> {
              new SystemProperties(
                      "jmc.configs",
                      "org/anasoid/jmc/core/config/JmcConfigTest.sys.prop"
                          + ":org/anasoid/jmc/core/config/JmcConfigTest1.sys.prop")
                  .execute(
                      () -> {
                        Assertions.assertNotNull(System.getProperty("jmc.configs"));
                        Assertions.assertNotNull(System.getenv("JMC_CONFIGS"));
                        try (AutoCloseable c =
                            JmcConfig.createLocalJmcConfig(
                                Map.of("override.map", "map"),
                                Arrays.asList("org/anasoid/jmc/core/config/JmcConfigTest.key"))) {
                          Assertions.assertEquals("test", JmcConfig.getProperty("env1"));
                          Assertions.assertEquals("test", JmcConfig.getProperty("env2"));
                          Assertions.assertEquals("1", JmcConfig.getProperty("overrideEnvOder"));
                          Assertions.assertEquals("map", JmcConfig.getProperty("override.map"));
                          Assertions.assertEquals("key", JmcConfig.getProperty("override.key"));
                          Assertions.assertEquals(
                              "system", JmcConfig.getProperty("override.by.system"));
                        }
                      });
            });

    Assertions.assertNull(JmcConfig.getProperty("env1"));
    Assertions.assertNull(JmcConfig.getProperty("env2"));
    Assertions.assertNull(JmcConfig.getProperty("overrideEnvOder"));
    Assertions.assertNull(JmcConfig.getProperty("override.by.system"));
    Assertions.assertNull(JmcConfig.getProperty("override.key"));
    Assertions.assertNull(JmcConfig.getProperty("override.map"));
    Assertions.assertNull(System.getProperty("jmc.configs"));
    Assertions.assertNull(System.getenv("JMC_CONFIGS"));
  }

  @Test
  void testWrongPathFail() throws Exception {

    try {
      try (AutoCloseable c1 = JmcConfig.createLocalJmcConfig(UUID.randomUUID().toString())) {
        // Nothing
      }
      Assertions.fail();
    } catch (ConversionConfigException e) {
      // Success
    }
  }

  @Test
  void testParamPath() throws Exception {
    try (AutoCloseable c =
        JmcConfig.createLocalJmcConfig(
            "org/anasoid/jmc/core/config/JmcConfigTest.env",
            "org/anasoid/jmc/core/config/JmcConfigTest1.env")) {
      try (AutoCloseable c1 =
          JmcConfig.createLocalJmcConfig(
              Map.of("override.key", "key"),
              Arrays.asList(
                  "org/anasoid/jmc/core/config/JmcConfigTest.sys.prop",
                  "org/anasoid/jmc/core/config/JmcConfigTest1.sys.prop"))) {
        Assertions.assertEquals("key", JmcConfig.getProperty("override.key"));
        Assertions.assertEquals("test", JmcConfig.getProperty("env1"));
        Assertions.assertEquals("test", JmcConfig.getProperty("env2"));
        Assertions.assertEquals("1", JmcConfig.getProperty("overrideEnvOder"));
        Assertions.assertEquals("system", JmcConfig.getProperty("override.by.system"));
      }
    }
    Assertions.assertNull(JmcConfig.getProperty("env1"));
    Assertions.assertNull(JmcConfig.getProperty("env2"));
    Assertions.assertNull(JmcConfig.getProperty("overrideEnvOder"));
    Assertions.assertNull(JmcConfig.getProperty("override.by.system"));
  }

  @Test
  void testParamMap() throws Exception {

    try (AutoCloseable c1 =
        JmcConfig.createLocalJmcConfig(Map.of("key", "value"), (List<String>) null)) {
      Assertions.assertEquals("value", JmcConfig.getProperty("key"));
    }
    try (AutoCloseable c1 = JmcConfig.createLocalJmcConfig(Map.of("key", "value"))) {
      Assertions.assertEquals("value", JmcConfig.getProperty("key"));
    }
    try (AutoCloseable c1 = JmcConfig.createLocalJmcConfig(Map.of("key", "value"))) {
      Assertions.assertEquals("value", JmcConfig.getProperty("key"));
    }
  }

  @Test
  void testBoolean() throws Exception {

    try (AutoCloseable c1 =
        JmcConfig.createLocalJmcConfig(
            Map.of("key", "true", "key2", "false", "key3", "str"), (List<String>) null)) {

      Assertions.assertTrue(JmcConfig.getBoolean("key"));
      Assertions.assertFalse(JmcConfig.getBoolean("key2"));

      JmcConfig.setBoolean("key", false);
      Assertions.assertFalse(JmcConfig.getBoolean("key"));

      JmcConfig.setBoolean("key", null);
      Assertions.assertNull(JmcConfig.getBoolean("key"));
      Assertions.assertTrue(JmcConfig.getBoolean("key", true));
      try {
        JmcConfig.getBoolean("key3");
        Assertions.fail();
      } catch (ConversionConfigException e) {
        // Success
      }
    }
  }

  @Test
  void testProperty() throws Exception {
    Assertions.assertEquals("val", JmcConfig.getProperty(UUID.randomUUID().toString(), "val"));
  }

  @Test
  void testInteger() throws Exception {

    try (AutoCloseable c1 =
        JmcConfig.createLocalJmcConfig(
            Map.of("key", "1", "key2", "2", "key3", "str"), (List<String>) null)) {

      Assertions.assertEquals(1, JmcConfig.getInteger("key"));
      Assertions.assertEquals(2, JmcConfig.getInteger("key2"));

      JmcConfig.setInteger("key", 3);
      Assertions.assertEquals(3, JmcConfig.getInteger("key"));

      JmcConfig.setInteger("key", null);
      Assertions.assertNull(JmcConfig.getInteger("key"));
      Assertions.assertEquals(5, JmcConfig.getInteger("key", 5));
      try {
        JmcConfig.getInteger("key3");
        Assertions.fail();
      } catch (ConversionConfigException e) {
        // Success
      }
    }
  }

  @Test
  void testParamPathFile() throws Exception {
    Assertions.assertNull(JmcConfig.getProperty("env1"));
    try (AutoCloseable c =
        JmcConfig.createLocalJmcConfig(
            System.getProperties().getProperty("user.dir")
                + "/src/test/resources/"
                + "org/anasoid/jmc/core/config/JmcConfigTest.env")) {

      Assertions.assertEquals("test", JmcConfig.getProperty("env1"));
    }
    Assertions.assertNull(JmcConfig.getProperty("env1"));
  }

  @Test
  void testDoubleCloseFail() throws Exception {
    JmcConfig.reset();
    Assertions.assertNull(JmcConfig.getProperty("env1"));
    AutoCloseable c = JmcConfig.createLocalJmcConfig();

    c.close();
    try {
      c.close();
      Assertions.fail();
    } catch (IllegalStateException e) {
      // Success
    }
  }

  @Test
  void testDoubleCloseLevelFail() throws Exception {
    JmcConfig.reset();

    AutoCloseable c = JmcConfig.createLocalJmcConfig();
    AutoCloseable c1 = JmcConfig.createLocalJmcConfig();

    try {
      c.close();
      Assertions.fail();
    } catch (IllegalStateException e) {
      // Success
    }
  }
}
