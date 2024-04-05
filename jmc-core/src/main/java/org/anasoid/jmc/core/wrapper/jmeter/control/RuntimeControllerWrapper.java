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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.control;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.RunTime;
import org.apache.jmeter.control.gui.RunTimeGui;

/**
 * Wrapper for Runtime.
 *
 * @see Runtime
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Runtime Controller")
public class RuntimeControllerWrapper extends GenericControllerWrapper<RunTime, RunTimeGui> {

  /** Desired runtime in seconds. 0 means no run.. */
  @JmcProperty("RunTime.seconds")
  @Getter
  @Default
  @SuppressWarnings({"PMD.ImmutableField"})
  private String seconds = "1";

  @Override
  public Class<?> getTestClass() {
    return RunTime.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return RunTimeGui.class;
  }

  /** Builder. */
  public abstract static class RuntimeControllerWrapperBuilder<
          C extends RuntimeControllerWrapper, B extends RuntimeControllerWrapperBuilder<C, B>>
      extends GenericControllerWrapperBuilder<RunTime, RunTimeGui, C, B> {

    /** Desired runtime in seconds. 0 means no run.. */
    public B withSeconds(int seconds) {
      return withSeconds(String.valueOf(seconds));
    }

    /** Desired runtime in seconds. 0 means no run.. */
    public B withSeconds(Variable seconds) {
      return withSeconds(String.valueOf(seconds.getValue()));
    }

    /** Desired runtime in seconds. 0 means no run.. */
    public B withSeconds(String seconds) {
      this.seconds$set = true;
      this.seconds$value = seconds;
      return self();
    }
  }
}
