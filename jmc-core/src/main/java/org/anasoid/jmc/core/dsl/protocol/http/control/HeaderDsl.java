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

package org.anasoid.jmc.core.dsl.protocol.http.control;

import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control.HeaderWrapper;

/**
 * Dsl for HeaderWrapper.
 *
 * @see HeaderWrapper
 */
public final class HeaderDsl {

  private HeaderDsl() {}

  /**
   * Get Header builder.
   *
   * @param name name.
   * @param value value.
   * @return builder.
   */
  public static HeaderWrapper header(String name, String value) {

    return HeaderWrapper.builder().withName(name).withValue(value).build();
  }
}
