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
 * Date :   09-Mar-2021
 */

package org.anasoid.jmc.core.dsl.assertions;

import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.DurationAssertionWrapper;

/**
 * Dsl for DurationAssertion.
 *
 * @see DurationAssertionWrapper
 */
public final class DurationAssertionDsl {

  private DurationAssertionDsl() {}

  /**
   * Get DurationAssertionWrapper builder.
   *
   * @return builder.
   */
  public static DurationAssertionWrapper.DurationAssertionWrapperBuilder<?, ?>
      durationAssertionBuilder() {

    return DurationAssertionWrapper.builder();
  }

  /**
   * Get DurationAssertion builder with name.
   *
   * @param name name.
   * @param duration The maximum number of milliseconds each response is allowed before being marked
   *     as failed.
   */
  public static DurationAssertionWrapper durationAssertionBuilder(String name, String duration) {

    return durationAssertionBuilder().withName(name).withDuration(duration).build();
  }

  /**
   * Get DurationAssertion builder with name.
   *
   * @param name name.
   * @param duration The maximum number of milliseconds each response is allowed before being marked
   *     as failed.
   */
  public static DurationAssertionWrapper durationAssertionBuilder(String name, int duration) {

    return durationAssertionBuilder().withName(name).withDuration(duration).build();
  }

  /**
   * Get DurationAssertion builder with name.
   *
   * @param name name.
   * @param duration The maximum number of milliseconds each response is allowed before being marked
   *     as failed.
   */
  public static DurationAssertionWrapper durationAssertionBuilder(String name, Variable duration) {

    return durationAssertionBuilder().withName(name).withDuration(duration).build();
  }
}
