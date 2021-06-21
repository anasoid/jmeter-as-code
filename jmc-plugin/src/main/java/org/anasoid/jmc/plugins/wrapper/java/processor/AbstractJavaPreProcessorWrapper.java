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
 * Date :   15-Jun-2021
 */

package org.anasoid.jmc.plugins.wrapper.java.processor;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmc.plugins.component.java.processor.JavaPreProcessor;
import org.anasoid.jmc.plugins.component.java.processor.JavaPreProcessorExecutor;
import org.anasoid.jmc.plugins.component.java.processor.JavaPreProcessorGui;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJavaTestElementWrapper;

/** Base class for JavaPreProcessor Wrapper. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractJavaPreProcessorWrapper
    extends AbstractJavaTestElementWrapper<JavaPreProcessor>
    implements PreProcessorWrapper<JavaPreProcessor>, JavaPreProcessorExecutor {

  @Override
  public Class<?> getTestClass() {
    return JavaPreProcessor.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return JavaPreProcessorGui.class;
  }
}