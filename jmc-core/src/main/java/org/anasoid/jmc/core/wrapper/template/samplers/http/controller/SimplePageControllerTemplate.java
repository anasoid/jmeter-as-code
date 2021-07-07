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

package org.anasoid.jmc.core.wrapper.template.samplers.http.controller;

import lombok.NonNull;
import org.anasoid.jmc.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmc.core.wrapper.jmeter.control.RecordingControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;
import org.anasoid.jmc.core.wrapper.template.samplers.AbstractSamplerControllerTemplate;
import org.anasoid.jmc.core.wrapper.template.samplers.http.SimplePageTemplate;

/**
 * Simple Page Controller that include {@link HTTPSamplerProxyWrapper} with name, path domain. can
 * be customize using inheritance to add config ...
 */
public class SimplePageControllerTemplate extends AbstractSamplerControllerTemplate {

  protected final String name;
  protected final String domain;
  protected final String path;
  protected final HttpMethod method;

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   */
  public SimplePageControllerTemplate(@NonNull String name, @NonNull String path) {
    this(name, path, HttpMethod.GET);
  }

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   * @param domain domain.
   */
  public SimplePageControllerTemplate(@NonNull String name, @NonNull String path, String domain) {

    this(name, path, domain, HttpMethod.GET);
  }

  /**
   * Constructor.
   *
   * @param name sampler name.
   * @param path path.
   * @param method http method.
   */
  public SimplePageControllerTemplate(
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
  public SimplePageControllerTemplate(
      @NonNull String name, @NonNull String path, String domain, @NonNull HttpMethod method) {
    this.name = name;
    this.path = path;
    this.domain = domain;
    this.method = method;
  }

  /** Prepare Main sampler builder. */
  protected void prepareSamplerBuilder(
      HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder<?, ?> builder) {}

  /** Prepare Main sampler wrapper. */
  protected void prepareSamplerWrapper(HTTPSamplerProxyWrapper samplerProxyWrapper) {} // NOSONAR

  @Override
  protected void initMainSampler(
      RecordingControllerWrapper.RecordingControllerWrapperBuilder controller) {
    controller.addSampler(createSampler());
  }

  protected AbstractJmcTemplate<
          HTTPSamplerProxyWrapper, HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder>
      createSampler() {
    return new SimplePageTemplate(getName(), getPath(), getDomain(), getMethod()) {
      @Override
      protected void prepareBuilder(
          HTTPSamplerProxyWrapper.HTTPSamplerProxyWrapperBuilder builder) {
        super.prepareBuilder(builder);
        prepareSamplerBuilder(builder);
      }

      @Override
      protected void prepareWrapper(HTTPSamplerProxyWrapper wrapper) {
        super.prepareWrapper(wrapper);
        prepareSamplerWrapper(wrapper);
      }
    };
  }

  protected String getName() {
    return name;
  }

  protected String getDomain() {
    return domain;
  }

  protected String getPath() {
    return path;
  }

  protected HttpMethod getMethod() {
    return method;
  }
}
