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

import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmc.core.wrapper.jmeter.control.ControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
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
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G>, ThreadWrapper<T> {

  /** the sampler controller. */
  @JmcProperty(AbstractThreadGroup.MAIN_CONTROLLER)
  protected @Getter GenericControllerWrapper<?, ?> samplerController;
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
  private @Getter @Setter String numThreads = "1";

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

    private B withSamplerController(GenericControllerWrapper<?, ?> controller) { // NOSONAR
      // hide samplerController
      return self();
    }

    /** Add sampler. */
    public B addSampler(SamplerWrapper<?> sampler) { // NOSONAR
      return addSamplers(Arrays.asList(sampler));
    }

    /** Add sampler. */
    public <E extends SamplerWrapper<?>> B addSampler(JmcTemplate<E> template) {
      return addSampler(template.generate());
    }

    /** Add samplers as child in tree. */
    public B addSamplers(List<SamplerWrapper<?>> samplers) { // NOSONAR
      return withChilds(samplers);
    }

    /** Add Controller. */
    public B addController(ControllerWrapper<?> controller) { // NOSONAR
      return addControllers(Arrays.asList(controller));
    }

    /** Add Controller. */
    public <E extends ControllerWrapper<?>> B addController(JmcTemplate<E> template) { // NOSONAR
      return addController(template.generate());
    }

    /** Add Controllers as child in tree. */
    public B addControllers(List<ControllerWrapper<?>> controllers) { // NOSONAR
      return withChilds(controllers);
    }
  }
}
