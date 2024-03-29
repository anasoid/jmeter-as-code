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
 * Date :   15-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java;

import java.io.Serializable;
import java.util.List;

/**
 * Main Interface for Java JavaTestElementExecutor. Each subclass will have it own executor
 * signature method.
 */
public interface JavaTestElementExecutor extends Serializable {

  /**
   * get Valid Parameters key.
   *
   * @return list of valid parameters, null to not check validity.
   */
  List<String> getParametersKey();

  /**
   * Executed on Start Test.
   */
  default void onStartTest() {}

  /**
   * Executed on End Test.
   */
  default void onEndTest() {}
}
