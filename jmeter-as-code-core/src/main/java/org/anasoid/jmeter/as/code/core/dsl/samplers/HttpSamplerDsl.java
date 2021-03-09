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

package org.anasoid.jmeter.as.code.core.dsl.samplers;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;

/**
 * Dsl for HTTPSamplerProxyWrapper.
 *
 * @see HTTPSamplerProxyWrapper
 */
public final class HttpSamplerDsl {

  private HttpSamplerDsl() {}

  /**
   * Get HTTPSamplerProxy builder.
   *
   * @return builder.
   */
  public static HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> httpBuilder() {

    return HTTPSamplerProxyWrapper.builder();
  }

  /**
   * Get HTTPSamplerProxy builder with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> httpBuilder(
      String name, String path) {

    return httpBuilder().withName(name).withPath(path);
  }

  /**
   * Get HTTPSamplerProxy builder with domain, name and path.
   *
   * @param domain sampler domain.
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> httpBuilder(
      String domain, String name, String path) {

    return httpBuilder(name, path).withDomain(domain);
  }

  /**
   * Get HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return sampler.
   */
  public static HTTPSamplerProxyWrapper http(String name, String path) {

    return httpBuilder(name, path).build();
  }

  /**
   * Get HTTPSamplerProxy sampler with domain, name and path.
   *
   * @param domain sampler domain.
   * @param name sampler name.
   * @param path sampler path.
   * @return sampler.
   */
  public static HTTPSamplerProxyWrapper http(String domain, String name, String path) {

    return httpBuilder(domain, name, path).build();
  }
}
