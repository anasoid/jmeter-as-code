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
import org.apache.jmeter.timers.GaussianRandomTimer;
import org.apache.jmeter.timers.gui.GaussianRandomTimerGui;

/**
 * Wrapper for GaussianRandomTimer.
 *
 * @see GaussianRandomTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public class GaussianRandomTimerWrapper
    extends RandomTimerWrapper<GaussianRandomTimer, GaussianRandomTimerGui> {

  @Override
  public Class<?> getGuiClass() {
    return GaussianRandomTimerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return GaussianRandomTimer.class;
  }

  /** builder. */
  public abstract static class GaussianRandomTimerWrapperBuilder<
          C extends GaussianRandomTimerWrapper, B extends GaussianRandomTimerWrapperBuilder<C, B>>
      extends RandomTimerWrapper.RandomTimerWrapperBuilder<
          GaussianRandomTimer, GaussianRandomTimerGui, C, B> {

    /** Deviation in milliseconds. */
    public B withDeviation(String deviation) {
      return withRange(deviation);
    }

    /** Deviation in milliseconds. */
    public B withDeviation(Integer deviation) {
      return withRange(String.valueOf(deviation));
    }

    /** Deviation in milliseconds. */
    public B withDeviation(Variable deviation) {
      return withRange(String.valueOf(deviation.getValue()));
    }
  }
}
