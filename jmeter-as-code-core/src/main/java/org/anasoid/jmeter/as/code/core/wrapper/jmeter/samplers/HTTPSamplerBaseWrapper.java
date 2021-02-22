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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPFileArgWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
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

  /** Server Name or IP. */
  @JmcProperty(HTTPSamplerBase.DOMAIN)
  @Getter
  @JmcNullAllowed
  private final String domain;
  /** Path. */
  @JmcProperty(HTTPSamplerBase.PATH)
  @JmcMandatory
  @Getter
  private final String path;

  /** Port Number. */
  @JmcProperty(value = HTTPSamplerBase.PORT, asString = true)
  @Getter
  @JmcNullAllowed
  private final Integer port;

  /** Protocol [http]. */
  @JmcProperty(value = HTTPSamplerBase.PROTOCOL, asString = true)
  @Getter
  @JmcNullAllowed
  private final String protocol;

  @JmcProperty(value = HTTPSamplerBase.CONTENT_ENCODING, asString = true)
  @Getter
  @JmcNullAllowed
  private final String contentEncoding;

  /** HTTP Method. */
  @JmcProperty(HTTPSamplerBase.METHOD)
  @Getter
  @Default
  private final HttpMethod method = HttpMethod.GET;

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

  @JmcProperty(value = HTTPSamplerBase.PROXYPORT, asString = true)
  @Getter
  private final Integer proxyPort;
  /** Proxy user. */
  @JmcProperty(HTTPSamplerBase.PROXYUSER)
  @Getter
  private final String proxyUser;

  /** Proxy pass. */
  @JmcProperty(HTTPSamplerBase.PROXYPASS)
  @Getter
  private final String proxyPass;

  /** Connect Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.CONNECT_TIMEOUT, asString = true)
  @Getter
  @JmcNullAllowed
  private final Integer connectTimeout;

  /** Response Timeouts (milliseconds). */
  @JmcProperty(value = HTTPSamplerBase.RESPONSE_TIMEOUT, asString = true)
  @Getter
  @JmcNullAllowed
  private final Integer responseTimeout;

  /** Follow redirects. */
  @JmcProperty(HTTPSamplerBase.FOLLOW_REDIRECTS)
  @Getter
  @Default
  private Boolean followRedirects = Boolean.TRUE;

  /** Redirect Automatically. */
  @JmcProperty(HTTPSamplerBase.AUTO_REDIRECTS)
  @Getter
  @Default
  private final Boolean autoRedirects = Boolean.FALSE;

  /** Use KeepAlive. */
  @JmcProperty(HTTPSamplerBase.USE_KEEPALIVE)
  @Getter
  @Default
  private final Boolean useKeepAlive = Boolean.TRUE;

  /** Use multipart/form-data. */
  @JmcProperty(HTTPSamplerBase.DO_MULTIPART_POST)
  @Getter
  @Default
  private final Boolean doMultipartPost = Boolean.FALSE;

  /** Browser-compatible headers. */
  @JmcProperty(HTTPSamplerBase.BROWSER_COMPATIBLE_MULTIPART)
  @Getter
  private final Boolean doBrowserCompatibleMultipart;

  /** Save response as MD5 hash. */
  @JmcProperty(HTTPSamplerBase.MD5)
  @Getter
  private final Boolean md5;

  @JmcProperty(HTTPSamplerBase.POST_BODY_RAW)
  @Getter
  private final Boolean postBodyRaw;

  /** Embedded Resources from HTML Files: Concurrent pool for parallel download. */
  @JmcProperty(value = HTTPSamplerBase.CONCURRENT_POOL, asString = true)
  @Default
  @Getter
  @JmcSkipDefault("6")
  private Integer concurrentPool = HTTPSamplerBase.CONCURRENT_POOL_SIZE;

  /** Embedded Resources from HTML Files: URLs must match. */
  @JmcProperty(value = HTTPSamplerBase.EMBEDDED_URL_RE, asString = true)
  @Getter
  @JmcNullAllowed
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
      return null;
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
      return null;
    }
  }

  @JmcCollection(
      value = "HTTPFileArgs.files",
      withElementProp = true,
      name = "HTTPsampler.Files",
      elementType = HTTPFileArgs.class,
      enabled = false)
  @Getter
  private final List<HTTPFileArgWrapper> filesArguments;

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

    /** hide method , generated by Lombok. */
    protected B withArguments(List<HTTPArgumentWrapper> arguments) {
      this.arguments$value = arguments;
      this.arguments$set = true;
      return self();
    }

    /** hide method , generated by Lombok. */
    protected B withFilesArguments(List<HTTPFileArgWrapper> filesArguments) {
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
        this.filesArguments = new ArrayList<>();
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
