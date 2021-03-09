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

package org.anasoid.jmeter.as.code.core.dsl.config;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;

/**
 * Dsl for ArgumentWrapper.
 *
 * @see ArgumentWrapper
 */
public final class ArgumentDsl {

  private ArgumentDsl() {}

  /**
   * Get ArgumentWrapper builder.
   *
   * @return builder.
   */
  public static ArgumentWrapper.ArgumentWrapperBuilder<?, ?> argumentBuilder() {

    return ArgumentWrapper.builder();
  }

  /**
   * Get Argument builder with name and value.
   *
   * @param name name.
   * @param value value.
   * @return builder.
   */
  public static ArgumentWrapper.ArgumentWrapperBuilder<?, ?> argumentBuilder(
      String name, String value) {

    return argumentBuilder().withName(name).withValue(value);
  }

  /**
   * Get Argument sampler with name and value.
   *
   * @param name name.
   * @param value value.
   * @return wrapper.
   */
  public static ArgumentWrapper argument(String name, String value) {

    return argumentBuilder(name, value).build();
  }
}
