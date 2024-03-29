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
 * Date :   06-Jul-2021
 */

package org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.vizualizers;

import kg.apc.jmeter.graphs.AbstractGraphPanelVisualizer;
import kg.apc.jmeter.vizualizers.SynthesisReportGui;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;

/**
 * Wrapper for SynthesisReportGui.
 *
 * @see SynthesisReportGui
 */
@JmcDefaultName("jp@gc - Synthesis Report (filtered)")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class SynthesisReportWrapper extends CorrectedResultCollectorWrapper<SynthesisReportGui> {

  /** Tobe generated in XML not used in this class. */
  @JmcProperty(value = AbstractGraphPanelVisualizer.INTERVAL_PROPERTY, type = Long.class)
  private static final Integer INTERVAL_PROPERTY = 500; // NOPMD

  /** Tobe generated in XML not used in this class. */
  @JmcProperty(value = AbstractGraphPanelVisualizer.GRAPH_AGGREGATED)
  private static final boolean GRAPH_AGGREGATED = false; // NOPMD

  @Override
  public Class<?> getGuiClass() {
    return SynthesisReportGui.class;
  }
}
