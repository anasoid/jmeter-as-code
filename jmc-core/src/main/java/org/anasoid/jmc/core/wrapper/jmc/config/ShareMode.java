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
 * Date :   12-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.config;

/** Shared data mode. */
public enum ShareMode {

  /** All threads - (the default) the file is shared between all the threads. */
  SHARE_ALL("shareMode.all"),

  /** each file is opened once for each thread group in which the element appears. */
  SHARE_GROUP("shareMode.group"),

  /** each file is opened separately for each thread. */
  SHARE_THREAD("shareMode.thread");

  public final String value;

  private ShareMode(String value) {
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
