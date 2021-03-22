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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

/**
 * Wrapper for ThreadGroup.
 *
 * @see ThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamAlias("ThreadGroup")
public class ThreadGroupWrapper extends AbstractThreadGroupWrapper<ThreadGroup, ThreadGroupGui> {

  @XStreamOmitField private static final long serialVersionUID = -5025050304929170782L;

  /** Specify Thread lifetime. */
  @JmcProperty(ThreadGroup.SCHEDULER)
  @Default
  @Getter
  private Boolean scheduler = false;

  /** Specify Thread lifetime. */
  @JmcProperty(ThreadGroup.DELAYED_START)
  @Default
  @Getter
  @JmcSkipDefault("false")
  private Boolean delayedStartup = false;

  /** Duration (seconds). */
  @JmcProperty(value = ThreadGroup.DURATION)
  @Getter
  @JmcNullAllowed
  private String durationAsVar;

  /** Startup delay (seconds). */
  @JmcProperty(value = ThreadGroup.DELAY)
  @Getter
  @JmcNullAllowed
  private String delayAsVar;

  /** Ramp-up period (seconds). */
  @JmcProperty(value = ThreadGroup.RAMP_TIME)
  @Getter
  @Builder.Default
  private String rampUpAsVar = "1";

  /** loops number. */
  @XStreamOmitField private @Builder.Default @Getter String loopsAsVar = "1";
  /**
   * In spite of the name, this is actually used to determine if the loop controller is repeatable.
   * The value is only used in nextIsNull() when the loop end condition has been detected: If
   * forever==true, then it calls resetLoopCount(), otherwise it calls setDone(true). Loop
   * Controllers always set forever=true, so that they will be executed next time the parent invokes
   * them. Thread Group sets the value false, so nextIsNull() sets done, and the Thread Group will
   * not be repeated. However, it's not clear that a Thread Group could ever be repeated.
   */
  @XStreamOmitField private @Builder.Default @Getter Boolean continueForever = false;

  @Override
  public void internalInit() {
    super.internalInit();
    LoopControllerWrapper.LoopControllerWrapperBuilder<?, ?> samplerControllerBuilder =
        LoopControllerWrapper.builder().withName("Loop Controller").withContinueForever(false);
    if (Boolean.TRUE.equals(continueForever)) {
      samplerControllerBuilder.withLoops(-1);
    } else {
      samplerControllerBuilder.withLoopsAsVar(loopsAsVar);
    }

    samplerController = samplerControllerBuilder.build();
  }

  /** builder. */
  public abstract static class ThreadGroupWrapperBuilder<
          C extends ThreadGroupWrapper, B extends ThreadGroupWrapperBuilder<C, B>>
      extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<
          ThreadGroup, ThreadGroupGui, C, B> {

    /** set rampUp. */
    public B withRampUp(int rampUp) {
      return withRampUpAsVar(String.valueOf(rampUp));
    }

    /** set duration. */
    public B withDuration(int duration) {
      return withDurationAsVar(String.valueOf(duration));
    }

    /** set delay. */
    public B withDelay(int delay) {
      return withDelayAsVar(String.valueOf(delay));
    }

    /** set loops. */
    public B withLoops(int loops) {
      return withLoopsAsVar(String.valueOf(loops));
    }
  }

  @Override
  public Class<ThreadGroup> getTestClass() {
    return ThreadGroup.class;
  }

  @Override
  public Class<ThreadGroupGui> getGuiClass() {
    return ThreadGroupGui.class;
  }
}
