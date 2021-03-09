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

package org.anasoid.jmeter.as.code.core.dsl.threads;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;

/**
 * Dsl for ThreadGroupWrapper.
 *
 * @see ThreadGroupWrapper
 */
public final class ThreadGroupDsl {

  private ThreadGroupDsl() {}

  /**
   * Get ThreadGroupWrapper builder.
   *
   * @param name threadGroup name.
   * @return builder.
   */
  public static ThreadGroupWrapper.ThreadGroupWrapperBuilder<?, ?> threadGroupBuilder(String name) {

    return ThreadGroupWrapper.builder().withName(name);
  }

  /**
   * Get ThreadGroupWrapper builder with name and path.
   *
   * @param name threadGroup name.
   * @param numThreads number of Threads.
   * @param rampUp Ramp-up period (seconds).
   * @param loops number of loops.
   * @return builder.
   */
  public static ThreadGroupWrapper.ThreadGroupWrapperBuilder<?, ?> threadGroupBuilder(
      String name, int numThreads, int rampUp, int loops) {

    return threadGroupBuilder(name).withNumThreads(numThreads).withRampUp(rampUp).withLoops(loops);
  }
}
