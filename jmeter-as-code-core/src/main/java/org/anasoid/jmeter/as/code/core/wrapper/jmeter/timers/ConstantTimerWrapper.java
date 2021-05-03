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
 * Date :   02-May-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.timers;

import lombok.experimental.SuperBuilder;
import org.apache.jmeter.timers.ConstantTimer;
import org.apache.jmeter.timers.gui.ConstantTimerGui;

/**
 * Wrapper for ConstantTimer.
 *
 * @see ConstantTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public class ConstantTimerWrapper
    extends AbstractConstantTimerWrapper<ConstantTimer, ConstantTimerGui> {

  @Override
  public Class<?> getGuiClass() {
    return ConstantTimerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ConstantTimer.class;
  }
}
