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
 * Date :   02-May-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.timers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.util.JSR223TestElementWrapper;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.timers.JSR223Timer;

/**
 * Wrapper for JSR223Timer.
 *
 * @see JSR223Timer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class JSR223TimerWrapper extends JSR223TestElementWrapper<JSR223Timer, TestBeanGUI>
    implements TimerWrapper<JSR223Timer> {

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return JSR223Timer.class;
  }
}
