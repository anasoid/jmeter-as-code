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
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
import org.anasoid.jmc.core.wrapper.jmc.assertions.AssertionField;
import org.anasoid.jmc.core.wrapper.jmc.assertions.MatchingRule;
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Test;

class ResponseAssertionXmlWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/assertions";

  private static final String NODE_NAME = "ResponseAssertion";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ResponseAssertionWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addAssertion(ResponseAssertionWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/responseassertion.default.jmx", NODE_NAME);
  }

  @Test
  void testReverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                ResponseAssertionWrapper.builder()
                    .withFieldToTest(AssertionField.RESPONSE_CODE)
                    .withIgnoreStatus(true)
                    .withMatchingRule(MatchingRule.MATCH)
                    .withOr(true)
                    .withNot(true)
                    .withCustomMessage("mymessage")
                    .withScope(Scope.VARIABLE)
                    .withScopeVariable("myvar")
                    .addPattern("pattern1")
                    .addPattern("pattern2")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/responseassertion.reverse.jmx", NODE_NAME);
  }
}
