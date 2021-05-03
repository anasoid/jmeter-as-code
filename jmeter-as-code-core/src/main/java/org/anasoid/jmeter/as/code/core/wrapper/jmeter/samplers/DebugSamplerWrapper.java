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
 * Date :   03-May-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.sampler.DebugSampler;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for DebugSampler.
 *
 * @see DebugSampler
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class DebugSamplerWrapper extends AbstractSamplerWrapper<DebugSampler, TestBeanGUI> {

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
    return DebugSampler.class;
  }
}
