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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;

/** Jmc properties Containers. */
class JmcPropertiesManager implements AutoCloseable {

  private static JmcProperties defaultJmcProperties = new JmcProperties();
  private ThreadLocal<List<JmcProperties>> localJmcPropertiesStack = new ThreadLocal<>();

  protected JmcPropertiesManager() {
    init(null);
  }

  protected JmcPropertiesManager(Map<String, String> params, String... paths) {
    init(params, paths);
  }

  /** close all local config, and refresh default. */
  protected void reset() {
    defaultJmcProperties = new JmcProperties();
    localJmcPropertiesStack.set(new ArrayList<>());
  }

  private void init(Map<String, String> params, String... paths) {

    if (localJmcPropertiesStack.get() == null) {
      localJmcPropertiesStack.set(new ArrayList<>());
    }
    List<JmcProperties> stack = localJmcPropertiesStack.get();
    JmcProperties current = defaultJmcProperties;
    if (stack.size() > 0) {
      current = stack.get(0);
    }
    localJmcPropertiesStack.get().add(0, new JmcProperties(current, params, paths));
  }

  @Override
  public void close() throws Exception {

    if (CollectionUtils.isNotEmpty(localJmcPropertiesStack.get())) {
      localJmcPropertiesStack.get().remove(0);
    } else {
      throw new IllegalStateException("JMC Properties is already closed");
    }
  }

  JmcProperties getCurrentJmcProperties() {
    if (CollectionUtils.isNotEmpty(localJmcPropertiesStack.get())) {
      return localJmcPropertiesStack.get().get(0);
    }
    return defaultJmcProperties;
  }
}
