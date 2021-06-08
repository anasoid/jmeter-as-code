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

package org.anasoid.jmc.core.wrapper.jmeter.threads;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SamplerWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

/**
 * Wrapper for AbstractThreadGroup.
 *
 * @see AbstractThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
@JmcChildrenTypes(type = {GenericControllerWrapper.class, SamplerWrapper.class})
public abstract class AbstractThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractParentThreadGroupWrapper<T, G> {

  /** Number of Threads (users). */
  @JmcProperty(value = AbstractThreadGroup.NUM_THREADS)
  @Builder.Default
  private @Getter @Setter String numThreads = "1";

  /** Same user on each iteration. */
  @JmcProperty(AbstractThreadGroup.IS_SAME_USER_ON_NEXT_ITERATION)
  @Builder.Default
  private @Getter @Setter Boolean isSameUserOnNextIteration = true;

  /** Builder. */
  @SuppressWarnings("PMD")
  public abstract static class AbstractThreadGroupWrapperBuilder<
          T extends AbstractThreadGroup,
          G extends AbstractThreadGroupGui,
          C extends AbstractThreadGroupWrapper<T, G>,
          B extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<T, G, C, B>>
      extends AbstractParentThreadGroupWrapper.AbstractParentThreadGroupWrapperBuilder<T, G, C, B> {

    /** set number of Threads. */
    public B withNumThreads(int numThreads) {
      return withNumThreads(String.valueOf(numThreads));
    }

    /** set number of Threads. */
    public B withNumThreads(Variable numThreads) {
      return withNumThreads(String.valueOf(numThreads.getValue()));
    }

    /** set number of Threads. */
    public B withNumThreads(String numThreads) {
      this.numThreads$value = numThreads;
      this.numThreads$set = true;
      return self();
    }
  }
}
