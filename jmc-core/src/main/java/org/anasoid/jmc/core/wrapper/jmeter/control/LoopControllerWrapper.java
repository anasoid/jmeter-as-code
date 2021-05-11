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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.control;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;

/**
 * Wrapper for LoopController.
 *
 * @see LoopController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Loop Controller")
public class LoopControllerWrapper
    extends GenericControllerWrapper<LoopController, LoopControlPanel> {

  /** Number of iterations to use. */
  @JmcProperty(value = LoopController.LOOPS)
  @Getter
  private final String loops;
  /** Infinite. */
  @JmcProperty("LoopController.continue_forever")
  @Builder.Default
  @Getter
  Boolean continueForever = false;

  @Override
  public Class<?> getTestClass() {
    return LoopController.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return LoopControlPanel.class;
  }

  /** Builder. */
  public abstract static class LoopControllerWrapperBuilder<
          C extends LoopControllerWrapper, B extends LoopControllerWrapperBuilder<C, B>>
      extends GenericControllerWrapper.GenericControllerWrapperBuilder<
          LoopController, LoopControlPanel, C, B> {

    /** Number of iterations to use. */
    public B withLoops(int loops) {
      return withLoops(String.valueOf(loops));
    }

    /** Number of iterations to use. */
    public B withLoops(Variable loops) {
      return withLoops(String.valueOf(loops.getValue()));
    }

    /** Number of iterations to use. */
    public B withLoops(String loops) {
      this.loops = loops;
      return self();
    }
  }
}
