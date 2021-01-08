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
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

@SuperBuilder(setterPrefix = "with")
public abstract class HTTPSamplerBaseWrapper<
        T extends HTTPSamplerBase, G extends AbstractSamplerGui>
    extends AbstractSamplerWrapper<T, G> {

  private @Getter String domain;
  private @Mandatory @Getter String path;
  private @Getter Integer port;
  private @Getter String contentEncoding;
  private @Getter String method;
  private @Getter String implementation;
  private @Getter String embeddedUrlRE;
  private @Getter String embeddedUrlExcludeRE;
  private @Getter String proxyScheme;
  private @Getter String proxyHost;
  private @Getter Integer proxyPortInt;
  private @Getter String proxyUser;
  private @Getter String proxyPass;

  private @Getter Integer connectTimeout;
  private @Getter Integer responseTimeout;

  private @Getter Boolean followRedirects;
  private @Getter Boolean useKeepAlive;
  private @Getter Boolean doMultipartPost;
  private @Getter Boolean doBrowserCompatibleMultipart;
  private @Getter Boolean monitor;
  private @Getter Boolean md5;
  private @Getter Boolean postBodyRaw;
  private @Getter Boolean concurrentDwn;
  private @Getter Boolean imageParser;

  @Singular private List<HTTPFileArgWrapper> hTTPFiles;
  @Singular private List<HTTPArgumentWrapper> arguments;

  // setIpSourceType(int value)

  @Override
  public T convert() {
    return super.convert();
  }
}
