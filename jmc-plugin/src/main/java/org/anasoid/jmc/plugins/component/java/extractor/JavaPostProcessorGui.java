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
 * Date :   18-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java.extractor;

import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElementGui;
import org.anasoid.jmc.plugins.wrapper.java.extractor.AbstractJavaPostProcessorWrapper;
import org.apache.jmeter.gui.GUIMenuSortOrder;
import org.apache.jmeter.gui.TestElementMetadata;
import org.apache.jmeter.gui.util.MenuFactory;
import org.apache.jmeter.testelement.TestElement;

/** JavaPostProcessor Gui. */
@GUIMenuSortOrder(5)
@TestElementMetadata(
    labelResource = "java_postproessor",
    actionGroups = MenuFactory.POST_PROCESSORS)
public class JavaPostProcessorGui extends AbstractJavaTestElementGui {

  /** Create a new JavaPostProcessorGui as a standalone component. */
  public JavaPostProcessorGui() {
    super();
  }

  @Override
  protected Class<AbstractJavaPostProcessorWrapper> getExecutorClass() {
    return AbstractJavaPostProcessorWrapper.class;
  }

  @Override
  public TestElement createTestElement() {
    JavaPostProcessor config = new JavaPostProcessor();
    modifyTestElement(config);
    return config;
  }
}
