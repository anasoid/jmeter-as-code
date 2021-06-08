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
 * Date :   08-Jun-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads;

import com.blazemeter.jmeter.threads.AbstractDynamicThreadGroupModel;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.config.JmcConfig;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.threads.AbstractParentThreadGroupWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

/**
 * Wrapper for AbstractDynamicThreadGroupModel.
 *
 * @see AbstractDynamicThreadGroupModel
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractDynamicThreadGroupModelWrapper<
        F extends AbstractDynamicThreadGroupModel, G extends AbstractThreadGroupGui>
    extends AbstractParentThreadGroupWrapper<F, G> {

  /** Ram up time (seconds). */
  @JmcProperty(value = AbstractDynamicThreadGroupModel.RAMP_UP)
  @Getter
  @Setter
  @JmcNullAllowed
  private String rampUp;

  /** Ram up steps count. */
  @JmcProperty(value = AbstractDynamicThreadGroupModel.STEPS)
  @Getter
  @Setter
  @JmcNullAllowed
  private String steps;

  /** Thread Iterations limits. */
  @JmcProperty(value = AbstractDynamicThreadGroupModel.ITERATIONS)
  @Getter
  @Setter
  @JmcNullAllowed
  private String iterations;

  /** Hold target Rate time (seconds). */
  @JmcProperty(value = AbstractDynamicThreadGroupModel.HOLD)
  @Getter
  @Setter
  private String hold;

  /** Target Concurrency . */
  @JmcProperty(value = AbstractDynamicThreadGroupModel.TARGET_LEVEL)
  @Getter
  @Setter
  private String targetConcurrency;

  /** Log Threads status into file . */
  @XStreamOmitField @Getter @Setter String logFilename;

  @JmcProperty(value = AbstractDynamicThreadGroupModel.LOG_FILENAME)
  protected String getFilePath() {

    if (logFilename == null) {
      return "";
    }
    return JmcConfig.getResultRootFolder() + logFilename;
  }

  /** Builder. */
  @SuppressWarnings("PMD.TooManyMethods")
  public abstract static class AbstractDynamicThreadGroupModelWrapperBuilder<
          F extends AbstractDynamicThreadGroupModel,
          G extends AbstractThreadGroupGui,
          C extends AbstractDynamicThreadGroupModelWrapper<F, G>,
          B extends AbstractDynamicThreadGroupModelWrapperBuilder<F, G, C, B>>
      extends AbstractParentThreadGroupWrapper.AbstractParentThreadGroupWrapperBuilder<F, G, C, B> {

    /** Ram up time (seconds). */
    public B withRampUp(String rampUp) {
      this.rampUp = rampUp;
      return self();
    }

    /** Ram up time (seconds). */
    public B withRampUp(int rampUp) {
      return withRampUp(String.valueOf(rampUp));
    }

    /** Ram up time (seconds). */
    public B withRampUp(Variable rampUp) {
      return withRampUp(String.valueOf(rampUp.getValue()));
    }

    /** Ram up steps count. */
    public B withSteps(String steps) {
      this.steps = steps;
      return self();
    }

    /** Ram up steps count. */
    public B withSteps(int steps) {
      return withSteps(String.valueOf(steps));
    }

    /** Ram up steps count. */
    public B withSteps(Variable steps) {
      return withSteps(String.valueOf(steps.getValue()));
    }

    /** Thread Iterations limits. */
    public B withIterations(String iterations) {
      this.iterations = iterations;
      return self();
    }

    /** Thread Iterations limits. */
    public B withIterations(int iterations) {
      return withIterations(String.valueOf(iterations));
    }

    /** Thread Iterations limits. */
    public B withIterations(Variable iterations) {
      return withIterations(String.valueOf(iterations.getValue()));
    }

    /** Hold target Rate time (seconds). */
    public B withHold(String hold) {
      this.hold = hold;
      return self();
    }

    /** Hold target Rate time (seconds). */
    public B withHold(int hold) {
      return withHold(String.valueOf(hold));
    }

    /** Hold target Rate time (seconds). */
    public B withHold(Variable hold) {
      return withHold(String.valueOf(hold.getValue()));
    }

    /** Target Concurrency . */
    public B withTargetConcurrency(String targetConcurrency) {
      this.targetConcurrency = targetConcurrency;
      return self();
    }

    /** Target Concurrency . */
    public B withTargetConcurrency(int targetConcurrency) {
      return withTargetConcurrency(String.valueOf(hold));
    }

    /** Target Concurrency . */
    public B withTargetConcurrency(Variable targetConcurrency) {
      return withTargetConcurrency(String.valueOf(targetConcurrency.getValue()));
    }
  }
}
