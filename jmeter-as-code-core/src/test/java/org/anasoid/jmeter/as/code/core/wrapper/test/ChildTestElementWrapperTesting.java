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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.test;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.gui.AbstractControllerGui;

@SuperBuilder(setterPrefix = "with")
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class ChildTestElementWrapperTesting<
        T extends GenericController, G extends AbstractControllerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  /** Infinite. */
  @JmcProperty("child.field")
  @Builder.Default
  @Getter
  String field = "super";

  @SuppressWarnings("PMD.UselessOverridingMethod")
  public abstract static class GenericControllerWrapperBuilder<
          T extends GenericController,
          G extends AbstractControllerGui,
          C extends GenericControllerWrapper<T, G>,
          B extends GenericControllerWrapper.GenericControllerWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    public B addChild(AbstractSamplerWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }
  }
}
