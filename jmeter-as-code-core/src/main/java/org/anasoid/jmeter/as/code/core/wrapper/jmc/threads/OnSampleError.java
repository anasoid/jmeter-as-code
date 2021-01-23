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

package org.anasoid.jmeter.as.code.core.wrapper.jmc.threads;

/** Thread group on Sample Error type. */
public enum OnSampleError {

  /** Continue, i.e. ignore sampler errors */
  ON_SAMPLE_ERROR_CONTINUE("continue"),

  /** Start next loop for current thread if sampler error occurs. */
  ON_SAMPLE_ERROR_START_NEXT_LOOP("startnextloop"),

  /** Stop current thread if sampler error occurs. */
  ON_SAMPLE_ERROR_STOPTHREAD("stopthread"),

  /**
   * Stop test (all threads) if sampler error occurs, the entire test is stopped at the end of any
   * current samples.
   */
  ON_SAMPLE_ERROR_STOPTEST("stoptest"),

  /**
   * Stop test NOW (all threads) if sampler error occurs, the entire test is stopped abruptly. Any
   * current samplers are interrupted if possible.
   */
  ON_SAMPLE_ERROR_STOPTEST_NOW("stoptestnow");

  public final String value;

  private OnSampleError(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return value;
  }
}
