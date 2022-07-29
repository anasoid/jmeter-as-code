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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.samplers;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmc.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmc.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPFileArgWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.util.HTTPFileArgs;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

/**
 * Wrapper for HTTPSamplerBase.
 *
 * @see HTTPSamplerBase
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.TooManyFields", "PMD.AbstractClassWithoutAnyMethod"})
public abstract class HTTPSamplerBaseWrapper<
        T extends HTTPSamplerBase, G extends AbstractSamplerGui>
    extends AbstractSamplerWrapper<T, G> {

  @Getter @Default @XStreamOmitField
  private final List<HTTPArgumentWrapper> arguments = new ArrayList<>();

  @JmcCollection(
      value = "HTTPFileArgs.files",
      withElementProp = true,
      name = "HTTPsampler.Files",
      elementType = HTTPFileArgs.class,
      enabled = false)
  @Getter
  private final List<HTTPFileArgWrapper> filesArguments;
  /** Server Name or IP. */
  @JmcProperty(HTTPSamplerBase.DOMAIN)
  @Getter
  @Setter
  @JmcNullAllowed
  private String domain;
  /** Path. */
  @JmcProperty(HTTPSamplerBase.PATH)
  @JmcMandatory
  @Getter
  @Setter
  private String path;
  /** Port Number. */
  @JmcProperty(value = HTTPSamplerBase.PORT)
  @Getter
  @Setter
  @JmcNullAllowed
  private String port;
  /** Protocol [http]. */
  @JmcProperty(value = HTTPSamplerBase.PROTOCOL)
  @Getter
  @Setter
  @JmcNullAllowed
  private String protocol;

  @JmcProperty(value = HTTPSamplerBase.CONTENT_ENCODING)
  @Getter
  @Setter
  @JmcNullAllowed
  private String contentEncoding;
  /** HTTP Method. */
  @JmcProperty(HTTPSamplerBase.METHOD)
  @Getter
  @Setter
  @Default
  private HttpMethod method = HttpMethod.GET;

  @JmcProperty(HTTPSamplerBase.IMPLEMENTATION)
  @Getter
  @Setter
  private Implementation implementation;
  /** Source Address Type. */
  @JmcProperty(value = HTTPSamplerBase.IP_SOURCE_TYPE)
  @Getter
  @Setter
  @JmcSkipDefault("0")
  private IpSourceType ipSourceType;
  /** Source IP Address. */
  @JmcProperty(HTTPSamplerBase.IP_SOURCE)
  @Getter
  @Setter
  private String ipSource;
  /** Proxy scheme. */
  @JmcProperty(HTTPSamplerBase.PROXYSCHEME)
  @Getter
  @Setter
  private String proxyScheme;
  /** Proxy host. */
  @JmcProperty(HTTPSamplerBase.PROXYHOST)
  @Getter
  @Setter
  private String proxyHost;
  /** Proxy Port. */
  @JmcProperty(value = HTTPSamplerBase.PROXYPORT)
  @Getter
  @Setter
  private String proxyPort;
  /** Proxy user. */
  @JmcProperty(HTTPSamplerBase.PROXYUSER)
  @Getter
  @Setter
  private String proxyUser;
  /** Proxy pass. */
  @JmcProperty(HTTPSamplerBase.PROXYPASS)
  @Getter
  @Setter
  private String proxyPass;
  /** Connect Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.CONNECT_TIMEOUT)
  @Getter
  @Setter
  @JmcNullAllowed
  private String connectTimeout;
  /** Response Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.RESPONSE_TIMEOUT)
  @Getter
  @Setter
  @JmcNullAllowed
  private String responseTimeout;
  /** Follow redirects. */
  @JmcProperty(HTTPSamplerBase.FOLLOW_REDIRECTS)
  @Getter
  @Setter
  @Default
  private Boolean followRedirects = Boolean.TRUE;
  /** Redirect Automatically. */
  @JmcProperty(HTTPSamplerBase.AUTO_REDIRECTS)
  @Getter
  @Setter
  @Default
  private Boolean autoRedirects = Boolean.FALSE;
  /** Use KeepAlive. */
  @JmcProperty(HTTPSamplerBase.USE_KEEPALIVE)
  @Getter
  @Setter
  @Default
  private Boolean useKeepAlive = Boolean.TRUE;
  /** Use multipart/form-data. */
  @JmcProperty(HTTPSamplerBase.DO_MULTIPART_POST)
  @Getter
  @Setter
  @Default
  private Boolean doMultipartPost = Boolean.FALSE;
  /** Browser-compatible headers. */
  @JmcProperty(HTTPSamplerBase.BROWSER_COMPATIBLE_MULTIPART)
  @Getter
  @Setter
  private Boolean doBrowserCompatibleMultipart;
  /** Save response as MD5 hash. */
  @JmcProperty(HTTPSamplerBase.MD5)
  @Getter
  @Setter
  private Boolean md5;

  @JmcProperty(HTTPSamplerBase.POST_BODY_RAW)
  @Getter
  @Setter
  private Boolean postBodyRaw;
  /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
  @JmcProperty(value = HTTPSamplerBase.CONCURRENT_POOL)
  @Default
  @Getter
  @Setter
  @JmcSkipDefault("6")
  private String concurrentPool = String.valueOf(HTTPSamplerBase.CONCURRENT_POOL_SIZE);
  /** Embedded Resources from HTML Files: URLs must match. */
  @JmcProperty(value = HTTPSamplerBase.EMBEDDED_URL_RE)
  @Getter
  @Setter
  @JmcNullAllowed
  private String embeddedUrlRE;
  /** Embedded Resources from HTML Files: URLs must not match. */
  @JmcProperty(HTTPSamplerBase.EMBEDDED_URL_EXCLUDE_RE)
  @Getter
  @Setter
  private String embeddedUrlExcludeRE;
  /** Embedded Resources from HTML Files: Parallel downloads. */
  @JmcProperty(HTTPSamplerBase.CONCURRENT_DWN)
  @Getter
  @Setter
  private Boolean concurrentDwn;

  @JmcProperty(HTTPSamplerBase.IMAGE_PARSER)
  @Getter
  @Setter
  private Boolean imageParser;

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
  @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
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
  @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
  protected final List<HTTPArgumentWrapper> getArgumentsBody() {
    if (Boolean.TRUE.equals(postBodyRaw)) {
      return arguments;
    } else {
      return null; // NOSONAR
    }
  }

  /** Builder. */
  @SuppressWarnings({"PMD.AccessorMethodGeneration", "PMD.TooManyMethods"})
  public abstract static class HTTPSamplerBaseWrapperBuilder<
          T extends HTTPSamplerBase,
          G extends AbstractSamplerGui,
          C extends HTTPSamplerBaseWrapper<T, G>,
          B extends HTTPSamplerBaseWrapperBuilder<T, G, C, B>>
      extends AbstractSamplerWrapper.AbstractSamplerWrapperBuilder<T, G, C, B> {

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

    /** set autoRedirects. */
    public B withAutoRedirects(Boolean autoRedirects) {
      if (Boolean.TRUE.equals(autoRedirects)
          && Boolean.TRUE.equals(this.followRedirects$value)
          && this.followRedirects$set) {
        throw new ConversionIllegalStateException(
            "can't set to true followRedirects  and autoRedirects");
      }
      this.autoRedirects$value = autoRedirects;
      this.autoRedirects$set = true;
      if (Boolean.TRUE.equals(autoRedirects)) {
        withFollowRedirects(false);
      }
      return self();
    }

    /** set followRedirects. */
    public B withFollowRedirects(Boolean followRedirects) {
      if (Boolean.TRUE.equals(followRedirects) && Boolean.TRUE.equals(this.autoRedirects$value)) {
        throw new ConversionIllegalStateException(
            "can't set to true followRedirects  and autoRedirects");
      }
      this.followRedirects$value = followRedirects;
      this.followRedirects$set = true;
      return self();
    }

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

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private B withFilesArguments(List<HTTPFileArgWrapper> filesArguments) {
      this.filesArguments = filesArguments;
      return self();
    }

    /**
     * Add file arguments.
     *
     * @param filesArguments List of HTTPFileArgWrapper.
     */
    public B addFileArguments(Collection<HTTPFileArgWrapper> filesArguments) {
      if (this.filesArguments == null) {
        withFilesArguments(new ArrayList<>());
      }
      this.filesArguments.addAll(filesArguments);

      return self();
    }

    /**
     * Add file argument.
     *
     * @param filesArgument HTTPFileArgWrapper.
     */
    public B addFileArgument(HTTPFileArgWrapper filesArgument) {
      addFileArguments(Arrays.asList(filesArgument));
      return self();
    }

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void withArguments(List<HTTPArgumentWrapper> arguments) {
      this.arguments$value = arguments;
      this.arguments$set = true;
    } // NOSONAR

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
        withArguments(new ArrayList<>());
      }
      this.arguments$value.addAll(arguments);

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
