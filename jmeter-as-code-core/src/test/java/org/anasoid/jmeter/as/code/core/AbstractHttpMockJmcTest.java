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
 * Date :   25-Feb-2021
 */

package org.anasoid.jmeter.as.code.core;

import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.lanwen.wiremock.ext.WiremockResolver;
import ru.lanwen.wiremock.ext.WiremockResolver.Wiremock;
import ru.lanwen.wiremock.ext.WiremockUriResolver;
import ru.lanwen.wiremock.ext.WiremockUriResolver.WiremockUri;

/** Abstract class t test with wiremock. */
@ExtendWith({WiremockResolver.class, WiremockUriResolver.class})
public abstract class AbstractHttpMockJmcTest extends AbstractJmcTest {

  private String wiremockUri;
  private WireMockServer wiremockServer;

  /** Init wiremock. */
  @BeforeEach
  public void setup(@Wiremock WireMockServer server, @WiremockUri String uri) {
    server.stubFor(any(anyUrl()));
    wiremockServer = server;
    wiremockUri = uri;
  }

  protected String getWiremockUri() {
    return wiremockUri;
  }

  protected WireMockServer getWiremockServer() {
    return wiremockServer;
  }
}
