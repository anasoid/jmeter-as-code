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

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.http.client.config.CookiePolicy;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Test;

class CookieManagerWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/protocol/http/control";

  private static final String NODE_NAME = "CookieManager";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withPath("")
                            .addConfig(CookieManagerWrapper.builder().build())
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpcookiemanager.default.jmx", NODE_NAME);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withPath("")
                            .addConfig(
                                CookieManagerWrapper.builder()
                                    .withClearEachIteration(true)
                                    .withControlledByThreadGroup(true)
                                    .withComment("comment")
                                    .withPolicy(CookiePolicy.STANDARD_STRICT)
                                    .addCookie(
                                        CookieWrapper.builder()
                                            .withName("myname")
                                            .withValue("myvalue")
                                            .withDomain("mydomain")
                                            .withPath("mypath")
                                            .withSecure(true)
                                            .build())
                                    .addCookie("myname2", "myvalue2")
                                    .build())
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpcookiemanager.reverse.jmx", NODE_NAME);
  }
}
