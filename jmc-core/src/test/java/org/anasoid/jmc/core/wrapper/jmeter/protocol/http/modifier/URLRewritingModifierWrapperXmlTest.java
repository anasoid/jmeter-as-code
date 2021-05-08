package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.modifier;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
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
 * Date :   24-Apr-2021
 */

class URLRewritingModifierWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/protocol/http/modifier";

  private static final String NODE_NAME = "URLRewritingModifier";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPreProcessor(
                URLRewritingModifierWrapper.builder()
                    .withName("HTTP URL Re-writing Modifier")
                    .withArgumentName("session")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/httpurlrewritingmodifier.default.jmx",
        NODE_NAME);
  }

  @Test
  void testDefaultInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPreProcessor(
                URLRewritingModifierWrapper.builder()
                    .withName("HTTP URL Re-writing Modifier inverse")
                    .withCache(true)
                    .withEncode(true)
                    .withPathExtension(true)
                    .withPathExtensionNoEquals(true)
                    .withPathExtensionNoQuestionmark(true)
                    .withArgumentName("session")
                    .build())
            .build();

    checkWrapper(
        testPlanWrapper, PARENT_PATH + "/httpurlrewritingmodifier.default.inverse.jmx",
        NODE_NAME);
  }
}
