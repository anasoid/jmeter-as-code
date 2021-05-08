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

package org.anasoid.jmc.core.dsl.control;

import org.anasoid.jmc.core.wrapper.jmeter.control.OnceOnlyControllerWrapper;

/**
 * Dsl for OnceOnlyController.
 *
 * @see OnceOnlyControllerWrapper
 */
public final class OnceOnlyControllerDsl {

  private OnceOnlyControllerDsl() {}

  /**
   * Get OnceOnlyControllerWrapper builder.
   *
   * @return builder.
   */
  public static OnceOnlyControllerWrapper.OnceOnlyControllerWrapperBuilder<?, ?>
      onceOnlyControllerBuilder() {

    return OnceOnlyControllerWrapper.builder();
  }

  /**
   * Get onceOnlyController builder with name.
   *
   * @param name name.
   * @return builder.
   */
  public static OnceOnlyControllerWrapper.OnceOnlyControllerWrapperBuilder<?, ?>
      onceOnlyControllerBuilder(String name) {

    return onceOnlyControllerBuilder().withName(name);
  }
}
