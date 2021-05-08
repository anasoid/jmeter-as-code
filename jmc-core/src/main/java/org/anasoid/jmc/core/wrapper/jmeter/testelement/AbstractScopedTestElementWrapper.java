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

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
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
    extends AbstractBasicChildTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  @JmcProperty("Scope.variable")
  @Getter
  private String scopeVariable;

  @JmcProperty("Sample.scope")
  @Getter
  @Setter
  @JmcSkipDefault("parent")
  @Default
  private Scope scope = Scope.PARENT;

  /** Builder. */
  public abstract static class AbstractScopedTestElementWrapperBuilder<
          T extends AbstractScopedTestElement,
          G extends AbstractScopedJMeterGuiComponent,
          C extends AbstractScopedTestElementWrapper<T, G>,
          B extends AbstractScopedTestElementWrapperBuilder<T, G, C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          T, C, B> {

    /** Scope variable. */
    public B withScopeVariable(String scopeVariable) {
      this.scopeVariable = scopeVariable;
      return self();
    }

    /** Scope variable. */
    public B withScopeVariable(Variable scopeVariable) {
      return withScopeVariable(scopeVariable.getName());
    }
  }
}
