package org.anasoid.jmc.core.application;

import java.util.UUID;
import org.anasoid.jmc.core.config.JmcConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
 * Date :   23-Jun-2021
 */

class JMeterHomeTest {

  @Test
  void testGetJmeterHomeJmc() {

    try (MockedStatic<JmcConfig> JmcConfigUtilities = Mockito.mockStatic(JmcConfig.class)) {

      JmcConfigUtilities.when(() -> JmcConfig.getProperty(JMeterHome.JMETER_HOME_PROPERTY))
          .thenReturn("home1");
      Assertions.assertEquals("home1", JMeterHome.getJmeterHome());
    }
  }

  @Test
  void testGetJmeterHomeSystem() {

    try (MockedStatic<JmcConfig> JmcConfigUtilities = Mockito.mockStatic(JmcConfig.class)) {

      JmcConfigUtilities.when(() -> JmcConfig.getProperty(JMeterHome.JMETER_HOME_PROPERTY))
          .thenReturn(null);
      Assertions.assertNotNull(JMeterHome.getJmeterHome());
    }
  }

  @Test
  void testisValidJmeterHomeNull() {

    Assertions.assertFalse(JMeterHome.isValidJmeterHome(null));
  }

  @Test
  void testisValidJmeterHomeFail() {

    Assertions.assertFalse(JMeterHome.isValidJmeterHome(UUID.randomUUID().toString()));
  }

  @Test
  void testisValidJmeterHomeSuccess() {

    Assertions.assertTrue(JMeterHome.isValidJmeterHome(JMeterHome.getJmeterHome()));
  }

  @Test
  void testGetJmeterHomeInitFail() {

    try (MockedStatic<JMeterHome> JMeterHomeUtilities = Mockito.mockStatic(JMeterHome.class)) {
      JMeterHomeUtilities.when(JMeterHome::getJmeterHome).thenReturn("");

      Assertions.assertFalse(JMeterHome.init());
    }
  }
}
