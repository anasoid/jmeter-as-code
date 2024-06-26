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
 * Date :   24-Jun-2021
 */

package org.anasoid.jmc.core.application;

import java.util.HashMap;
import java.util.Map;

/** Jmeter utils to configure Jmeter for Testing. */
public final class JMeterTestUtils {

  private JMeterTestUtils() {}

  /** Set jmeter home. */
  public static void setupJMeterHome() {
    JMeterHome.getInstance().init();
  }

  public static void addProperties(Map<String, String> mapProperties) {
    JMeterHome.getInstance().addProperties(mapProperties);
  }

  /**
   * addProperties to Jmeter.
   *
   * @param key key.
   * @param value value.
   */
  public static void addProperty(String key, String value) {
    Map map = new HashMap();
    map.put(key, value);
    addProperties(map);
  }
}
