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

package org.anasoid.jmc.plugins.component.java.processor;

import java.util.Arrays;
import java.util.Collection;
import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElementGui;
import org.anasoid.jmc.plugins.wrapper.java.processor.AbstractJavaPreProcessorWrapper;
import org.apache.jmeter.gui.util.MenuFactory;

/** JavaPreProcessor Gui. */
public class JavaPreProcessorGui extends AbstractJavaTestElementGui {

  @Override
  protected Class getTestClass() {
    return JavaPreProcessor.class;
  }

  @Override
  protected Class<AbstractJavaPreProcessorWrapper> getExecutorClass() {
    return AbstractJavaPreProcessorWrapper.class;
  }

  @Override
  public Collection<String> getMenuCategories() {
    return Arrays.asList(MenuFactory.PRE_PROCESSORS);
  }
}
