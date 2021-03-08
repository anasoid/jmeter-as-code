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

package org.anasoid.jmeter.as.code.core.wrapper.jmc.assertions;

/** Indicates how the text being tested is checked against the pattern. */
public enum MatchingRule {

  /** true if the whole text matches the regular expression pattern. */
  MATCH(1),

  /** true if the text contains the regular expression pattern. */
  CONTAINS(1 << 1),

  /** true if the whole text equals the pattern string (case-sensitive). */
  EQUALS(1 << 3),

  /** true if the text contains the pattern string (case-sensitive). */
  SUBSTRING(1 << 4);

  public final int value;

  private MatchingRule(int value) {
    this.value = value;
  }

  public int value() {
    return value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
