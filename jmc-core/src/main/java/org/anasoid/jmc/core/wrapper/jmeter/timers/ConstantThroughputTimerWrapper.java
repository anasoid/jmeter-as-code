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

package org.anasoid.jmc.core.wrapper.jmeter.timers;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.timers.ConstantThroughputTimer;

/**
 * Wrapper for ConstantThroughputTimer.
 *
 * @see ConstantThroughputTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Constant Throughput Timer")
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public class ConstantThroughputTimerWrapper
    extends AbstractBasicChildTestElementWrapper<ConstantThroughputTimer>
    implements JMeterGUIWrapper<TestBeanGUI>, TimerWrapper<ConstantThroughputTimer> {

  @JmcProperty(value = "calcMode")
  @Getter
  @Setter
  @Default
  private CalcMode calcMode = CalcMode.ThisThreadOnly;

  /** Throughput we want the timer to try to generate. */
  @JmcProperty(value = "throughput")
  @Getter
  @Setter
  @Default
  private String throughput = "0.0";

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ConstantThroughputTimer.class;
  }

  /** Enum CalcMode. */
  public enum CalcMode {
    /**
     * this thread only - each thread will try to maintain the target throughput. The overall
     * throughput will be proportional to the number of active threads.
     */
    ThisThreadOnly(0),
    /**
     * all active threads - the target throughput is divided amongst all the active threads in all
     * Thread Groups. Each thread will throughput as needed, based on when it last ran. In this
     * case, each other Thread Group will need a Constant Throughput timer with the same settings.
     */
    AllActiveThreads(1),
    /**
     * all active threads in current thread group - the target throughput is divided amongst all the
     * active threads in the group. Each thread will throughput as needed, based on when it last
     * ran.
     */
    AllActiveThreadsInCurrentThreadGroup(2),
    /**
     * all active threads (shared) - as above; each thread is throughputed based on when any thread
     * last ran.
     */
    AllActiveThreads_Shared(3),
    /**
     * all active threads in current thread group (shared) - as above, but each thread is
     * throughputed based on when any thread in the group last ran.
     */
    AllActiveThreadsInCurrentThreadGroup_Shared(4);

    public final Integer value;

    CalcMode(Integer value) {
      this.value = value;
    }

    public Integer value() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** Builder. */
  public abstract static class ConstantThroughputTimerWrapperBuilder<
          C extends ConstantThroughputTimerWrapper,
          B extends ConstantThroughputTimerWrapperBuilder<C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          ConstantThroughputTimer, C, B> {

    /** Throughput we want the timer to try to generate. */
    public B withThroughput(String throughput) {
      this.throughput$value = throughput;
      this.throughput$set = true;
      return self();
    }

    /** Throughput we want the timer to try to generate. */
    public B withThroughput(Integer throughput) {
      return withThroughput(String.valueOf(throughput));
    }

    /** Throughput we want the timer to try to generate. */
    public B withThroughput(Variable throughput) {
      return withThroughput(String.valueOf(throughput.getValue()));
    }
  }
}
