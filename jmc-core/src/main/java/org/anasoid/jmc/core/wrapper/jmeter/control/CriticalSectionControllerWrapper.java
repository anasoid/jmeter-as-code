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
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.CriticalSectionController;
import org.apache.jmeter.control.gui.CriticalSectionControllerGui;

/**
 * Wrapper for CriticalSectionController.
 *
 * @see CriticalSectionController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Critical Section Controller")
public class CriticalSectionControllerWrapper
    extends GenericControllerWrapper<CriticalSectionController, CriticalSectionControllerGui> {

  /**
   * Lock that will be taken by controller, ensure you use different lock names for unrelated
   * sections.
   */
  @JmcProperty("CriticalSectionController.lockName")
  @Getter
  @Setter
  @JmcMandatory
  private String lockName;

  @Override
  public Class<?> getGuiClass() {
    return CriticalSectionControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return CriticalSectionController.class;
  }
}
