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
 * Date :   09-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.dsl;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;

/**
 * Dsl for TestPlanWrapper.
 *
 * @see TestPlanWrapper
 */
@SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
public final class TestPlanDsl {

  private TestPlanDsl() {}

  /**
   * Get TestPlan builder.
   *
   * @return builder.
   */
  public static TestPlanWrapper.TestPlanWrapperBuilder<?, ?> testPlanBuilder() {

    return TestPlanWrapper.builder();
  }

  /**
   * Get TestPlan builder with name and path.
   *
   * @param name sampler name.
   * @return builder.
   */
  public static TestPlanWrapper.TestPlanWrapperBuilder<?, ?> testPlanBuilder(String name) {

    return testPlanBuilder().withName(name);
  }

  /**
   * Get TestPlan sampler with name and path.
   *
   * @param name sampler name.
   * @return testPlan.
   */
  public static TestPlanWrapper http(String name) {

    return testPlanBuilder(name).build();
  }
}
