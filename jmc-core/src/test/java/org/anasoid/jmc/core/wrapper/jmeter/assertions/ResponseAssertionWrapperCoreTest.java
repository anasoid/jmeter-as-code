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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.ResponseAssertionWrapper.ResponseAssertionWrapperBuilder;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;
import org.anasoid.jmc.core.wrapper.test.ParentTestElementWrapperTesting;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResponseAssertionWrapperCoreTest extends AbstractJmcCoreTest {

  @Test
  void testAddAssertionTemplate() throws IOException {

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withField("field")
            .addAssertion(new MyAssertion())
            .build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);
    StringWriter wr = new StringWriter(); // NOPMD
    ParentTestElementWrapperTesting wrapper =
        (ParentTestElementWrapperTesting) applicationTest.toJmx(wr);
    Assertions.assertEquals(
        "Response Assertion", ((ResponseAssertionWrapper) wrapper.getChilds().get(0)).getName());
  }

  class MyAssertion
      extends AbstractJmcTemplate<ResponseAssertionWrapper, ResponseAssertionWrapperBuilder> {

    @Override
    protected void prepareBuilder(ResponseAssertionWrapperBuilder builder) {
      super.prepareBuilder(builder);
    }

    @Override
    protected JmcWrapperBuilder<?> init() {
      return ResponseAssertionWrapper.builder();
    }
  }
}
