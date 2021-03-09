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

package org.anasoid.jmeter.as.code.core.dsl.protocol.http.util;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;

/**
 * Dsl for HTTPArgumentWrapper.
 *
 * @see HTTPArgumentWrapper
 */
public final class HttpArgumentDsl {

  private HttpArgumentDsl() {}

  /**
   * Get HTTPArgumentWrapper builder.
   *
   * @return builder.
   */
  public static HTTPArgumentWrapper.HTTPArgumentWrapperBuilder<?, ?> httpArgumentBuilder() {

    return HTTPArgumentWrapper.builder();
  }

  /**
   * Get HTTPArgument builder with name and value.
   *
   * @param name name.
   * @param value value.
   * @return builder.
   */
  public static HTTPArgumentWrapper.HTTPArgumentWrapperBuilder<?, ?> httpArgumentBuilder(
      String name, String value) {

    return httpArgumentBuilder().withName(name).withValue(value);
  }

  /**
   * Get HTTPArgument with name and value.
   *
   * @param name name.
   * @param value value.
   * @return wrapper.
   */
  public static HTTPArgumentWrapper httpArgument(String name, String value) {

    return httpArgumentBuilder(name, value).build();
  }

  /**
   * Get HTTPArgument with name and value.
   *
   * @param name name.
   * @param value value.
   * @param alwaysEncoded alwaysEncoded.
   * @return wrapper.
   */
  public static HTTPArgumentWrapper httpArgument(String name, String value, boolean alwaysEncoded) {

    return httpArgumentBuilder(name, value).withAlwaysEncoded(alwaysEncoded).build();
  }
}
