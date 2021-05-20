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
 * Date :   11-May-2021
 */

package org.anasoid.jmc.core.wrapper.template.samplers.http;

import lombok.NonNull;
import org.anasoid.jmc.core.dsl.samplers.HttpSamplerDsl;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;

/**
 * Helper template.
 *
 * @see HTTPSamplerProxyWrapper
 */
public class SimplePageTemplate
    extends AbstractJmcTemplate<
        HTTPSamplerProxyWrapper, HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder> {

  private final String name;
  private final String domain;
  private final String path;
  private final HttpMethod method;

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   */
  public SimplePageTemplate(@NonNull String name, @NonNull String path) {
    this(name, path, HttpMethod.GET);
  }

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   * @param domain domain.
   */
  public SimplePageTemplate(@NonNull String name, @NonNull String path, String domain) {

    this(name, path, domain, HttpMethod.GET);
  }

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   * @param method http method.
   */
  public SimplePageTemplate(
      @NonNull String name, @NonNull String path, @NonNull HttpMethod method) {
    this(name, path, null, method);
  }

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   * @param domain domain.
   * @param method http method.
   */
  public SimplePageTemplate(
      @NonNull String name, @NonNull String path, String domain, @NonNull HttpMethod method) {
    this.name = name;
    this.path = path;
    this.domain = domain;
    this.method = method;
  }

  @Override
  protected JmcWrapperBuilder<?> init() {
    return HttpSamplerDsl.http(name, path).withMethod(method).withDomain(domain);
  }
}
