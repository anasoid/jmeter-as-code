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
 * Date :   24-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.modifier;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.modifier.RegExUserParameters;
import org.apache.jmeter.protocol.http.modifier.gui.RegExUserParametersGui;

/**
 * Wrapper for RegExUserParameters.
 *
 * @see RegExUserParameters
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("RegEx User Parameters")
public class RegExUserParametersWrapper
    extends AbstractBasicChildTestElementWrapper<RegExUserParameters>
    implements JMeterGUIWrapper<RegExUserParametersGui>, PreProcessorWrapper<RegExUserParameters> {

  /** Name of a reference to a regular expression. */
  @JmcProperty(RegExUserParameters.REG_EX_REF_NAME)
  @Getter
  @Setter
  @JmcMandatory
  private String regExRefName;
  /** Group number of regular expression used to extract parameter names. */
  @JmcProperty(RegExUserParameters.REG_EX_PARAM_NAMES_GR_NR)
  @Getter
  @Setter
  @JmcMandatory
  private String regExParamNamesGrNr;

  /** Group number of regular expression used to extract parameter values. */
  @JmcProperty(RegExUserParameters.REG_EX_PARAM_VALUES_GR_NR)
  @Getter
  @Setter
  @JmcMandatory
  private String regExParamValuesGrNr;

  @Override
  public Class<?> getGuiClass() {
    return RegExUserParametersGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RegExUserParameters.class;
  }
}
