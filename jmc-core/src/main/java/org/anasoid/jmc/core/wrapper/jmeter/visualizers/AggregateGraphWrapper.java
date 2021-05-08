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
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.visualizers.StatGraphVisualizer;

/**
 * Wrapper for StatGraphVisualizer.
 *
 * @see StatGraphVisualizer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.TooManyFields"})
public class AggregateGraphWrapper
    extends ResultCollectorWrapper<ResultCollector, StatGraphVisualizer> {

  @Override
  public Class<?> getGuiClass() {
    return StatGraphVisualizer.class;
  }
}
