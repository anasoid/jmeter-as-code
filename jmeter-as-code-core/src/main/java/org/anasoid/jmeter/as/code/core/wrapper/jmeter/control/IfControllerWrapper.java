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
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.control.IfController;
import org.apache.jmeter.control.gui.IfControllerPanel;

/**
 * Wrapper for IfController.
 *
 * @see IfController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class IfControllerWrapper extends GenericControllerWrapper<IfController, IfControllerPanel> {

  /**
   * By default the condition is interpreted as JavaScript code that returns "true" or "false", but
   * this can be overridden (see below).
   */
  @JmcProperty("IfController.condition")
  @Getter
  @Setter
  @JmcMandatory
  private String condition;

  /**
   * Evaluate for all children : Should condition be evaluated for all children? If not checked,
   * then the condition is only evaluated on entry.
   */
  @JmcProperty("IfController.evaluateAll")
  @Getter
  @Setter
  @Default
  private boolean evaluateAll = false;

  /**
   * Interpret Condition as Variable Expression? f this is selected, then the condition must be an
   * expression that evaluates to "true" (case is ignored). For example, ${FOUND} or
   * ${__jexl3(${VAR} > 100)}. Unlike the JavaScript case, the condition is only checked to see if
   * it matches "true" (case is ignored).
   */
  @JmcProperty("IfController.useExpression")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean useExpression = true;

  @Override
  public Class<?> getGuiClass() {
    return IfControllerPanel.class;
  }

  @Override
  public Class<?> getTestClass() {
    return IfController.class;
  }
}
