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

package org.anasoid.jmc.core.wrapper.jmeter.timers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.timers.UniformRandomTimer;
import org.apache.jmeter.timers.gui.UniformRandomTimerGui;

/**
 * Wrapper for UniformRandomTimer.
 *
 * @see UniformRandomTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
@JmcDefaultName("Uniform Random Timer")
public class UniformRandomTimerWrapper
    extends RandomTimerWrapper<UniformRandomTimer, UniformRandomTimerGui> {

  @Override
  public Class<?> getGuiClass() {
    return UniformRandomTimerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return UniformRandomTimer.class;
  }

  /** builder. */
  public abstract static class UniformRandomTimerWrapperBuilder<
          C extends UniformRandomTimerWrapper,
          B extends UniformRandomTimerWrapper.UniformRandomTimerWrapperBuilder<C, B>>
      extends RandomTimerWrapperBuilder<UniformRandomTimer, UniformRandomTimerGui, C, B> {

    /** Random value in milliseconds. */
    public B withRandom(String random) {
      return withRange(random);
    }

    /** Random value in milliseconds. */
    public B withRandom(Integer random) {
      return withRange(String.valueOf(random));
    }

    /** Random value in milliseconds. */
    public B withRandom(Variable random) {
      return withRange(String.valueOf(random.getValue()));
    }
  }
}
