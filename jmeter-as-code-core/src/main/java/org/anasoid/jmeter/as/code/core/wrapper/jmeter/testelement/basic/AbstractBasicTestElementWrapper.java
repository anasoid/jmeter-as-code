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
 * Date :   27-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcInherited;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Abstract Class for all test element. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractBasicTestElementWrapper<T extends AbstractTestElement>
    implements TestElementWrapper<T> {

  @XStreamOmitField private static final long serialVersionUID = 5001920204233593046L;
  @XStreamOmitField private boolean isInitialized;

  @Override
  public final void init() {
    if (!isInitialized) {
      internalInit();
      isInitialized = true;
    }
  }

  protected void internalInit() {}

  @JmcInherited
  @Override
  public String getTestClassAsString() {
    if (getTestClass() != null) {
      return getTestClass().getSimpleName();
    }
    return null;
  }

  @Override
  public List<TestElementWrapper<?>> getChilds() {
    return null;
  }

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  @JmcMethodAlias("guiclass")
  @JmcAsAttribute
  public String getGuiClassAsString() {
    if (this instanceof JMeterGUIWrapper) {
      JMeterGUIWrapper<?> gui = (JMeterGUIWrapper) this;
      return gui.getGuiClass().getSimpleName();
    }
    return null;
  }
}