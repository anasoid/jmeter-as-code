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

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.types.IntegerManager;
import org.anasoid.jmeter.as.code.core.xstream.types.LongManager;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.timers.SyncTimer;

/**
 * Wrapper for SyncTimer.
 *
 * @see SyncTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public class SyncTimerWrapper extends AbstractBasicChildTestElementWrapper<SyncTimer>
    implements JMeterGUIWrapper<TestBeanGUI>, TimerWrapper<SyncTimer> {

  /**
   * Number of threads to release at once. Setting it to 0 is equivalent to setting it to Number of
   * threads in Thread Group.
   */
  @JmcProperty(value = "groupSize", type = IntegerManager.class)
  @Getter
  @Setter
  @Default
  private String groupSize = "0";

  /**
   * Timeout in milliseconds. If set to 0, Timer will wait for the number of threads to reach the
   * value in "Number of Simultaneous Users to Group". If superior to 0, then timer will wait at max
   * "Timeout in milliseconds" for the number of Threads. If after the timeout interval the number
   * of users waiting is not reached, timer will stop waiting. Defaults to 0.
   */
  @JmcProperty(value = "timeoutInMs", type = LongManager.class)
  @Getter
  @Setter
  @Default
  private String timeout = "0";

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return SyncTimer.class;
  }

  /** Builder. */
  public abstract static class SyncTimerWrapperBuilder<
          C extends SyncTimerWrapper, B extends SyncTimerWrapperBuilder<C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          SyncTimer, C, B> {

    /**
     * Number of threads to release at once. Setting it to 0 is equivalent to setting it to Number
     * of threads in Thread Group.
     */
    public B withGroupSize(String groupSize) {
      this.groupSize$value = groupSize;
      this.groupSize$set = true;
      return self();
    }

    /**
     * Number of threads to release at once. Setting it to 0 is equivalent to setting it to Number
     * of threads in Thread Group.
     */
    public B withGroupSize(Integer groupSize) {
      return withGroupSize(String.valueOf(groupSize));
    }

    /**
     * Number of threads to release at once. Setting it to 0 is equivalent to setting it to Number
     * of threads in Thread Group.
     */
    public B withGroupSize(Variable groupSize) {
      return withGroupSize(String.valueOf(groupSize.getValue()));
    }

    /**
     * Timeout in milliseconds. If set to 0, Timer will wait for the number of threads to reach the
     * value in "Number of Simultaneous Users to Group". If superior to 0, then timer will wait at
     * max "Timeout in milliseconds" for the number of Threads. If after the timeout interval the
     * number of users waiting is not reached, timer will stop waiting. Defaults to 0.
     */
    public B withTimeout(String timeout) {
      this.timeout$value = timeout;
      this.timeout$set = true;
      return self();
    }

    /**
     * Timeout in milliseconds. If set to 0, Timer will wait for the number of threads to reach the
     * value in "Number of Simultaneous Users to Group". If superior to 0, then timer will wait at
     * max "Timeout in milliseconds" for the number of Threads. If after the timeout interval the
     * number of users waiting is not reached, timer will stop waiting. Defaults to 0.
     */
    public B withTimeout(Integer timeout) {
      return withTimeout(String.valueOf(timeout));
    }

    /**
     * Timeout in milliseconds. If set to 0, Timer will wait for the number of threads to reach the
     * value in "Number of Simultaneous Users to Group". If superior to 0, then timer will wait at
     * max "Timeout in milliseconds" for the number of Threads. If after the timeout interval the
     * number of users waiting is not reached, timer will stop waiting. Defaults to 0.
     */
    public B withTimeout(Variable timeout) {
      return withTimeout(String.valueOf(timeout.getValue()));
    }
  }
}
