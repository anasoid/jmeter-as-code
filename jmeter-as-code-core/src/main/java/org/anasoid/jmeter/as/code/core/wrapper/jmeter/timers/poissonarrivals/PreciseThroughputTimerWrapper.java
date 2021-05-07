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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.timers.poissonarrivals;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.timers.TimerWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.types.IntegerManager;
import org.anasoid.jmeter.as.code.core.xstream.types.LongManager;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.timers.poissonarrivals.PreciseThroughputTimer;

/**
 * Wrapper for PreciseThroughputTimer.
 *
 * @see org.apache.jmeter.timers.poissonarrivals.PreciseThroughputTimer
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class PreciseThroughputTimerWrapper
    extends AbstractBasicChildTestElementWrapper<PreciseThroughputTimer>
    implements JMeterGUIWrapper<TestBeanGUI>, TimerWrapper<PreciseThroughputTimer> {

  /** Batched departures. Number of threads in the batch (threads). */
  @JmcProperty(value = "batchSize", type = IntegerManager.class)
  @Getter
  @Setter
  @Default
  private String batchSize = "1";

  /** Batched departures. Delay between threads in the batch (ms). */
  @JmcProperty(value = "batchThreadDelay", type = IntegerManager.class)
  @Getter
  @Setter
  @Default
  private String batchThreadDelay = "0";

  /** Setting to ensure repeatable sequence : Random seed (change from 0 to random). */
  @JmcProperty(value = "randomSeed", type = LongManager.class)
  @Getter
  @Setter
  @Default
  private String randomSeed = "0";

  /**
   * Delay threads to ensure target throughput : Target throughput (in samples per 'throughput
   * period').
   */
  @JmcProperty(value = "throughput")
  @Getter
  @Setter
  @Default
  private String throughput = "100";

  /** Delay threads to ensure target throughput : Throughput period (seconds). */
  @JmcProperty(value = "throughputPeriod", type = IntegerManager.class)
  @Getter
  @Setter
  @Default
  private String throughputPeriod = "3600";

  /** Delay threads to ensure target throughput : Test duration (seconds). */
  @JmcProperty(value = "duration", type = LongManager.class)
  @Getter
  @Setter
  @Default
  private String duration = "3600";

  @Override
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return PreciseThroughputTimer.class;
  }

  /** Builder. */
  @SuppressWarnings({"PMD.TooManyMethods"})
  public abstract static class PreciseThroughputTimerWrapperBuilder<
          C extends PreciseThroughputTimerWrapper,
          B extends PreciseThroughputTimerWrapperBuilder<C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          PreciseThroughputTimer, C, B> {

    /** Batched departures. Number of threads in the batch (threads). */
    public B withBatchSize(String batchSize) {
      this.batchSize$value = batchSize;
      this.batchSize$set = true;
      return self();
    }

    /** Batched departures. Number of threads in the batch (threads). */
    public B withBatchSize(Integer batchSize) {
      return withBatchSize(String.valueOf(batchSize));
    }

    /** Batched departures. Number of threads in the batch (threads). */
    public B withBatchSize(Variable batchSize) {
      return withBatchSize(String.valueOf(batchSize.getValue()));
    }

    /** Batched departures. Delay between threads in the batch (ms). */
    public B withBatchThreadDelay(String batchThreadDelay) {
      this.batchThreadDelay$value = batchThreadDelay;
      this.batchThreadDelay$set = true;
      return self();
    }

    /** Batched departures. Delay between threads in the batch (ms). */
    public B withBatchThreadDelay(Integer batchThreadDelay) {
      return withBatchThreadDelay(String.valueOf(batchThreadDelay));
    }

    /** Batched departures. Delay between threads in the batch (ms). */
    public B withBatchThreadDelay(Variable batchThreadDelay) {
      return withBatchThreadDelay(String.valueOf(batchThreadDelay.getValue()));
    }

    /** Setting to ensure repeatable sequence : Random seed (change from 0 to random). */
    public B withRandomSeed(String randomSeed) {
      this.randomSeed$value = randomSeed;
      this.randomSeed$set = true;
      return self();
    }

    /** Setting to ensure repeatable sequence : Random seed (change from 0 to random). */
    public B withRandomSeed(Integer randomSeed) {
      return withRandomSeed(String.valueOf(randomSeed));
    }

    /** Setting to ensure repeatable sequence : Random seed (change from 0 to random). */
    public B withRandomSeed(Variable randomSeed) {
      return withRandomSeed(String.valueOf(randomSeed.getValue()));
    }

    /**
     * Delay threads to ensure target throughput : Target throughput (in samples per 'throughput
     * period').
     */
    public B withThroughput(String throughput) {
      this.throughput$value = throughput;
      this.throughput$set = true;
      return self();
    }

    /**
     * Delay threads to ensure target throughput : Target throughput (in samples per 'throughput
     * period').
     */
    public B withThroughput(Integer throughput) {
      return withThroughput(String.valueOf(throughput));
    }

    /**
     * Delay threads to ensure target throughput : Target throughput (in samples per 'throughput
     * period').
     */
    public B withThroughput(Variable throughput) {
      return withThroughput(String.valueOf(throughput.getValue()));
    }

    /** Delay threads to ensure target throughput : Throughput period (seconds). */
    public B withThroughputPeriod(String throughputPeriod) {
      this.throughputPeriod$value = throughputPeriod;
      this.throughputPeriod$set = true;
      return self();
    }

    /** Delay threads to ensure target throughput : Throughput period (seconds). */
    public B withThroughputPeriod(Integer throughputPeriod) {
      return withThroughputPeriod(String.valueOf(throughputPeriod));
    }

    /** Delay threads to ensure target throughput : Throughput period (seconds). */
    public B withThroughputPeriod(Variable throughputPeriod) {
      return withThroughputPeriod(String.valueOf(throughputPeriod.getValue()));
    }

    /** Delay threads to ensure target throughput : Test duration (seconds). */
    public B withDuration(String duration) {
      this.duration$value = duration;
      this.duration$set = true;
      return self();
    }

    /** Delay threads to ensure target throughput : Test duration (seconds). */
    public B withDuration(Integer duration) {
      return withDuration(String.valueOf(duration));
    }

    /** Delay threads to ensure target throughput : Test duration (seconds). */
    public B withDuration(Variable duration) {
      return withDuration(String.valueOf(duration.getValue()));
    }
  }
}
