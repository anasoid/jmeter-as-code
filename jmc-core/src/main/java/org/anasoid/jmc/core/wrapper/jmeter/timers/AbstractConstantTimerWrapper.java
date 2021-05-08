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

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.AbstractTimerGui;

/**
 * Wrapper for subclass of ConstantTimer.
 *
 * @see ConstantTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public abstract class AbstractConstantTimerWrapper<
        G extends ConstantTimer, F extends AbstractTimerGui>
    extends AbstractBasicChildTestElementWrapper<G>
    implements JMeterGUIWrapper<F>, TimerWrapper<G> {

  /** Number of milliseconds to pause. */
  @JmcProperty("ConstantTimer.delay")
  @Getter
  @Setter
  @Default
  private String delay = "300";

  /** builder. */
  public abstract static class AbstractConstantTimerWrapperBuilder<
          G extends ConstantTimer,
          F extends AbstractTimerGui,
          C extends AbstractConstantTimerWrapper<G, F>,
          B extends AbstractConstantTimerWrapperBuilder<G, F, C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          G, C, B> {

    /** Number of milliseconds to pause in addition to the calculate delay. */
    public B withDelay(String delay) {
      this.delay$value = delay;
      this.delay$set = true;
      return self();
    }

    /** Number of milliseconds to pause in addition to the calculate delay. */
    public B withDelay(Integer delay) {
      return withDelay(String.valueOf(delay));
    }

    /** Number of milliseconds to pause in addition to the calculate delay. */
    public B withDelay(Variable delay) {
      return withDelay(String.valueOf(delay.getValue()));
    }
  }
}
