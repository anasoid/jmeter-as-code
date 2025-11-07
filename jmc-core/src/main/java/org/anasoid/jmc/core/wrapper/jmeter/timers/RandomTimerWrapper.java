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
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.timers.RandomTimer;
import org.apache.jmeter.timers.gui.AbstractRandomTimerGui;

/**
 * Wrapper for RandomTimer.
 *
 * @see RandomTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)

public abstract class RandomTimerWrapper<G extends RandomTimer, F extends AbstractRandomTimerGui>
    extends AbstractConstantTimerWrapper<G, F> {

  /** Deviation in milliseconds. */
  @JmcProperty("RandomTimer.range")
  @Getter
  @Setter
  @Default
  private String range = "100.0";

  /** builder. */
  public abstract static class RandomTimerWrapperBuilder<
          G extends RandomTimer,
          F extends AbstractRandomTimerGui,
          C extends RandomTimerWrapper<G, F>,
          B extends RandomTimerWrapperBuilder<G, F, C, B>>
      extends AbstractConstantTimerWrapper.AbstractConstantTimerWrapperBuilder<G, F, C, B> {

    protected B withRange(String range) {
      this.range$value = range;
      this.range$set = true;
      return self();
    }
  }
}
