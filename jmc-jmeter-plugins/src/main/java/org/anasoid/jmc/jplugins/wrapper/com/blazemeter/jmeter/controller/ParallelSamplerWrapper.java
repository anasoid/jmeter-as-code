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
 * Date :   07-Jul-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.controller;

import com.blazemeter.jmeter.controller.ParallelControllerGui;
import com.blazemeter.jmeter.controller.ParallelSampler;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.config.CSVDataSet;

/**
 * Wrapper for CSVDataSet.
 *
 * @see CSVDataSet
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("bzm - Parallel Controller")
public class ParallelSamplerWrapper
    extends GenericControllerWrapper<ParallelSampler, ParallelControllerGui> {

  @XStreamOmitField private static final long serialVersionUID = 5901529684597605018L;
  /**
   * Use option Generate parent sample to generate one parent sample that unite all child elements.
   */
  @JmcProperty(value = "PARENT_SAMPLE")
  @Getter
  @Setter
  @Default
  private Boolean generateParent = false;

  /** Use option Limit max thread number to limit the concurrent thread simultaneosly active. */
  @JmcProperty(value = "LIMIT_MAX_THREAD_NUMBER")
  @Getter
  @Setter
  @Default
  private Boolean limitMaxThreadNumber = false;

  /** If Limit max thread number is select it's possible to specify thread number (by default 6). */
  @JmcProperty(value = "MAX_THREAD_NUMBER")
  @Getter
  @Setter
  @Default
  private Integer maxThreadNumber = 6;

  @Override
  public Class<?> getGuiClass() {
    return ParallelControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ParallelSampler.class;
  }
}
