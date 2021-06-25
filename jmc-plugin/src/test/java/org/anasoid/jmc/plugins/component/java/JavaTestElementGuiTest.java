package org.anasoid.jmc.plugins.component.java;

import org.anasoid.jmc.plugins.AbstractJmcPluginGuiTest;
import org.anasoid.jmc.plugins.component.java.timers.JavaTimer;
import org.anasoid.jmc.plugins.component.java.timers.JavaTimerGui;
import org.anasoid.jmc.plugins.wrapper.java.timers.executor.TestJavaTimerWrapperWithField;
import org.junit.jupiter.api.Test;
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
 * Date :   24-Jun-2021
 */

class JavaTestElementGuiTest extends AbstractJmcPluginGuiTest {

  @Test
  void testClearGui() {
    JavaTimerGui instance = new JavaTimerGui();
    instance.clearGui();
  }

  @Test
  void testCreateTestElement() {
    JavaTimerGui instance = new JavaTimerGui();
    instance.createTestElement();
  }

  @Test
  void testGetLabelResource() {
    JavaTimerGui instance = new JavaTimerGui();
    instance.getLabelResource();
  }

  @Test
  void testGetStaticLabel() {
    JavaTimerGui instance = new JavaTimerGui();
    instance.getStaticLabel();
  }

  @Test
  void testModifyTestElement() {

    JavaTimerGui instance = new JavaTimerGui();
    instance.modifyTestElement(getElement());
  }

  @Test
  void testConfigure() {
    JavaTimerGui instance = new JavaTimerGui();
    instance.configure(getElement());
  }

  private AbstractJavaTestElement getElement() {
    AbstractJavaTestElement element = new JavaTimer();
    element.setExecutorClass(TestJavaTimerWrapperWithField.class.getName());
    return element;
  }
}
