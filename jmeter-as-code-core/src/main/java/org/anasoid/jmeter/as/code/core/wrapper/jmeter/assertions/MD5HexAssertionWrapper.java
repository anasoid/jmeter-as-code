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
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.MD5HexAssertion;
import org.apache.jmeter.assertions.gui.MD5HexAssertionGUI;

/**
 * Wrapper for MD5HexAssertion.
 *
 * @see MD5HexAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class MD5HexAssertionWrapper extends AbstractBasicChildTestElementWrapper<MD5HexAssertion>
    implements JMeterGUIWrapper<MD5HexAssertionGUI>, AssertionWrapper<MD5HexAssertion> {

  /** 32 hex digits representing the MD5 hash (case not significant). */
  @JmcProperty("MD5HexAssertion.size")
  @Getter
  @Setter
  @JmcMandatory
  private String md5hex;

  @Override
  public Class<MD5HexAssertionGUI> getGuiClass() {
    return MD5HexAssertionGUI.class;
  }

  @Override
  public Class<MD5HexAssertion> getTestClass() {
    return MD5HexAssertion.class;
  }
}
