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

package org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.graphs;

import kg.apc.jmeter.graphs.AbstractGraphPanelVisualizer;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.vizualizers.CorrectedResultCollectorWrapper;

/**
 * Wrapper for AbstractGraphPanelVisualizer.
 *
 * @see AbstractGraphPanelVisualizer
 */
@SuppressWarnings("PMD.RedundantFieldInitializer")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractGraphPanelVisualizerWrapper<G extends AbstractGraphPanelVisualizer>
    extends CorrectedResultCollectorWrapper<G> {

  /** Group timeline interval. */
  @JmcProperty(value = AbstractGraphPanelVisualizer.INTERVAL_PROPERTY, type = Long.class)
  @Getter
  @Setter
  private Integer interval;

  @Override
  protected void internalInit() {
    super.internalInit();
    if (interval == null) {
      interval = intervalDefaultValue();
    }
  }

  /** Interval default value. */
  protected int intervalDefaultValue() {
    return 500;
  }

  /**
   * True : Aggregate Display all samplers combined. False : Detailed display, one row per sampler.
   */
  @JmcProperty(value = AbstractGraphPanelVisualizer.GRAPH_AGGREGATED)
  @Getter
  @Setter
  @Default
  private boolean graphAggregate = false;
}
