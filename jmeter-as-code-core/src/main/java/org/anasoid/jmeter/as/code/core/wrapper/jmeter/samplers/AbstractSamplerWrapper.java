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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

/**
 * Wrapper for AbstractSampler.
 *
 * @see AbstractSampler
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class AbstractSamplerWrapper<
        T extends AbstractSampler, G extends AbstractSamplerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  /** Builder. */
  public abstract static class AbstractSamplerWrapperBuilder<
          T extends AbstractSampler,
          G extends AbstractSamplerGui,
          C extends AbstractSamplerWrapper<T, G>,
          B extends AbstractSamplerWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    /** Add Assertion. */
    public B addAssertion(AssertionWrapper child) { // NOSONAR
      return super.addChild(child);
    }

    /** Add configElement as child in tree. */
    public B addConfig(ConfigTestElementWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }
  }
}
