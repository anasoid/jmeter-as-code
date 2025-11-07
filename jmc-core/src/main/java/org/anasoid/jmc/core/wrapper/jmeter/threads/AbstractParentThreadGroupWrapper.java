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
 * Wrapper for AbstractThreadGroup, without numThread.
 *
 * @see AbstractThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcChildrenTypes(type = {GenericControllerWrapper.class, SamplerWrapper.class})
public abstract class AbstractParentThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G>, ThreadWrapper<T> {

  /** the sampler controller. */
  @JmcProperty("ThreadGroup.main_controller")
  protected @Getter GenericControllerWrapper<?, ?> samplerController;

  /** Action to be taken after a Sampler error. */
  @JmcProperty(AbstractThreadGroup.ON_SAMPLE_ERROR)
  @Builder.Default
  private @Getter @Setter OnSampleError onSampleError = OnSampleError.ON_SAMPLE_ERROR_CONTINUE;

  /** Builder. */
  @SuppressWarnings("PMD")
  public abstract static class AbstractParentThreadGroupWrapperBuilder<
          T extends AbstractThreadGroup,
          G extends AbstractThreadGroupGui,
          C extends AbstractParentThreadGroupWrapper<T, G>,
          B extends
              AbstractParentThreadGroupWrapper.AbstractParentThreadGroupWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapperBuilder<T, C, B> {

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
      return withChildren(samplers);
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
      return withChildren(controllers);
    }
  }
}
