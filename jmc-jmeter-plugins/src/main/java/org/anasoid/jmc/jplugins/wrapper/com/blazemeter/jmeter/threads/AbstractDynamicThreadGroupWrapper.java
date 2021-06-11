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
 * Date :   08-Jun-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads;

import com.blazemeter.jmeter.threads.AbstractDynamicThreadGroup;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.control.VirtualUserControllerWrapper;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

/**
 * Wrapper for AbstractDynamicThreadGroup.
 *
 * @see AbstractDynamicThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractDynamicThreadGroupWrapper<
        F extends AbstractDynamicThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractDynamicThreadGroupModelWrapper<F, G> {

  @JmcProperty(value = AbstractDynamicThreadGroup.UNIT)
  public static final String UNIT = "S";

  @Override
  public void internalInit() {
    super.internalInit();

    samplerController =
        VirtualUserControllerWrapper.builder().withName(null).withEnabled(null).build();
  }
}
