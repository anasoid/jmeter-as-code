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
 * Date :   22-Mar-2021
 */

package org.anasoid.jmc.core.application.interceptors;

import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;

/** Prepare Interceptor to custom test before persistence. */
public interface PrepareInterceptor {

  /**
   * Check if this interceptor support the node in input.
   *
   * @param testElementWrapper node.
   * @return true if this interceptor support this node.
   */
  boolean support(TestElementWrapper<?> testElementWrapper);

  /**
   * prepare the input node.
   *
   * @param testElementWrapper node.
   */
  void prepare(TestElementWrapper<?> testElementWrapper);
}
