package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.SizeAssertionWrapper.FieldtoTest;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.SizeAssertionWrapper.Operator;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.test.utils.SetterTestUtils;
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

class SizeAssertionWrapperXmlCoreTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/assertions";

  private static final String NODE_NAME = "SizeAssertion";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(SizeAssertionWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(SizeAssertionWrapper.builder().withSize(20).build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/sizeAssertion.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addAssertion(
                SizeAssertionWrapper.builder()
                    .withName("Size Assertion inverse")
                    .withSize(new Variable("size"))
                    .withFieldToTest(FieldtoTest.RESPONSE_HEADERS)
                    .withComparisonType(Operator.LESS_THAN)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/sizeAssertion.inverse.jmx", NODE_NAME);
  }
}
