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

import java.util.Arrays;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.gui.AbstractControllerGui;

/**
 * Wrapper for AbstractTestElementWrapper.
 *
 * @see AbstractTestElementWrapper
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
@JmcChildrenTypes(type = {AbstractSamplerWrapper.class})
public abstract class GenericControllerWrapper<
        T extends GenericController, G extends AbstractControllerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G>, ControllerWrapper<T> {

  /** Builder. */
  @SuppressWarnings("PMD.UselessOverridingMethod")
  public abstract static class GenericControllerWrapperBuilder<
          T extends GenericController,
          G extends AbstractControllerGui,
          C extends GenericControllerWrapper<T, G>,
          B extends GenericControllerWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

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
    public B addController(ControllerWrapper<?> controller) { // NOSONAR
      return addControllers(Arrays.asList(controller));
    }

    /** Add Controller. */
    public <E extends ControllerWrapper<?>> B addController(JmcTemplate<E> template) { // NOSONAR
      return addController(template.generate());
    }

    /** Add Controllers as child in tree. */
    public B addControllers(List<ControllerWrapper<?>> controllers) { // NOSONAR
      for (ControllerWrapper<?> controller : controllers) {
        withChild(controller);
      }
      return self();
    }
  }
}
