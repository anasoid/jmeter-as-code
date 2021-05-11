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
 * Date :   25-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.reporters;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.OnErrorTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.reporters.ResultAction;
import org.apache.jmeter.reporters.gui.ResultActionGui;

/**
 * Wrapper for ResultAction.
 *
 * @see ResultAction
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Result Status Action Handler")
public class ResultActionWrapper extends OnErrorTestElementWrapper<ResultAction, ResultActionGui>
    implements PostProcessorWrapper<ResultAction> {

  @Override
  public Class<?> getGuiClass() {
    return ResultActionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ResultAction.class;
  }
}
