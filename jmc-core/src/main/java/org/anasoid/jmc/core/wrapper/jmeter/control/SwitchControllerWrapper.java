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

package org.anasoid.jmc.core.wrapper.jmeter.control;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.SwitchController;
import org.apache.jmeter.control.gui.SwitchControllerGui;

/**
 * Wrapper for SwitchController.
 *
 * @see SwitchController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Switch Controller")
public class SwitchControllerWrapper
    extends GenericControllerWrapper<SwitchController, SwitchControllerGui> {

  /**
   * The number (or name) of the subordinate element to be invoked. Elements are numbered from 0.
   * Defaults to 0.
   */
  @JmcProperty("SwitchController.value")
  @Getter
  @Setter
  @JmcMandatory
  @JmcNullAllowed
  private String switchValue;

  @Override
  public Class<?> getGuiClass() {
    return SwitchControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return SwitchController.class;
  }
}
