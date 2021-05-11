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
 * Date :   09-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.modifiers;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.modifiers.CounterConfig;
import org.apache.jmeter.modifiers.gui.CounterConfigGui;

/**
 * Wrapper for CounterConfig.
 *
 * @see CounterConfig
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Counter")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class CounterConfigWrapper extends ConfigTestElementWrapper<CounterConfig, CounterConfigGui>
    implements Validator {

  @XStreamOmitField private static final long serialVersionUID = -4321773956536604266L;

  /** Starting value. */
  @JmcProperty("CounterConfig.start")
  @Getter
  @Setter
  @JmcNullAllowed
  private String start;

  /** Maximum value. */
  @JmcProperty("CounterConfig.end")
  @Getter
  @Setter
  @JmcNullAllowed
  private String end;

  /** Increment. */
  @JmcProperty("CounterConfig.incr")
  @Getter
  @Setter
  @JmcNullAllowed
  private String increment;

  /** Variable Name. */
  @JmcProperty("CounterConfig.name")
  @Getter
  @Setter
  @JmcNullAllowed
  private Variable variable;

  @JmcNullAllowed
  @JmcProperty("CounterConfig.format")
  @Getter
  @Setter
  private String format;

  /** Track counter independently for each user. */
  @JmcProperty("CounterConfig.per_user")
  @Getter
  @Setter
  @Default
  private boolean perUser = false;

  /** =Reset counter on each Thread Group Iteration. */
  @JmcProperty("CounterConfig.reset_on_tg_iteration")
  @Getter
  @Setter
  @JmcSkipDefault("false")
  @Default
  private boolean resetOnEachIteration = false;

  @Override
  public Class<?> getGuiClass() {
    return CounterConfigGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return CounterConfig.class;
  }

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (!perUser && resetOnEachIteration) {

      throw new ConversionIllegalStateException(
          "When using perUser=false, resetOnEachIteration should not be used");
    }
  }

  /** Builder. */
  @SuppressWarnings({"PMD.TooManyMethods"})
  public abstract static class CounterConfigWrapperBuilder<
          C extends CounterConfigWrapper, B extends CounterConfigWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          CounterConfig, CounterConfigGui, C, B> {

    /** Increment. */
    public B withIncrement(Integer increment) {
      return withIncrement(String.valueOf(increment));
    }

    /** Increment. */
    public B withIncrement(Variable increment) {
      return withIncrement(String.valueOf(increment.getValue()));
    }

    /** Increment. */
    public B withIncrement(String increment) {
      this.increment = increment;
      return self();
    }

    /** Starting value. */
    public B withStart(Integer start) {
      return withStart(String.valueOf(start));
    }

    /** Starting value. */
    public B withStart(Variable start) {
      return withStart(String.valueOf(start));
    }

    /** Starting value. */
    public B withStart(String start) {
      this.start = start;
      return self();
    }

    /** Maximum value. */
    public B withEnd(Integer end) {
      return withEnd(String.valueOf(end));
    }

    /** Maximum value. */
    public B withEnd(Variable end) {
      return withEnd(String.valueOf(end.getValue()));
    }

    /** Maximum value. */
    public B withEnd(String end) {
      this.end = end;
      return self();
    }

    /** track counter independently for each user. */
    public B withPerUser(boolean perUser) {
      if (!perUser && this.resetOnEachIteration$set) {
        throw new ConversionIllegalStateException(
            "When using perUser=false, resetOnEachIteration should not be used");
      }
      if (!perUser) {
        withResetOnEachIteration(false);
      }
      this.perUser$value = perUser;
      this.perUser$set = true;
      return self();
    }

    /** reset counter on each thread group iteration. */
    public B withResetOnEachIteration(boolean resetOnEachIteration) {
      if (resetOnEachIteration && (!this.perUser$value && this.perUser$set)) {
        throw new ConversionIllegalStateException(
            "When using ConversionIllegalStateException=false,"
                + " resetOnEachIteration should not be used");
      }
      this.resetOnEachIteration$value = resetOnEachIteration;
      this.resetOnEachIteration$set = true;
      return self();
    }
  }
}
