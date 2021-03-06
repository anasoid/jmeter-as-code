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

package org.anasoid.jmc.plugins.wrapper.java.extractor;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.plugins.component.java.extractor.JavaPostProcessor;
import org.anasoid.jmc.plugins.component.java.extractor.JavaPostProcessorExecutor;
import org.anasoid.jmc.plugins.component.java.extractor.JavaPostProcessorGui;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJavaTestElementWrapper;

/** Base class for JavaPostProcessor Wrapper. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractJavaPostProcessorWrapper
    extends AbstractJavaTestElementWrapper<JavaPostProcessor>
    implements PostProcessorWrapper<JavaPostProcessor>, JavaPostProcessorExecutor {

  @Override
  public Class<?> getTestClass() {
    return JavaPostProcessor.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return JavaPostProcessorGui.class;
  }
}
