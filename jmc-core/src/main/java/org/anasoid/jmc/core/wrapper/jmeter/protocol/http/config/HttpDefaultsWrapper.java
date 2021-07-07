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
 * Date :   05-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.config;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmc.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.protocol.http.config.gui.HttpDefaultsGui;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;

/**
 * Wrapper for HttpDefaultsGui. Doesn't have dedicated test class, only gui class.
 *
 * @see HttpDefaultsGui
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("HTTP Request Defaults")
@SuppressWarnings({"PMD.TooManyFields"})
public class HttpDefaultsWrapper
    extends ConfigTestElementWrapper<ConfigTestElement, HttpDefaultsGui> {

  /** Server Name or IP. */
  @JmcProperty(HTTPSamplerBase.DOMAIN)
  @Getter
  @JmcNullAllowed
  private final String domain;
  /** Path. */
  @JmcProperty(HTTPSamplerBase.PATH)
  @Getter
  @JmcNullAllowed
  private final String path;

  /** Port Number. */
  @JmcProperty(value = HTTPSamplerBase.PORT)
  @Getter
  @JmcNullAllowed
  private final String port;

  /** Protocol [http]. */
  @JmcProperty(value = HTTPSamplerBase.PROTOCOL)
  @Getter
  @JmcNullAllowed
  private final String protocol;

  @JmcProperty(value = HTTPSamplerBase.CONTENT_ENCODING)
  @Getter
  @JmcNullAllowed
  private final String contentEncoding;

  @JmcProperty(HTTPSamplerBase.IMPLEMENTATION)
  @Getter
  private final Implementation implementation;

  /** Source Address Type. */
  @JmcProperty(value = HTTPSamplerBase.IP_SOURCE_TYPE)
  @Getter
  @JmcSkipDefault("0")
  private final IpSourceType ipSourceType;

  /** Source IP Address. */
  @JmcProperty(HTTPSamplerBase.IP_SOURCE)
  @Getter
  private final String ipSource;

  /** Proxy scheme. */
  @JmcProperty(HTTPSamplerBase.PROXYSCHEME)
  @Getter
  private final String proxyScheme;
  /** Proxy host. */
  @JmcProperty(HTTPSamplerBase.PROXYHOST)
  @Getter
  private final String proxyHost;

  @JmcProperty(value = HTTPSamplerBase.PROXYPORT)
  @Getter
  private final String proxyPort;
  /** Proxy user. */
  @JmcProperty(HTTPSamplerBase.PROXYUSER)
  @Getter
  private final String proxyUser;

  /** Proxy pass. */
  @JmcProperty(HTTPSamplerBase.PROXYPASS)
  @Getter
  private final String proxyPass;

  /** Connect Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.CONNECT_TIMEOUT)
  @Getter
  @JmcNullAllowed
  private final String connectTimeout;

  /** Response Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.RESPONSE_TIMEOUT)
  @Getter
  @JmcNullAllowed
  private final String responseTimeout;

  /** Save response as MD5 hash. */
  @JmcProperty(HTTPSamplerBase.MD5)
  @Getter
  private final Boolean md5;

  @JmcProperty(HTTPSamplerBase.POST_BODY_RAW)
  @Getter
  private final Boolean postBodyRaw;
  /** Embedded Resources from HTML Files: URLs must match. */
  @JmcProperty(value = HTTPSamplerBase.EMBEDDED_URL_RE)
  @Getter
  private final String embeddedUrlRE;
  /** Embedded Resources from HTML Files: URLs must not match. */
  @JmcProperty(HTTPSamplerBase.EMBEDDED_URL_EXCLUDE_RE)
  @Getter
  private final String embeddedUrlExcludeRE;
  /** Embedded Resources from HTML Files: Parallel downloads. */
  @JmcProperty(HTTPSamplerBase.CONCURRENT_DWN)
  @Getter
  private final Boolean concurrentDwn;

  @JmcProperty(HTTPSamplerBase.IMAGE_PARSER)
  @Getter
  private final Boolean imageParser;

  @Getter @Default @XStreamOmitField
  private final List<HTTPArgumentWrapper> arguments = new ArrayList<>();
  /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
  @JmcProperty(value = HTTPSamplerBase.CONCURRENT_POOL)
  @Default
  @Getter
  private final String concurrentPool = String.valueOf(HTTPSamplerBase.CONCURRENT_POOL_SIZE);

  /**
   * Arguments format when User Defined Variables.
   *
   * @return list of arguments.
   */
  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "HTTPsampler.Arguments",
      elementType = Arguments.class,
      guiclass = HTTPArgumentsPanel.class,
      testclass = Arguments.class,
      testname = "User Defined Variables")
  @JmcMethodAlias("arguments")
  @JmcEmptyAllowed
  protected final List<HTTPArgumentWrapper> getArgumentsUser() {
    if (!Boolean.TRUE.equals(postBodyRaw)) {
      return arguments;
    } else {
      return null; // NOSONAR
    }
  }

  /**
   * Arguments format when body is used.
   *
   * @return list of arguments (One argument with empty name).
   */
  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "HTTPsampler.Arguments",
      elementType = Arguments.class,
      enabled = false)
  @JmcMethodAlias("arguments")
  @JmcEmptyAllowed
  protected final List<HTTPArgumentWrapper> getArgumentsBody() {
    if (Boolean.TRUE.equals(postBodyRaw)) {
      return arguments;
    } else {
      return null; // NOSONAR
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return HttpDefaultsGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ConfigTestElement.class;
  }

  /** Builder. */
  @SuppressWarnings({"PMD.AccessorMethodGeneration", "PMD.TooManyMethods"})
  public abstract static class HttpDefaultsWrapperBuilder<
          C extends HttpDefaultsWrapper, B extends HttpDefaultsWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          ConfigTestElement, HttpDefaultsGui, C, B> {

    /** set body. */
    public B withBody(String body) {
      if (!CollectionUtils.isEmpty(arguments$value)) {
        throw new ConversionIllegalStateException("can't set body and arguments on sampler");
      }
      addArgument(
          HTTPArgumentWrapper.builder().withName("").withValue(body).withUseEquals(null).build());
      withPostBodyRaw(true);
      return self();
    }

    /** set body. */
    protected B withPostBodyRaw(Boolean postBodyRaw) {
      this.postBodyRaw = postBodyRaw;
      return self();
    }

    /** hide method , generated by Lombok. */
    @SuppressWarnings({"PMD.UnusedFormalParameter", "PMD.UnusedPrivateMethod"})
    private void withArguments(List<HTTPArgumentWrapper> arguments) {} // NOSONAR

    /** Port Number. */
    public B withPort(int port) {
      return withPort(String.valueOf(port));
    }

    /** Port Number. */
    public B withPort(Variable port) {
      return withPort(String.valueOf(port.getValue()));
    }

    /** Port Number. */
    public B withPort(String port) {
      this.port = port;
      return self();
    }

    /** set Proxy Port. */
    public B withProxyPort(int proxyPort) {
      return withProxyPort(String.valueOf(proxyPort));
    }

    /** set Proxy Port. */
    public B withProxyPort(Variable proxyPort) {
      return withProxyPort(String.valueOf(proxyPort.getValue()));
    }

    /** set Proxy Port. */
    public B withProxyPort(String proxyPort) {
      this.proxyPort = proxyPort;
      return self();
    }

    /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
    public B withConcurrentPool(int pool) {
      return withConcurrentPool(String.valueOf(pool));
    }

    /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
    public B withConcurrentPool(Variable pool) {
      return withConcurrentPool(String.valueOf(pool.getValue()));
    }

    /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
    public B withConcurrentPool(String concurrentPool) {
      this.concurrentPool$value = concurrentPool;
      this.concurrentPool$set = true;
      return self();
    }

    /** set Connect Timeout. */
    public B withConnectTimeout(int timeout) {
      return withConnectTimeout(String.valueOf(timeout));
    }

    /** set Connect Timeout. */
    public B withConnectTimeout(Variable timeout) {
      return withConnectTimeout(String.valueOf(timeout.getValue()));
    }

    /** set Connect Timeout. */
    public B withConnectTimeout(String connectTimeout) {
      this.connectTimeout = connectTimeout;
      return self();
    }

    /** set Response Timeout. */
    public B withResponseTimeout(int timeout) {
      return withResponseTimeout(String.valueOf(timeout));
    }

    /** set Response Timeout. */
    public B withResponseTimeout(Variable timeout) {
      return withResponseTimeout(String.valueOf(timeout.getValue()));
    }

    /** set Response Timeout. */
    public B withResponseTimeout(String responseTimeout) {
      this.responseTimeout = responseTimeout;
      return self();
    }

    /**
     * Add arguments. Each argument consists of a name/value pair, as well as (optional) metadata.
     *
     * @param arguments List of arguments.
     */
    public B addArguments(Collection<HTTPArgumentWrapper> arguments) {
      if (Boolean.TRUE.equals(postBodyRaw)) {
        throw new ConversionIllegalStateException("can't set arguments with body on sampler");
      }
      if (!this.arguments$set) {
        this.arguments$value = new ArrayList<>();
      }
      this.arguments$value.addAll(arguments);
      this.arguments$set = true;

      return self();
    }

    /**
     * Add argument, consists of a name/value pair, default metadata is '='.
     *
     * @param name name.
     * @param value value.
     */
    public B addArgument(String name, String value) {
      addArgument(HTTPArgumentWrapper.builder().withName(name).withValue(value).build());
      return self();
    }

    /**
     * Add arguments. Each argument consists of a name/value pair, as well as (optional) metadata.
     *
     * @param argument argument. to be add
     */
    public B addArgument(HTTPArgumentWrapper argument) {
      return addArguments(Arrays.asList(argument));
    }
  }
}
