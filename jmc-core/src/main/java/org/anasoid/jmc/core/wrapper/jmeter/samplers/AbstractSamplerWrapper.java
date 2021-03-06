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

package org.anasoid.jmc.core.wrapper.jmeter.samplers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.samplers.AbstractSampler;

/**
 * Wrapper for AbstractSampler.
 *
 * @see AbstractSampler
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class AbstractSamplerWrapper<
        T extends AbstractSampler, G extends AbstractJMeterGuiComponent>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G>, SamplerWrapper<T> {

  /** Builder. */
  public abstract static class AbstractSamplerWrapperBuilder<
          T extends AbstractSampler,
          G extends AbstractJMeterGuiComponent,
          C extends AbstractSamplerWrapper<T, G>,
          B extends AbstractSamplerWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {}
}
