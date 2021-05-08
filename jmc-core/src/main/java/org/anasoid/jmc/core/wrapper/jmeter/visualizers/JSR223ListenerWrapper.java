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
 * Date :   21-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.visualizers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.util.JSR223TestElementWrapper;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.visualizers.JSR223Listener;

/**
 * Wrapper for JSR223Listener.
 *
 * @see JSR223Listener
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class JSR223ListenerWrapper extends JSR223TestElementWrapper<JSR223Listener, TestBeanGUI>
    implements AssertionWrapper<JSR223Listener> {

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return JSR223Listener.class;
  }
}
