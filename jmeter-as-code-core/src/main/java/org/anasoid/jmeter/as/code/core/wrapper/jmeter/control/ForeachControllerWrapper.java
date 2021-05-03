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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.ForeachController;
import org.apache.jmeter.control.gui.ForeachControlPanel;

/**
 * Wrapper for ForeachController.
 *
 * @see ForeachController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class ForeachControllerWrapper
    extends GenericControllerWrapper<ForeachController, ForeachControlPanel> {

  /**
   * Input variable prefix : Prefix for the variable names to be used as input. Defaults to an empty
   * string as prefix.
   */
  @JmcProperty("ForeachController.inputVal")
  @Getter
  @Setter
  @JmcNullAllowed
  private String inputVal;

  /**
   * Output variable : The name of the variable which can be used in the loop for replacement in the
   * samplers. Defaults to an empty variable name, which is most probably not wanted.
   */
  @JmcProperty("ForeachController.returnVal")
  @Getter
  @Setter
  @JmcNullAllowed
  private String returnVariable;

  /**
   * Start index for loop : Start index (exclusive) for loop over variables (first element is at
   * start index + 1).
   */
  @JmcProperty("ForeachController.startIndex")
  @Getter
  @Setter
  private String startIndex;

  /** End index for loop : End index (inclusive) for loop over variables. */
  @JmcProperty("ForeachController.endIndex")
  @Getter
  @Setter
  private String endIndex;

  /** If not checked, the "_" separator is omitted. */
  @JmcProperty("ForeachController.useSeparator")
  @Getter
  @Setter
  @Default
  private boolean useSeparator = true;

  @Override
  public Class<?> getGuiClass() {
    return ForeachControlPanel.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ForeachController.class;
  }
}
