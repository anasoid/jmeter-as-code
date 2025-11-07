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
import org.apache.jmeter.timers.PoissonRandomTimer;
import org.apache.jmeter.timers.gui.PoissonRandomTimerGui;

/**
 * Wrapper for PoissonRandomTimer.
 *
 * @see PoissonRandomTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Poisson Random Timer")
public class PoissonRandomTimerWrapper
    extends RandomTimerWrapper<PoissonRandomTimer, PoissonRandomTimerGui> {

  @Override
  public Class<?> getGuiClass() {
    return PoissonRandomTimerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return PoissonRandomTimer.class;
  }

  /** builder. */
  public abstract static class PoissonRandomTimerWrapperBuilder<
          C extends PoissonRandomTimerWrapper,
          B extends PoissonRandomTimerWrapper.PoissonRandomTimerWrapperBuilder<C, B>>
      extends RandomTimerWrapper.RandomTimerWrapperBuilder<
          PoissonRandomTimer, PoissonRandomTimerGui, C, B> {

    /** Lambda value in milliseconds. */
    public B withLambda(String lambda) {
      return withRange(lambda);
    }

    /** Lambda value in milliseconds. */
    public B withLambda(Integer lambda) {
      return withRange(String.valueOf(lambda));
    }

    /** Lambda value in milliseconds. */
    public B withLambda(Variable lambda) {
      return withRange(String.valueOf(lambda.getValue()));
    }
  }
}
