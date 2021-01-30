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

import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
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
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.gui.HTTPArgumentsPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.protocol.http.util.HTTPFileArgs;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

@SuperBuilder(setterPrefix = "with")
@SuppressWarnings({"PMD.TooManyFields", "PMD.AbstractClassWithoutAnyMethod"})
public abstract class HTTPSamplerBaseWrapper<
        T extends HTTPSamplerBase, G extends AbstractSamplerGui>
    extends AbstractSamplerWrapper<T, G> {

  /** Server Name or IP. */
  @JmcProperty(HTTPSamplerBase.DOMAIN)
  @Getter
  private final String domain;
  /** Path. */
  @JmcProperty(HTTPSamplerBase.PATH)
  @JmcMandatory
  @Getter
  private final String path;

  /** Port Number. */
  @JmcProperty(HTTPSamplerBase.PORT)
  @Getter
  private final Integer port;

  /** Protocol [http]. */
  @JmcProperty(HTTPSamplerBase.PROTOCOL)
  @Getter
  private final String protocol;

  @JmcProperty(HTTPSamplerBase.CONTENT_ENCODING)
  @Getter
  private final String contentEncoding;

  /** HTTP Method. */
  @JmcProperty(HTTPSamplerBase.METHOD)
  @Getter
  private final HttpMethod method;

  @JmcProperty(HTTPSamplerBase.IMPLEMENTATION)
  @Getter
  private final Implementation implementation;

  /** Source Address Type. */
  @JmcProperty(HTTPSamplerBase.IP_SOURCE_TYPE)
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

  @JmcProperty(HTTPSamplerBase.PROXYPORT)
  @Getter
  private final Integer proxyPortInt;
  /** Proxy user. */
  @JmcProperty(HTTPSamplerBase.PROXYUSER)
  @Getter
  private final String proxyUser;

  /** Proxy pass. */
  @JmcProperty(HTTPSamplerBase.PROXYPASS)
  @Getter
  private final String proxyPass;

  /** Connect Timeouts (milliseconds). */
  @JmcProperty(HTTPSamplerBase.CONNECT_TIMEOUT)
  @Getter
  private final Integer connectTimeout;

  /** Response Timeouts (milliseconds). */
  @JmcProperty(HTTPSamplerBase.RESPONSE_TIMEOUT)
  @Getter
  private final Integer responseTimeout;

  /** Follow redirects. */
  @JmcProperty(HTTPSamplerBase.FOLLOW_REDIRECTS)
  @Getter
  private final Boolean followRedirects;

  /** Redirect Automatically. */
  @JmcProperty(HTTPSamplerBase.AUTO_REDIRECTS)
  @Getter
  private final Boolean autoRedirects;

  /** Use KeepAlive. */
  @JmcProperty(HTTPSamplerBase.USE_KEEPALIVE)
  @Getter
  private final Boolean useKeepAlive;

  /** Use multipart/form-data. */
  @JmcProperty(HTTPSamplerBase.DO_MULTIPART_POST)
  @Getter
  private final Boolean doMultipartPost;

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
  @JmcProperty(HTTPSamplerBase.CONCURRENT_POOL)
  @Default
  @Getter
  private Integer concurentPool = HTTPSamplerBase.CONCURRENT_POOL_SIZE;

  /** Embedded Resources from HTML Files: URLs must match. */
  @JmcProperty(HTTPSamplerBase.EMBEDDED_URL_RE)
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

  @JmcCollection(
      value = HTTPSamplerBase.ARGUMENTS,
      withElementProp = true,
      name = "HTTPsampler.Arguments",
      elementType = Arguments.class,
      guiclass = HTTPArgumentsPanel.class,
      testclass = Arguments.class)
  @Builder.Default
  @Getter
  @JmcEmptyAllowed
  private final List<HTTPArgumentWrapper> arguments = new ArrayList<>();

  @JmcCollection(
      value = "HTTPFileArgs.files",
      withElementProp = true,
      name = "HTTPsampler.Files",
      elementType = HTTPFileArgs.class)
  @Getter
  private final List<HTTPFileArgWrapper> hTTPFiles;
}
