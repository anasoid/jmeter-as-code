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

package org.anasoid.jmc.core.dsl.samplers;

import org.anasoid.jmc.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder;

/**
 * Dsl for HTTPSamplerProxyWrapper.
 *
 * @see HTTPSamplerProxyWrapper
 */
public final class HttpSamplerDsl {

  private HttpSamplerDsl() {}

  /**
   * Get HTTPSamplerProxy builder with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> http(
      String name, String path) {

    return HTTPSamplerProxyWrapper.builder().withName(name).withPath(path);
  }

  /**
   * Get HTTPSamplerProxy builder with domain, name and path.
   *
   * @param domain sampler domain.
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> http(
      String domain, String name, String path) {

    return http(name, path).withDomain(domain);
  }

  /**
   * Get HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapperBuilder<?, ?> get(String name, String path) {

    return http(name, path);
  }

  /**
   * Post HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapperBuilder<?, ?> post(String name, String path) {

    return http(name, path).withMethod(HttpMethod.POST);
  }

  /**
   * Put HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapperBuilder<?, ?> put(String name, String path) {

    return http(name, path).withMethod(HttpMethod.PUT);
  }

  /**
   * OPTIONS HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapperBuilder<?, ?> options(String name, String path) {

    return http(name, path).withMethod(HttpMethod.OPTIONS);
  }

  /**
   * DELETE HTTPSamplerProxy sampler with name and path.
   *
   * @param name sampler name.
   * @param path sampler path.
   * @return builder.
   */
  public static HTTPSamplerProxyWrapperBuilder<?, ?> delete(String name, String path) {

    return http(name, path).withMethod(HttpMethod.DELETE);
  }
}
