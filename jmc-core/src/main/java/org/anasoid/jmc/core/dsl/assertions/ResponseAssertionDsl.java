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

package org.anasoid.jmc.core.dsl.assertions;

import java.util.Arrays;
import org.anasoid.jmc.core.wrapper.jmc.assertions.AssertionField;
import org.anasoid.jmc.core.wrapper.jmc.assertions.MatchingRule;
import org.anasoid.jmc.core.wrapper.jmeter.assertions.ResponseAssertionWrapper;

/**
 * Dsl for ResponseAssertion.
 *
 * @see ResponseAssertionWrapper
 */
public final class ResponseAssertionDsl {

  private ResponseAssertionDsl() {}

  /**
   * Get ResponseAssertionWrapper builder.
   *
   * @return builder.
   */
  public static ResponseAssertionWrapper.ResponseAssertionWrapperBuilder<?, ?> responseAssertion() {

    return ResponseAssertionWrapper.builder();
  }

  /**
   * Get ResponseAssertion builder with name.
   *
   * @param name name.
   * @return builder.
   */
  public static ResponseAssertionWrapper.ResponseAssertionWrapperBuilder<?, ?> responseAssertion(
      String name) {

    return responseAssertion().withName(name);
  }

  /**
   * Get ResponseAssertion builder with name and http status code.
   *
   * @param name name.
   * @param code http code (ex : 200, 500, ...).
   */
  public static ResponseAssertionWrapper httpCode(String name, int code) {

    return responseAssertion()
        .withName(name)
        .withFieldToTest(AssertionField.RESPONSE_CODE)
        .withMatchingRule(MatchingRule.MATCH)
        .addPattern(String.valueOf(code))
        .build();
  }

  /**
   * Get ResponseAssertion builder with name and http status code 200.
   *
   * @param name name.
   */
  public static ResponseAssertionWrapper httpCodeOK(String name) {
    return httpCode(name, 200);
  }

  /**
   * Get ResponseAssertion builder with name and http status code.
   *
   * @param name name.
   * @param text list of text to check present.
   */
  public static ResponseAssertionWrapper httpResponseContain(String name, String... text) {

    return responseAssertion()
        .withName(name)
        .withMatchingRule(MatchingRule.CONTAINS)
        .addPatterns(Arrays.asList(text))
        .build();
  }

  /**
   * Get ResponseAssertion builder with name and http status code.
   *
   * @param name name.
   * @param text list of text to check not present.
   */
  public static ResponseAssertionWrapper httpResponseNotContain(String name, String... text) {

    return responseAssertion()
        .withName(name)
        .withMatchingRule(MatchingRule.CONTAINS)
        .withNot(true)
        .addPatterns(Arrays.asList(text))
        .build();
  }
}
