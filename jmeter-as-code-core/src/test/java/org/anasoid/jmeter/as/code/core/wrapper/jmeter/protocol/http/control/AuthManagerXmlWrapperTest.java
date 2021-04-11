package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.SetterTestUtils;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.http.client.config.AuthMechanism;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions.ResponseAssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;
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
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/protocol/http/control";

  private static final String NODE_NAME = "AuthManager";



  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(AuthManagerWrapper.builder().withName("HTTP Authorization Manager").build())
            .build();

    String wrapperContent = toTmpFile(testPlanWrapper, "authorizationmanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/authorizationmanager.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "authorizationmanager  not identical " + diff);
  }

  @Test
  void testReverseDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
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

    String wrapperContent = toTmpFile(testPlanWrapper, "authorizationmanager_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/authorizationmanager.inverse.default.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "authorizationmanager  not identical " + diff);
  }
}
