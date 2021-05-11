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

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmc.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Test;

/**
 * Test for HttpDefaultsWrapper based on XML result comparison.
 *
 * @see HttpDefaultsWrapper
 */
class HttpDefaultsXMLWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/protocol/http/config";

  private static final String NODE_NAME = "ConfigTestElement";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addConfig(HttpDefaultsWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpdefault.default.jmx", NODE_NAME);
  }

  @Test
  void testBody() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(HttpDefaultsWrapper.builder().withBody("body").build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpdefault.body.jmx", NODE_NAME);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                HttpDefaultsWrapper.builder()
                    .withComment("comment")
                    .withPath("/path")
                    .withDomain("server")
                    .withPort(443)
                    .withProtocol("https")
                    .withContentEncoding("UTF-8")
                    .withImageParser(true)
                    .withConcurrentDwn(true)
                    .withConcurrentPool(10)
                    .withMd5(true)
                    .withEmbeddedUrlRE("umatch")
                    .withEmbeddedUrlExcludeRE("unmatch")
                    .withIpSource("1.1.1.1")
                    .withIpSourceType(IpSourceType.DEVICE)
                    .withProxyScheme("https")
                    .withProxyHost("proxy")
                    .withProxyUser("puser")
                    .withProxyPass("pass")
                    .withProxyPort(3128)
                    .withImplementation(Implementation.Java)
                    .withConnectTimeout(1000)
                    .withResponseTimeout(2000)
                    .addArgument(
                        HTTPArgumentWrapper.builder()
                            .withName("param")
                            .withValue("value")
                            .withAlwaysEncoded(true)
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpdefault.reverse.jmx", NODE_NAME);
  }
}
