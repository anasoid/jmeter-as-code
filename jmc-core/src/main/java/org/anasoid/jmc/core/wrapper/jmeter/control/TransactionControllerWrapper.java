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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.control;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.control.TransactionController;
import org.apache.jmeter.control.gui.TransactionControllerGui;

/**
 * Wrapper for TransactionController.
 *
 * @see TransactionController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Transaction Controller")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class TransactionControllerWrapper
    extends GenericControllerWrapper<TransactionController, TransactionControllerGui> {

  /**
   * Generate Parent Sample : If checked, then the sample is generated as a parent of the other
   * samples, otherwise the sample is generated as an independent sample.
   */
  @JmcProperty("TransactionController.parent")
  @Getter
  @Setter
  @Default
  private boolean parent = false;

  /**
   * Include duration of timer and pre-post processors in generated sample : Whether to include
   * timer, pre- and post-processing delays in the generated sample. Default is false.
   */
  @JmcProperty("TransactionController.includeTimers")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.TRUE)
  private boolean includeTimers = false;

  @Override
  public Class<?> getGuiClass() {
    return TransactionControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return TransactionController.class;
  }
}
