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

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.reporters.ResultCollectorWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.visualizers.StatVisualizer;

/**
 * Wrapper for StatVisualizer.
 *
 * @see StatVisualizer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Aggregate Report")
@SuppressWarnings({"PMD.RedundantFieldInitializer"})
public class AggregateReportWrapper
    extends ResultCollectorWrapper<ResultCollector, StatVisualizer> {

  @JmcProperty("useGroupName")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  boolean useGroupName = false;

  @JmcProperty("saveHeaders")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.TRUE)
  boolean saveHeaders = true;

  @Override
  public Class<?> getGuiClass() {
    return StatVisualizer.class;
  }
}
