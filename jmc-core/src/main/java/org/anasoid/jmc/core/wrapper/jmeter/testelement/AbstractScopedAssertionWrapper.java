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
import org.anasoid.jmc.core.wrapper.jmc.scope.Scope;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.assertions.gui.AbstractAssertionGui;
import org.apache.jmeter.testelement.AbstractScopedAssertion;

/**
 * Wrapper for AbstractScopedAssertion.
 *
 * @see AbstractScopedAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractScopedAssertionWrapper<
        T extends AbstractScopedAssertion, G extends AbstractAssertionGui>
    extends AbstractScopedTestElementWrapper<T, G> implements AssertionWrapper<T> {

  @JmcProperty("Assertion.scope")
  @Getter
  @Setter
  @JmcSkipDefault("children")
  @Default
  private Scope scope = Scope.CHILDREN;
}
