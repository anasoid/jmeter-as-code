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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.control;

import com.blazemeter.jmeter.control.VirtualUserController;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.apache.jmeter.control.gui.LoopControlPanel;

/**
 * Wrapper for VirtualUserController. Internal use only.
 *
 * @see VirtualUserController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class VirtualUserControllerWrapper
    extends GenericControllerWrapper<VirtualUserController, LoopControlPanel> {

  /** hide testclass attribute. */
  @Override
  @SuppressWarnings("PMD.UselessOverridingMethod")
  public String getTestClassAsString() {
    return super.getTestClassAsString();
  }

  @Override
  public Class<?> getTestClass() {
    return VirtualUserController.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return null;
  }
}
