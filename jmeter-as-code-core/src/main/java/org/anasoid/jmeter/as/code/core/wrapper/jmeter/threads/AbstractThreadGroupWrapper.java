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

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.template.JmcTemplate;
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
@JmcChildrenTypes(type = {GenericControllerWrapper.class, AbstractSamplerWrapper.class})
public abstract class AbstractThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  /** Action to be taken after a Sampler error. */
  @JmcProperty(AbstractThreadGroup.ON_SAMPLE_ERROR)
  @Builder.Default
  private @Getter @Setter OnSampleError onSampleError = OnSampleError.ON_SAMPLE_ERROR_CONTINUE;

  /** Same user on each iteration. */
  @JmcProperty(AbstractThreadGroup.IS_SAME_USER_ON_NEXT_ITERATION)
  @Builder.Default
  private @Getter @Setter Boolean isSameUserOnNextIteration = true;

  /** Number of Threads (users). */
  @JmcProperty(value = AbstractThreadGroup.NUM_THREADS)
  @Builder.Default
  private @Getter @Setter String numThreadsAsVar = "1";

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

    /** set number of Threads. */
    public B withNumThreads(int numThreads) {
      return withNumThreadsAsVar(String.valueOf(numThreads));
    }

    private B withSamplerController(GenericControllerWrapper<?, ?> controller) { // NOSONAR
      // hide samplerController
      return self();
    }

    /** Add sampler. */
    public B addSampler(AbstractSamplerWrapper<?, ?> sampler) { // NOSONAR
      return addSamplers(Arrays.asList(sampler));
    }

    /** Add sampler. */
    public <E extends AbstractSamplerWrapper<?, ?>> B addSampler(JmcTemplate<E> template) {
      return addSampler(template.generate());
    }

    /** Add samplers as child in tree. */
    public B addSamplers(List<AbstractSamplerWrapper<?, ?>> samplers) { // NOSONAR
      for (AbstractSamplerWrapper<?, ?> sampler : samplers) {
        withChild(sampler);
      }
      return self();
    }

    /** Add Controller. */
    public B addController(GenericControllerWrapper<?, ?> controller) { // NOSONAR
      return addControllers(Arrays.asList(controller));
    }

    /** Add Controller. */
    public <E extends GenericControllerWrapper<?, ?>> B addControllers(JmcTemplate<E> template) {
      return addController(template.generate());
    }

    /** Add Controllers as child in tree. */
    public B addControllers(List<GenericControllerWrapper<?, ?>> controllers) { // NOSONAR
      for (GenericControllerWrapper<?, ?> controller : controllers) {
        withChild(controller);
      }
      return self();
    }
  }
}
