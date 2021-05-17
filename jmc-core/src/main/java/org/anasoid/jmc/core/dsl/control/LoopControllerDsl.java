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

import org.anasoid.jmc.core.wrapper.jmeter.control.LoopControllerWrapper;

/**
 * Dsl for LoopController.
 *
 * @see LoopControllerWrapper
 */
public final class LoopControllerDsl {

  private LoopControllerDsl() {}

  /**
   * Get LoopControllerWrapper builder.
   *
   * @return builder.
   */
  public static LoopControllerWrapper.LoopControllerWrapperBuilder<?, ?> loopControllerBuilder() {

    return LoopControllerWrapper.builder();
  }

  /**
   * Get loopController builder with name.
   *
   * @param name name.
   * @return builder.
   */
  public static LoopControllerWrapper.LoopControllerWrapperBuilder<?, ?> loopControllerBuilder(
      String name) {

    return loopControllerBuilder().withName(name);
  }

  /**
   * Get loopController builder with name.
   *
   * @param name name.
   * @param loops Number of iterations to use.
   * @return builder.
   */
  public static LoopControllerWrapper.LoopControllerWrapperBuilder<?, ?> loopControllerBuilder(
      String name, String loops) {

    return loopControllerBuilder().withName(name).withLoops(loops);
  }

  /**
   * Get loopController builder with name.
   *
   * @param name name.
   * @param loops Number of iterations to use.
   * @return builder.
   */
  public static LoopControllerWrapper.LoopControllerWrapperBuilder<?, ?> loopControllerBuilder(
      String name, int loops) {

    return loopControllerBuilder().withName(name).withLoops(loops);
  }
}
