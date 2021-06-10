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
 * Date :   09-Jun-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads;

import kg.apc.jmeter.threads.AbstractSimpleThreadGroup;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.LoopControllerWrapper.LoopControllerWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.threads.AbstractThreadGroupWrapper;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

/**
 * Wrapper for AbstractSimpleThreadGroup.
 *
 * @see AbstractSimpleThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class AbstractSimpleThreadGroupWrapper<
        F extends AbstractSimpleThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractThreadGroupWrapper<F, G> {

  @Override
  public void internalInit() {
    super.internalInit();
    LoopControllerWrapperBuilder<?, ?> samplerControllerBuilder =
        LoopControllerWrapper.builder().withContinueForever(false).withLoops(-1);

    samplerController = samplerControllerBuilder.build();
  }
}
