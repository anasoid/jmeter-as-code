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
 * Date :   30-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.assertions.XMLAssertion;
import org.apache.jmeter.assertions.gui.XMLAssertionGui;

/**
 * Wrapper for XMLAssertion.
 *
 * @see XMLAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("XML Assertion")
public class XMLAssertionWrapper extends AbstractBasicChildTestElementWrapper<XMLAssertion>
    implements JMeterGUIWrapper<XMLAssertionGui>, AssertionWrapper<XMLAssertion> {

  @Override
  public Class<?> getGuiClass() {
    return XMLAssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return XMLAssertion.class;
  }
}
