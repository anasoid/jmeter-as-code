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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
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
  @Getter
  @Setter
  @Default
  public UNIT unit = UNIT.MINUTE;

  @Override
  public void internalInit() {
    super.internalInit();

    samplerController =
        VirtualUserControllerWrapper.builder().withName(null).withEnabled(null).build();
  }

  /** enum for unit second or minute. */
  public enum UNIT {
    /** Minute. */
    MINUTE("M"),
    /** Second. */
    SECOND("S");

    private final String value;

    UNIT(String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
