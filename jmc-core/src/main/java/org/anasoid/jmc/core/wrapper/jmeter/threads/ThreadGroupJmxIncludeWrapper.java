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
 * Date :   03-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.threads;

import java.util.Arrays;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmc.generic.AbstractParentJmxIncludeWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.ControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SamplerWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
import org.apache.jmeter.testelement.AbstractTestElement;

/** AbstractThreadGroup Include Jmx. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcChildrenTypes(type = {ControllerWrapper.class, SamplerWrapper.class})
public class ThreadGroupJmxIncludeWrapper
    extends AbstractParentJmxIncludeWrapper<AbstractTestElement>
    implements ThreadWrapper<AbstractTestElement> {

  private static final long serialVersionUID = -8017246349372467904L;

  /** Builder. */
  public abstract static class ThreadGroupJmxIncludeWrapperBuilder<
          C extends ThreadGroupJmxIncludeWrapper,
          B extends ThreadGroupJmxIncludeWrapperBuilder<C, B>>
      extends AbstractParentJmxIncludeWrapper.AbstractParentJmxIncludeWrapperBuilder<
          AbstractTestElement, C, B> {

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
