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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.test;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;

/** Wrapper for testing. Child Element. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class SubChildTestingElementWrapperTesting
    extends ChildTestElementWrapperTesting<LoopController, LoopControlPanel> {

  @JmcProperty("child.field")
  @Builder.Default
  @Getter
  String field = "subSuper";

  @JmcProperty("subChild.bb1")
  @Builder.Default
  @Getter
  Boolean bb1 = false;

  @JmcProperty("subChild.method")
  public String getMethod() {
    return "me";
  }

  @Override
  public Class<?> getTestClass() {
    return LoopController.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return LoopControlPanel.class;
  }
}
