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
 * Date :   14-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import java.io.IOException;
import java.util.Arrays;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.AttributesFilterManager;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.HttpMethod;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.Implementation;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.samplers.IpSourceType;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.util.HTTPFileArgWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;

/**
 * Test for HTTPSamplerProxyWrapper based on XML result comparison.
 *
 * @see HTTPSamplerProxyWrapper
 */
class HTTPSamplerProxyWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/samplers/http";

  private static final String NODE_NAME = "HTTPSamplerProxy";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withName(DEFAULT_HTTP_REQUEST)
                            .withPath("")
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpsampler_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpsampler.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpsampler  not identical " + diff);
  }

  @Test
  void testBody() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withName(DEFAULT_HTTP_REQUEST)
                            .withPath("")
                            .withBody("body")
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpsampler_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpsampler.body.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpsampler  not identical " + diff);
  }

  @Test
  void testFullDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withName(DEFAULT_HTTP_REQUEST)
                            .withPath("")
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpsampler_");
    String expectedContent = readFile(PARENT_PATH + "/httpsampler.default.jmx");
    Diff diff =
        JmcXmlComparator.compare(
            expectedContent,
            wrapperContent,
            null,
            Arrays.asList(AttributesFilterManager.getCommentFilter()));
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpsampler  not identical " + diff);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName(DEFAULT_TEST_PLAN)
            .addThread(
                ThreadGroupWrapper.builder()
                    .withName(DEFAULT_THREAD_GROUP)
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withName(DEFAULT_HTTP_REQUEST)
                            .withComment("comment")
                            .withPath("/path")
                            .withDomain("server")
                            .withPort(443)
                            .withMethod(HttpMethod.POST)
                            .withProtocol("https")
                            .withContentEncoding("UTF-8")
                            .withAutoRedirects(true)
                            .withUseKeepAlive(false)
                            .withDoMultipartPost(true)
                            .withDoBrowserCompatibleMultipart(true)
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
                            .addFileArgument(
                                HTTPFileArgWrapper.builder()
                                    .withName("file")
                                    .withPath("filepath")
                                    .withMimeType("image/png")
                                    .build())
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpsampler_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpsampler.reverse.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpsampler  not identical " + diff);
  }
}
