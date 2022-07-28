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
 * Date :   22-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.threads;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.enums.EnumToStringConverter;

/** Thread group on Sample Error type. */
@XStreamConverter(value = EnumToStringConverter.class)
public enum OnError {

  /** Continue, i.e. ignore sampler errors */
  ON_ERROR_CONTINUE("0"),

  /** Stop current thread if sampler error occurs. */
  ON_ERROR_STOPTHREAD("1"),

  /**
   * Stop test (all threads) if sampler error occurs, the entire test is stopped at the end of any
   * current samples.
   */
  ON_ERROR_STOPTEST("2"),

  /**
   * Stop test NOW (all threads) if sampler error occurs, the entire test is stopped abruptly. Any
   * current samplers are interrupted if possible.
   */
  ON_ERROR_STOPTEST_NOW("3"),

  /** Start next loop for current thread if sampler error occurs. */
  ON_ERROR_START_NEXT_THREAD_LOOP("4"),

  ON_ERROR_START_NEXT_ITERATION_OF_CURRENT_LOOP("5"),

  ON_ERROR_BREAK_CURRENT_LOOP("6");

  private final String value;

  OnError(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
