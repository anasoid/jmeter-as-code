package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.http.client.config.AuthMechanism;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Test;
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
 * Date :   11-Apr-2021
 */

class AuthManagerXmlWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/protocol/http/control";

  private static final String NODE_NAME = "AuthManager";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(AuthManagerWrapper.builder().withName("HTTP Authorization Manager").build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/authorizationmanager.default.jmx", NODE_NAME);
  }

  @Test
  void testReverseDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                AuthManagerWrapper.builder()
                    .withName("HTTP Authorization Manager inverse")
                    .withClearEachIteration(true)
                    .addAuthorization(
                        AuthorizationWrapper.builder()
                            .withUrl("url")
                            .withUsername("me")
                            .withPassword("pass")
                            .withDomain("mydomain")
                            .withRealm("corp")
                            .withMechanism(AuthMechanism.DIGEST)
                            .build())
                    .addAuthorization(
                        AuthorizationWrapper.builder()
                            .withUrl("url2")
                            .withUsername("me2")
                            .withPassword("pass")
                            .withDomain("mydomain")
                            .build())
                    .addAuthorization(AuthorizationWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/authorizationmanager.inverse.default.jmx", NODE_NAME);
  }
}
