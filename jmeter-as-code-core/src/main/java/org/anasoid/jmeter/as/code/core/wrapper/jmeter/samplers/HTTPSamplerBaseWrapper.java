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

import java.util.List;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.Mandatory;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPFileArgWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

@SuperBuilder(setterPrefix = "with")
public abstract class HTTPSamplerBaseWrapper<
        T extends HTTPSamplerBase, G extends AbstractSamplerGui>
    extends AbstractSamplerWrapper<T, G> {

  @JmcProperty(HTTPSamplerBase.DOMAIN)
  private @Getter String domain;

  @JmcProperty(HTTPSamplerBase.PATH)
  private @Mandatory @Getter String path;

  @JmcProperty(HTTPSamplerBase.PORT)
  private @Getter Integer port;

  @JmcProperty(HTTPSamplerBase.CONTENT_ENCODING)
  private @Getter String contentEncoding;

  @JmcProperty(HTTPSamplerBase.METHOD)
  private @Getter String method;

  @JmcProperty(HTTPSamplerBase.IMPLEMENTATION)
  private @Getter String implementation;

  @JmcProperty(HTTPSamplerBase.EMBEDDED_URL_RE)
  private @Getter String embeddedUrlRE;

  @JmcProperty(HTTPSamplerBase.EMBEDDED_URL_EXCLUDE_RE)
  private @Getter String embeddedUrlExcludeRE;

  @JmcProperty(HTTPSamplerBase.PROXYSCHEME)
  private @Getter String proxyScheme;

  @JmcProperty(HTTPSamplerBase.PROXYHOST)
  private @Getter String proxyHost;

  @JmcProperty(HTTPSamplerBase.PROXYPORT)
  private @Getter Integer proxyPortInt;

  @JmcProperty(HTTPSamplerBase.PROXYUSER)
  private @Getter String proxyUser;

  @JmcProperty(HTTPSamplerBase.PROXYPASS)
  private @Getter String proxyPass;

  @JmcProperty(HTTPSamplerBase.CONNECT_TIMEOUT)
  private @Getter Integer connectTimeout;

  @JmcProperty(HTTPSamplerBase.RESPONSE_TIMEOUT)
  private @Getter Integer responseTimeout;

  @JmcProperty(HTTPSamplerBase.FOLLOW_REDIRECTS)
  private @Getter Boolean followRedirects;

  @JmcProperty(HTTPSamplerBase.USE_KEEPALIVE)
  private @Getter Boolean useKeepAlive;

  @JmcProperty(HTTPSamplerBase.DO_MULTIPART_POST)
  private @Getter Boolean doMultipartPost;

  @JmcProperty(HTTPSamplerBase.BROWSER_COMPATIBLE_MULTIPART)
  private @Getter Boolean doBrowserCompatibleMultipart;

  @JmcProperty(HTTPSamplerBase.MONITOR)
  private @Getter Boolean monitor;

  @JmcProperty(HTTPSamplerBase.MD5)
  private @Getter Boolean md5;

  @JmcProperty(HTTPSamplerBase.POST_BODY_RAW)
  private @Getter Boolean postBodyRaw;

  @JmcProperty(HTTPSamplerBase.CONCURRENT_DWN)
  private @Getter Boolean concurrentDwn;

  @JmcProperty(HTTPSamplerBase.IMAGE_PARSER)
  private @Getter Boolean imageParser;

  @Singular private List<HTTPFileArgWrapper> hTTPFiles;
  @Singular private List<HTTPArgumentWrapper> arguments;

  // setIpSourceType(int value)

  @Override
  public T convert() {
    return super.convert();
  }
}
