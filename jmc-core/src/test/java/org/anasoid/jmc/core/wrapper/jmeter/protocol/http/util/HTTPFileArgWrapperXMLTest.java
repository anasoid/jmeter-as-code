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

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
import org.anasoid.jmc.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;

class HTTPFileArgWrapperXMLTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/protocol/http/utils";

  private static final String NODE_NAME = "elementProp";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(HTTPFileArgWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(
                        HTTPSamplerProxyWrapper.builder()
                            .withName("HTTP Request")
                            .withPath("")
                            .addFileArgument(
                                HTTPFileArgWrapper.builder()
                                    .withName("file")
                                    .withPath("filepath")
                                    .withMimeType("image/png")
                                    .build())
                            .build())
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, "httpargument_");
    String wrapperContentFragment =
        getFragmentSingleNode(wrapperContent, "HTTPSamplerProxy", "collectionProp", NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/httpfilearg.default.jmx");
    String expectedContentFragment =
        getFragmentSingleNode(expectedContent, "HTTPSamplerProxy", "collectionProp", NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), "httpfielarg  not identical " + diff);
  }
}
