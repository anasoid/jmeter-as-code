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

package org.anasoid.jmc.core.wrapper.jmeter.threads;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.LoopControllerWrapper.LoopControllerWrapperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

/**
 * Wrapper for ThreadGroup.
 *
 * @see ThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamAlias("ThreadGroup")
@JmcDefaultName("Thread Group")
public class ThreadGroupWrapper extends AbstractThreadGroupWrapper<ThreadGroup, ThreadGroupGui> {

  @XStreamOmitField private static final long serialVersionUID = -5025050304929170782L;

  /** Specify Thread lifetime. */
  @JmcProperty(ThreadGroup.SCHEDULER)
  @Default
  @Setter
  @Getter
  private Boolean scheduler = false;

  /** Specify Thread lifetime. */
  @JmcProperty(ThreadGroup.DELAYED_START)
  @Default
  @Getter
  @Setter
  @JmcSkipDefault("false")
  private Boolean delayedStartup = false;

  /** Duration (seconds). */
  @JmcProperty(value = ThreadGroup.DURATION)
  @Getter
  @Setter
  @JmcNullAllowed
  private String duration;

  /** Startup delay (seconds). */
  @JmcProperty(value = ThreadGroup.DELAY)
  @Getter
  @JmcNullAllowed
  private final String delay;

  /** Ramp-up period (seconds). */
  @JmcProperty(value = ThreadGroup.RAMP_TIME)
  @Getter
  @Builder.Default
  private final String rampUp = "1";

  /** Number of iterations to use. */
  @XStreamOmitField private @Builder.Default @Getter final String loops = "1";
  /**
   * In spite of the name, this is actually used to determine if the loop controller is repeatable.
   * The value is only used in nextIsNull() when the loop end condition has been detected: If
   * forever==true, then it calls resetLoopCount(), otherwise it calls setDone(true). Loop
   * Controllers always set forever=true, so that they will be executed next time the parent invokes
   * them. Thread Group sets the value false, so nextIsNull() sets done, and the Thread Group will
   * not be repeated. However, it's not clear that a Thread Group could ever be repeated.
   */
  @XStreamOmitField private @Builder.Default @Getter final Boolean continueForever = false;

  @Override
  public void internalInit() {
    super.internalInit();
    LoopControllerWrapperBuilder<?, ?> samplerControllerBuilder =
        LoopControllerWrapper.builder().withContinueForever(false);
    if (Boolean.TRUE.equals(continueForever)) {
      samplerControllerBuilder.withLoops(-1);
    } else {
      samplerControllerBuilder.withLoops(loops);
    }

    samplerController = samplerControllerBuilder.build();
  }

  @Override
  public Class<?> getTestClass() {
    return ThreadGroup.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return ThreadGroupGui.class;
  }

  /** builder. */
  @SuppressWarnings({"PMD.TooManyMethods"})
  public abstract static class ThreadGroupWrapperBuilder<
          C extends ThreadGroupWrapper, B extends ThreadGroupWrapperBuilder<C, B>>
      extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<
          ThreadGroup, ThreadGroupGui, C, B> {

    /** Ramp-up period (seconds). */
    public B withRampUp(int rampUp) {
      return withRampUp(String.valueOf(rampUp));
    }

    /** Ramp-up period (seconds). */
    public B withRampUp(Variable rampUp) {
      return withRampUp(String.valueOf(rampUp.getValue()));
    }

    /** Ramp-up period (seconds). */
    public B withRampUp(String rampUp) {
      this.rampUp$value = rampUp;
      this.rampUp$set = true;
      return self();
    }

    /** Duration (seconds). */
    public B withDuration(int duration) {
      return withDuration(String.valueOf(duration));
    }

    /** Duration (seconds). */
    public B withDuration(Variable duration) {
      return withDuration(String.valueOf(duration.getValue()));
    }

    /** Duration (seconds). */
    public B withDuration(String duration) {
      this.duration = duration;
      return self();
    }

    /** Startup delay (seconds). */
    public B withDelay(int delay) {
      return withDelay(String.valueOf(delay));
    }

    /** Startup delay (seconds). */
    public B withDelay(Variable delay) {
      return withDelay(String.valueOf(delay.getValue()));
    }

    /** Startup delay (seconds). */
    public B withDelay(String delay) {
      this.delay = delay;
      return self();
    }

    /** Number of iterations to use. */
    public B withLoops(int loops) {
      return withLoops(String.valueOf(loops));
    }

    /** Number of iterations to use. */
    public B withLoops(Variable loops) {
      return withLoops(String.valueOf(loops.getValue()));
    }

    /** Number of iterations to use. */
    public B withLoops(String loops) {
      this.loops$value = loops;
      this.loops$set = true;
      return self();
    }
  }
}
