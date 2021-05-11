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
 * Date :   05-May-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.visualizers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.reporters.ResultCollectorWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.visualizers.TableVisualizer;

/**
 * Wrapper for TableVisualizer.
 *
 * @see TableVisualizer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("View Results in Table")
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.TooManyFields"})
public class TableVisualizerWrapper
    extends ResultCollectorWrapper<ResultCollector, TableVisualizer> {

  @Override
  public Class<?> getGuiClass() {
    return TableVisualizer.class;
  }
}
