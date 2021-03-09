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

package org.anasoid.jmeter.as.code.core.dsl.protocol.http.control;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control.CookieManagerWrapper;

/**
 * Dsl for CookieManagerWrapper.
 *
 * @see CookieManagerWrapper
 */
public final class CookieManagerDsl {

  private CookieManagerDsl() {}

  /**
   * Get CookieManagerWrapper builder.
   *
   * @return builder.
   */
  public static CookieManagerWrapper.CookieManagerWrapperBuilder<?, ?> cookieManagerBuilder() {

    return CookieManagerWrapper.builder();
  }

  /**
   * Get CookieManager builder with name and path.
   *
   * @param name name.
   * @return builder.
   */
  public static CookieManagerWrapper.CookieManagerWrapperBuilder<?, ?> cookieManagerBuilder(
      String name) {

    return cookieManagerBuilder().withName(name);
  }

  /**
   * Get CookieManager builder with name and path.
   *
   * @param name name.
   * @param clearEachIteration clear Each Iteration.
   * @return builder.
   */
  public static CookieManagerWrapper.CookieManagerWrapperBuilder<?, ?> cookieManagerBuilder(
      String name, boolean clearEachIteration) {

    return cookieManagerBuilder(name).withClearEachIteration(clearEachIteration);
  }

  /**
   * Get CookieManager sampler with name and path.
   *
   * @param name name.
   * @return wrapper.
   */
  public static CookieManagerWrapper cookieManager(String name) {

    return cookieManagerBuilder(name).build();
  }

  /**
   * Get CookieManager with name and path.
   *
   * @param name name.
   * @param clearEachIteration clear Each Iteration.
   * @return wrapper.
   */
  public static CookieManagerWrapper cookieManager(String name, boolean clearEachIteration) {

    return cookieManagerBuilder(name, clearEachIteration).build();
  }
}
