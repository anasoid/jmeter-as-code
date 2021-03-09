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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.config;

import java.io.IOException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;

/**
 * Test for HttpDefaultsWrapper based on XML result comparison.
 *
 * @see HttpDefaultsWrapper
 */
class HttpDefaultsXMLWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/protocol/http/config";

  private static final String NODE_NAME = "ConfigTestElement";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addConfig(HttpDefaultsWrapper.builder().withName("HTTP Request Defaults").build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpconfig_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpdefault.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpconfig  not identical " + diff);
  }

  @Test
  void testBody() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addConfig(
                HttpDefaultsWrapper.builder()
                    .withName("HTTP Request Defaults")
                    .withBody("body")
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpconfig_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpdefault.body.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpconfig  not identical " + diff);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addConfig(
                HttpDefaultsWrapper.builder()
                    .withName("HTTP Request Defaults")
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
    String wrapperContent = toTmpFile(testPlanWrapper, "httpdefault_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpdefault.reverse.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpdefault  not identical " + diff);
  }
}