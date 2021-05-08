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

package org.anasoid.jmc.core.dsl.protocol.http.util;

import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPFileArgWrapper;

/**
 * Dsl for HTTPFileArgWrapper.
 *
 * @see HTTPFileArgWrapper
 */
public final class HttpFileArgDsl {

  private HttpFileArgDsl() {}

  /**
   * Get HTTPFileArgWrapper builder.
   *
   * @return builder.
   */
  public static HTTPFileArgWrapper.HTTPFileArgWrapperBuilder<?, ?> httpFileArgBuilder() {

    return HTTPFileArgWrapper.builder();
  }

  /**
   * Get HTTPFileArg builder with name and path.
   *
   * @param name name.
   * @param path path.
   * @return builder.
   */
  public static HTTPFileArgWrapper.HTTPFileArgWrapperBuilder<?, ?> httpFileArgBuilder(
      String name, String path) {

    return httpFileArgBuilder().withName(name).withPath(path);
  }

  /**
   * Get HTTPFileArg with name and value.
   *
   * @param name name.
   * @param path path.
   * @return wrapper.
   */
  public static HTTPFileArgWrapper httpFileArg(String name, String path) {

    return httpFileArgBuilder(name, path).build();
  }

  /**
   * Get HTTPFileArg with name and value.
   *
   * @param name name.
   * @param path path.
   * @param mimeType mimeType.
   * @return wrapper.
   */
  public static HTTPFileArgWrapper httpFileArg(String name, String path, String mimeType) {

    return httpFileArgBuilder(name, path).withMimeType(mimeType).build();
  }
}
