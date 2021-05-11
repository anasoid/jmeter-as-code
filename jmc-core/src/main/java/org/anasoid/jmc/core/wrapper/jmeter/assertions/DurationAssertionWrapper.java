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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedAssertionWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.assertions.DurationAssertion;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.DurationAssertionGui;

/**
 * Wrapper for ResponseAssertion.
 *
 * @see ResponseAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
@JmcDefaultName("XPath Extractor")
public class DurationAssertionWrapper
    extends AbstractScopedAssertionWrapper<DurationAssertion, DurationAssertionGui>
    implements Validator {

  /** The maximum number of milliseconds each response is allowed before being marked as failed. */
  @JmcProperty("DurationAssertion.duration")
  @Getter
  @Setter
  @JmcMandatory
  private String duration;

  @Override
  public Class<?> getGuiClass() {
    return DurationAssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return DurationAssertion.class;
  }

  @Override
  public void validate() throws ConversionIllegalStateException {
    if (getScope() == Scope.VARIABLE) {
      throw new ConversionIllegalStateException("Scope variable is not accepted in " + this);
    }
  }

  /** Builder. */
  public abstract static class DurationAssertionWrapperBuilder<
          C extends DurationAssertionWrapper, B extends DurationAssertionWrapperBuilder<C, B>>
      extends AbstractScopedAssertionWrapper.AbstractScopedAssertionWrapperBuilder<
          DurationAssertion, DurationAssertionGui, C, B> {

    public B withDuration(String duration) {
      this.duration = duration;
      return self();
    }

    public B withDuration(Integer duration) {
      return withDuration(String.valueOf(duration));
    }

    public B withDuration(Variable duration) {
      return withDuration(String.valueOf(duration.getValue()));
    }
  }
}
