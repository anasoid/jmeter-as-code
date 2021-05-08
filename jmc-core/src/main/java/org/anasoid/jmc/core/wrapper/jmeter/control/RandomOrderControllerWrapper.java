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

import lombok.experimental.SuperBuilder;
import org.apache.jmeter.control.RandomOrderController;
import org.apache.jmeter.control.gui.RandomOrderControllerGui;

/**
 * Wrapper for RandomOrderController.
 *
 * @see RandomOrderController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class RandomOrderControllerWrapper
    extends GenericControllerWrapper<RandomOrderController, RandomOrderControllerGui> {

  @Override
  public Class<?> getGuiClass() {
    return RandomOrderControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RandomOrderController.class;
  }
}
