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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

/**
 * Wrapper for AbstractThreadGroup.
 *
 * @see AbstractThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class AbstractThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  /** Action to be taken after a Sampler error. */
  @JmcProperty(AbstractThreadGroup.ON_SAMPLE_ERROR)
  @Builder.Default
  private @Getter OnSampleError onSampleError = OnSampleError.ON_SAMPLE_ERROR_CONTINUE;

  /** Same user on each iteration. */
  @JmcProperty(AbstractThreadGroup.IS_SAME_USER_ON_NEXT_ITERATION)
  @Builder.Default
  private @Getter Boolean isSameUserOnNextIteration = true;

  /** Number of Threads (users). */
  @JmcProperty(value = AbstractThreadGroup.NUM_THREADS, asString = true)
  @Builder.Default
  private @Getter Integer numThreads = 1;

  /** the sampler controller. */
  @JmcProperty(AbstractThreadGroup.MAIN_CONTROLLER)
  protected @Getter GenericControllerWrapper<?, ?> samplerController;

  /** Builder. */
  @SuppressWarnings("PMD")
  public abstract static class AbstractThreadGroupWrapperBuilder<
          T extends AbstractThreadGroup,
          G extends AbstractThreadGroupGui,
          C extends AbstractThreadGroupWrapper<T, G>,
          B extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    private B withSamplerController(GenericControllerWrapper<?, ?> controller) { // NOSONAR
      // hide samplerController
      return self();
    }

    /**
     * Add testElement as child in tree.
     *
     * @param child child.
     */
    public B addChild(AbstractSamplerWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }

    /**
     * Add confElement as child in tree.
     *
     * @param child child.
     */
    public B addChild(ConfigTestElementWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }

    /**
     * Add testElement as child in tree.
     *
     * @param child child.
     */
    public B addChild(GenericControllerWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }
  }
}
