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
 * Date :   06-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.data;

import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmeter.as.code.core.wrapper.test.SubChildTestingElementWrapperTesting;

/** Help to have Data Test. */
public final class DataTesting {

  private DataTesting() {}

  /** get default test Data. */
  public static ApplicationTest getDefautApplicationTest() {

    return ApplicationTestUtilsForTesting.getApplicationTest(getDefautTestElement());
  }

  /** get default ApplicationTest test Data. */
  public static ParentTestElementWrapperTesting getDefautTestElement() {

    // First sub
    SubChildTestingElementWrapperTesting subChildTestElementWrapperTesting =
        SubChildTestingElementWrapperTesting.builder()
            .withBb1(true)
            .withField("f")
            .withName("sub1")
            .build();

    // second sub
    SubChildTestingElementWrapperTesting subChildTestElementWrapperTesting2 =
        SubChildTestingElementWrapperTesting.builder()
            .withBb1(true)
            .withField("f")
            .withName("sub2")
            .build();

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withBb1(false)
            .withIi1(1)
            .withLl1(2L)
            .withField("field")
            .addArgument(ArgumentWrapper.builder().withName("param").withValue("value").build())
            .addChild(subChildTestElementWrapperTesting)
            .addChild(subChildTestElementWrapperTesting2)
            .build();

    return parentTestElementWrapperTesting;
  }
}
