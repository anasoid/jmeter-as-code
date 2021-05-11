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

package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.extractor.DebugPostProcessor;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for DebugPostProcessor.
 *
 * @see DebugPostProcessor
 */
@SuppressWarnings("PMD.RedundantFieldInitializer")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Debug PostProcessor")
public class DebugPostProcessorWrapper
    extends AbstractBasicChildTestElementWrapper<DebugPostProcessor>
    implements JMeterGUIWrapper<TestBeanGUI>, PostProcessorWrapper<DebugPostProcessor> {

  @XStreamOmitField private static final long serialVersionUID = 5579621630694220646L;

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
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return DebugPostProcessor.class;
  }
}
