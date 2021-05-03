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
 * Date :   11-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.config;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.config.RandomVariableConfig;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for RandomVariableConfig.
 *
 * @see RandomVariableConfig
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class RandomVariableConfigWrapper
    extends ConfigTestElementWrapper<RandomVariableConfig, TestBeanGUI> {

  @XStreamOmitField private static final long serialVersionUID = 1718898026893070845L;

  /** Configure the Random generator. minimum Value. */
  @JmcProperty("minimumValue")
  @Getter
  @Setter
  @Default
  private String minimumValue = "1";

  /** Configure the Random generator. maximum Value. */
  @JmcProperty("maximumValue")
  @Getter
  @Setter
  @JmcNullAllowed
  private String maximumValue;

  /** Configure the Random generator,Seed for Random function. */
  @JmcProperty("randomSeed")
  @Getter
  @Setter
  @JmcNullAllowed
  private String randomSeed;

  /** Output variable. Variable Name. */
  @JmcProperty("variableName")
  @Getter
  @Setter
  @JmcNullAllowed
  private Variable variable;

  /** Output variable. Output Format. */
  @JmcProperty("outputFormat")
  @Getter
  @Setter
  @JmcNullAllowed
  private String format;

  /** Per Thread(User) ?. */
  @JmcProperty("perThread")
  @Getter
  @Setter
  @Default
  private boolean perThread = false;

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RandomVariableConfig.class;
  }

  /** Builder. */
  public abstract static class RandomVariableConfigWrapperBuilder<
          C extends RandomVariableConfigWrapper, B extends RandomVariableConfigWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          RandomVariableConfig, TestBeanGUI, C, B> {

    /** Configure the Random generator. minimum Value. */
    public B withMinimumValue(Integer minimum) {
      return withMinimumValue(String.valueOf(minimum));
    }

    /** Configure the Random generator. minimum Value. */
    public B withMinimumValue(Variable minimum) {
      return withMinimumValue(String.valueOf(minimum.getValue()));
    }

    /** Configure the Random generator. minimum Value. */
    public B withMinimumValue(String minimum) {
      this.minimumValue$value = minimum;
      this.minimumValue$set = true;
      return self();
    }

    /** Configure the Random generator. maximum Value. */
    public B withMaximumValue(Integer maximum) {
      return withMaximumValue(String.valueOf(maximum));
    }

    /** Configure the Random generator. maximum Value. */
    public B withMaximumValue(Variable maximum) {
      return withMaximumValue(String.valueOf(maximum.getValue()));
    }

    /** Configure the Random generator. maximum Value. */
    public B withMaximumValue(String maximumValue) {
      this.maximumValue = maximumValue;
      return self();
    }

    /** Configure the Random generator,Seed for Random function. */
    public B withRandomSeed(Integer randomSeed) {
      return withRandomSeed(String.valueOf(randomSeed));
    }

    /** Configure the Random generator,Seed for Random function. */
    public B withRandomSeed(Variable randomSeed) {
      return withRandomSeed(String.valueOf(randomSeed.getValue()));
    }

    /** Configure the Random generator,Seed for Random function. */
    public B withRandomSeed(String randomSeed) {
      this.randomSeed = randomSeed;
      return self();
    }
  }
}
