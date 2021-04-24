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
 * Date :   24-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.modifiers;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.modifiers.SampleTimeout;
import org.apache.jmeter.modifiers.gui.SampleTimeoutGui;

/**
 * Wrapper for SampleTimeout.
 *
 * @see SampleTimeout
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class SampleTimeoutWrapper extends AbstractBasicChildTestElementWrapper<SampleTimeout>
    implements JMeterGUIWrapper<SampleTimeoutGui>, PreProcessorWrapper<SampleTimeout> {

  /** If the sample takes longer to complete, it will be interrupted (milliseconds). */
  @JmcProperty("InterruptTimer.timeout")
  @Getter
  @Setter
  @Default
  private String timeout = "10000";

  @Override
  public Class<SampleTimeoutGui> getGuiClass() {
    return SampleTimeoutGui.class;
  }

  @Override
  public Class<SampleTimeout> getTestClass() {
    return SampleTimeout.class;
  }

  /** Builder. */
  public abstract static class SampleTimeoutWrapperBuilder<
          C extends SampleTimeoutWrapper, B extends SampleTimeoutWrapperBuilder<C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          SampleTimeout, C, B> {

    /** If the sample takes longer to complete, it will be interrupted (milliseconds). */
    public B withTimeout(String timeout) {
      this.timeout$value = timeout;
      this.timeout$set = true;
      return self();
    }

    /** If the sample takes longer to complete, it will be interrupted (milliseconds). */
    public B withTimeout(Variable timeout) {
      return withTimeout(timeout.getValue());
    }

    /** If the sample takes longer to complete, it will be interrupted (milliseconds). */
    public B withTimeout(int timeout) {
      return withTimeout(String.valueOf(timeout));
    }
  }
}
