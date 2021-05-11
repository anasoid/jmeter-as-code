package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.HTMLAssertionWrapper.Doctype;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.HTMLAssertionWrapper.Format;
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
 * Date :   02-May-2021
 */

class HTMLAssertionWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/assertions";

  private static final String NODE_NAME = "HTMLAssertion";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(HTMLAssertionWrapper.builder().withName("HTML Assertion").build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/htmlAssertion.default.jmx", NODE_NAME);
  }

  @Test
  void testErrorOnly() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                HTMLAssertionWrapper.builder()
                    .withName("HTML Assertion")
                    .withErrorOnly(true)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/htmlAssertion.erroronly.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                HTMLAssertionWrapper.builder()
                    .withName("HTML Assertion inverse")
                    .withDoctype(Doctype.STRICT)
                    .withFormat(Format.XHTML)
                    .withErrorThreshold(10)
                    .withWarningThreshold(20)
                    .withFilename("my file")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/htmlAssertion.inverse.jmx", NODE_NAME);
  }
}
