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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.gui.AbstractScopedJMeterGuiComponent;
import org.apache.jmeter.testelement.AbstractScopedTestElement;

/**
 * Wrapper for AbstractScopedTestElement.
 *
 * @see AbstractScopedTestElement
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractScopedTestElementWrapper<
        T extends AbstractScopedTestElement, G extends AbstractScopedJMeterGuiComponent>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  @JmcProperty("Scope.variable")
  @Getter
  private String scopeVariable;
}
