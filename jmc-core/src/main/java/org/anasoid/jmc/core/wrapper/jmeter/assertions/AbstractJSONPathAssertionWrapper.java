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
 * Date :   30-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.assertions.gui.AbstractAssertionGui;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Wrapper for JSONPathAssertion and JMESPathAssertion. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractJSONPathAssertionWrapper<
        G extends AbstractTestElement, F extends AbstractAssertionGui>
    extends AbstractBasicChildTestElementWrapper<G>
    implements JMeterGUIWrapper<F>, AssertionWrapper<G>, Validator {

  /**
   * Value to use for exact matching or regular expression if Match as regular expression is
   * checked.
   */
  @JmcProperty("EXPECTED_VALUE")
  @Getter
  @Setter
  @JmcNullAllowed
  private String expectedValue;

  /** Check the extracted JMESPath against an expected one. */
  @JmcProperty("JSONVALIDATION")
  @Getter
  @Setter
  private boolean additionallyAssertValue;

  /** expect the value to be null. */
  @JmcProperty("EXPECT_NULL")
  @Getter
  @Setter
  private boolean expectNull;

  /** Invert assertion (will fail if above conditions met). */
  @JmcProperty("INVERT")
  @Getter
  @Setter
  private boolean invert;

  /** use a regular expression for matching. */
  @JmcProperty("ISREGEX")
  @Getter
  @Setter
  @Default
  private boolean isRegex = true;

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (!additionallyAssertValue && (expectedValue != null || !isRegex)) {

      throw new ConversionIllegalStateException(
          "expectedValue or isRegex can't be used with additionallyAssertValue");
    }
    if (expectNull && (expectedValue != null || !isRegex)) {
      throw new ConversionIllegalStateException(
          "expectedValue or isRegex can't be used with expectNull");
    }
  }
}
