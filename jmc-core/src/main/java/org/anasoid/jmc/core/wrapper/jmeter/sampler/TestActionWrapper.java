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
 * Date :   06-Jul-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.sampler;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.sampler.TestAction;
import org.apache.jmeter.sampler.gui.TestActionGui;

/**
 * Wrapper for TestAction.
 *
 * @see TestAction
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Flow Control Action")
public class TestActionWrapper extends AbstractSamplerWrapper<TestAction, TestActionGui>
    implements Validator {

  /** Pause / Stop / Stop Now / Go to next loop iteration . */
  @JmcProperty("ActionProcessor.action")
  @Getter
  @Setter
  @Default
  private Action action = Action.PAUSE;

  /** Current Thread / All Threads (ignored for Pause and Go to next loop iteration). */
  @JmcProperty("ActionProcessor.target")
  @Getter
  @Setter
  @Default
  private Target target = Target.CURRENT_THREAD;

  /** How long to pause for (milliseconds). */
  @JmcProperty("ActionProcessor.duration")
  @Getter
  @Setter
  @Default
  private String duration = "0";

  @Override
  public Class<?> getGuiClass() {
    return TestActionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return TestAction.class;
  }

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {

    // Duration
    if (!Action.PAUSE.equals(action) && !"0".equals(duration)) {
      throw new ConversionIllegalStateException("Duration is valid only for Pause action");
    }

    // Thread
    if (!Action.STOP.equals(action)
        && !Action.STOP_NOW.equals(action)
        && !Target.CURRENT_THREAD.equals(target)) {
      throw new ConversionIllegalStateException("Targte Thread is valid only for stop action");
    }
  }

  /** Stop Thread target. */
  public enum Target {
    CURRENT_THREAD(0),
    ALL_THREAD(2);

    private final Integer value;

    Target(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** Action. */
  public enum Action {
    STOP(0),
    PAUSE(1),
    STOP_NOW(2),
    /** Start next iteration of Thread Loop. */
    RESTART_NEXT_LOOP(3),
    /** Start next iteration of Current Loop. */
    START_NEXT_ITERATION_CURRENT_LOOP(4),
    /** Break Current Loop. */
    BREAK_CURRENT_LOOP(5);

    private final Integer value;

    Action(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** Builder. */
  public abstract static class TestActionWrapperBuilder<
          C extends TestActionWrapper, B extends TestActionWrapperBuilder<C, B>>
      extends AbstractSamplerWrapper.AbstractSamplerWrapperBuilder<
          TestAction, TestActionGui, C, B> {

    /** How long to pause for (milliseconds). */
    public B withDuration(String duration) {
      this.duration$value = duration;
      this.duration$set = true;
      return self();
    }

    /** How long to pause for (milliseconds). */
    public B withDuration(Integer duration) {
      return withDuration(String.valueOf(duration));
    }

    /** How long to pause for (milliseconds). */
    public B withDuration(Variable duration) {
      return withDuration(String.valueOf(duration.getValue()));
    }
  }
}
