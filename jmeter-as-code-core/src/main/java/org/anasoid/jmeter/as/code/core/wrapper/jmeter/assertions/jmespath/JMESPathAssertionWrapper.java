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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions.jmespath;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions.AbstractJSONPathAssertionWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.jmespath.JMESPathAssertion;
import org.apache.jmeter.assertions.jmespath.gui.JMESPathAssertionGui;

/**
 * Wrapper for JMESPathAssertion.
 *
 * @see JMESPathAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class JMESPathAssertionWrapper
    extends AbstractJSONPathAssertionWrapper<JMESPathAssertion, JMESPathAssertionGui> {

  /** Check that JMESPath to JSON element exists. */
  @JmcProperty("JMES_PATH")
  @Getter
  @Setter
  @JmcMandatory
  private String jmesPath;

  @Override
  public Class<JMESPathAssertionGui> getGuiClass() {
    return JMESPathAssertionGui.class;
  }

  @Override
  public Class<JMESPathAssertion> getTestClass() {
    return JMESPathAssertion.class;
  }
}
