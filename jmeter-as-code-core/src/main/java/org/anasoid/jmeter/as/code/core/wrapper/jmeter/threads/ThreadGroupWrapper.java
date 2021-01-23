package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@SuperBuilder(setterPrefix = "with")
@XStreamAlias("ThreadGroup")
@XStreamConverter(value = TestElementConverter.class)
public class ThreadGroupWrapper extends AbstractThreadGroupWrapper<ThreadGroup, ThreadGroupGui> {

  /** Specify Thread lifetime. */
  @JmcProperty(ThreadGroup.SCHEDULER)
  private @Getter Boolean scheduler;

  /** Duration (seconds). */
  @JmcProperty(ThreadGroup.DURATION)
  private @Getter Long duration;

  /** Startup delay (seconds). */
  @JmcProperty(ThreadGroup.DELAY)
  private @Getter Long delay;

  /** Ramp-up period (seconds). */
  @JmcProperty(ThreadGroup.RAMP_TIME)
  private @Builder.Default @Getter Integer rampUp = 1;

  /**
   * In spite of the name, this is actually used to determine if the loop controller is repeatable.
   * The value is only used in nextIsNull() when the loop end condition has been detected: If
   * forever==true, then it calls resetLoopCount(), otherwise it calls setDone(true). Loop
   * Controllers always set forever=true, so that they will be executed next time the parent invokes
   * them. Thread Group sets the value false, so nextIsNull() sets done, and the Thread Group will
   * not be repeated. However, it's not clear that a Thread Group could ever be repeated.
   */
  @XStreamOmitField private @Builder.Default @Getter Boolean continueForever = false;
  /** loops number. */
  @XStreamOmitField private @Builder.Default @Getter Integer loops = 1;

  @Override
  public void init() {
    samplerController =
        LoopControllerWrapper.builder()
            .withLoops(loops)
            .withContinueForever(continueForever)
            .build();
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
