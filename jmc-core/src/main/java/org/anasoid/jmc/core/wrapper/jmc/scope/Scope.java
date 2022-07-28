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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.scope;

/** Scope of scoped test element. */
public enum Scope {

  /** Sub-samples only - only applies to the sub-samples. */
  CHILDREN("children"),

  /** Main sample and sub-samples - applies to both. */
  ALL("all"),

  /** Main sample only - only applies to the main sample. Default. */
  PARENT("parent"),

  /** Use Jmeter Variable. */
  VARIABLE("variable");

  private final String value;

  Scope(String value) {
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
