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

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.config.JmcConfig;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.XMLSchemaAssertion;
import org.apache.jmeter.assertions.gui.XMLSchemaAssertionGUI;

/**
 * Wrapper for XMLSchemaAssertion.
 *
 * @see XMLSchemaAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class XMLSchemaAssertionWrapper
    extends AbstractBasicChildTestElementWrapper<XMLSchemaAssertion>
    implements JMeterGUIWrapper<XMLSchemaAssertionGUI>, AssertionWrapper<XMLSchemaAssertion> {

  /** Specify XML Schema File Name. */
  @XStreamOmitField @Getter @Setter private String filename;

  @JmcProperty("xmlschema_assertion_filename")
  @JmcMandatory
  protected String getFilePath() {
    if (filename == null) {
      return null;
    }
    return JmcConfig.getDataRootFolder() + filename;
  }

  @Override
  public Class<?> getGuiClass() {
    return XMLSchemaAssertionGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return XMLSchemaAssertion.class;
  }
}
