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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.extractor;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.extractor.DebugPostProcessor;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for DebugPostProcessor.
 *
 * @see DebugPostProcessor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class DebugPostProcessorWrapper extends AbstractBasicTestElementWrapper<DebugPostProcessor>
    implements JMeterGUIWrapper<TestBeanGUI> {

  @JmcProperty("displaySamplerProperties")
  @Getter
  @Setter
  @Default
  private boolean displaySamplerProperties = true;

  @JmcProperty("displayJMeterVariables")
  @Getter
  @Setter
  @Default
  private boolean displayJMeterVariables = true;

  @JmcProperty("displayJMeterProperties")
  @Getter
  @Setter
  @Default
  private boolean displayJMeterProperties = false;

  @JmcProperty("displaySystemProperties")
  @Getter
  @Setter
  @Default
  private boolean displaySystemProperties = false;

  @Override
  public Class<TestBeanGUI> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<DebugPostProcessor> getTestClass() {
    return DebugPostProcessor.class;
  }
}
