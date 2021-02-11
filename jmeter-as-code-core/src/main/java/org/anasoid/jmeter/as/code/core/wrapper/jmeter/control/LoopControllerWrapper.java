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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;

@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class LoopControllerWrapper
    extends GenericControllerWrapper<LoopController, LoopControlPanel> {

  /** Infinite. */
  @JmcProperty("LoopController.continue_forever")
  @Builder.Default
  @Getter
  Boolean continueForever = false;
  /** Loop Count. */
  @JmcProperty(LoopController.LOOPS)
  @Getter
  private Integer loops;

  @Override
  public Class<LoopController> getTestClass() {
    return LoopController.class;
  }

  @Override
  public Class<LoopControlPanel> getGuiClass() {
    return LoopControlPanel.class;
  }
}
