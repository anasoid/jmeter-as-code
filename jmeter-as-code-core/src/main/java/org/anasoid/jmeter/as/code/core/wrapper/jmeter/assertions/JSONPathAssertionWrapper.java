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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.JSONPathAssertion;
import org.apache.jmeter.assertions.gui.JSONPathAssertionGui;

/**
 * Wrapper for JSONPathAssertion.
 *
 * @see JSONPathAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class JSONPathAssertionWrapper
    extends AbstractJSONPathAssertionWrapper<JSONPathAssertion, JSONPathAssertionGui> {

  /** Check that JMESPath to JSON element exists. */
  @JmcProperty("JSON_PATH")
  @Getter
  @Setter
  @JmcMandatory
  private String jsonPath;

  @Override
  public Class<JSONPathAssertionGui> getGuiClass() {
    return JSONPathAssertionGui.class;
  }

  @Override
  public Class<JSONPathAssertion> getTestClass() {
    return JSONPathAssertion.class;
  }
}