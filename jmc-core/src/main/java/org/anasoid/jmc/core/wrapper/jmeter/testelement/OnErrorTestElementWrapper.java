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
 * Date :   25-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.threads.OnError;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.testelement.OnErrorTestElement;

/**
 * Wrapper for OnErrorTestElement.
 *
 * @see OnErrorTestElement
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class OnErrorTestElementWrapper<
        T extends OnErrorTestElement, G extends AbstractJMeterGuiComponent>
    extends AbstractBasicChildTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  /** Action to be taken after a Sampler error. */
  @JmcProperty(value = OnErrorTestElement.ON_ERROR_ACTION, type = Integer.class)
  @Builder.Default
  @Getter
  private final OnError onError = OnError.ON_ERROR_CONTINUE;
}
