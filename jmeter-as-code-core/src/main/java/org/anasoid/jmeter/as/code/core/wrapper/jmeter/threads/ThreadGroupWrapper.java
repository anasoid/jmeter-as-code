package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.AutoConvert;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@SuperBuilder(setterPrefix = "with")
@XStreamAlias("ThreadGroup")
@XStreamConverter(value = TestElementConverter.class)
public class ThreadGroupWrapper extends AbstractThreadGroupWrapper<ThreadGroup, ThreadGroupGui> {

  @JmcProperty(ThreadGroup.SCHEDULER)
  private @Getter Boolean scheduler;

  @JmcProperty(ThreadGroup.DURATION)
  private @Getter Long duration;

  @JmcProperty(ThreadGroup.DELAY)
  private @Getter Long delay;

  @JmcProperty(ThreadGroup.RAMP_TIME)
  private @Builder.Default @Getter Integer rampUp = 1;

  // For Loop Controller
  @XStreamOmitField
  private @AutoConvert(false) @Builder.Default @Getter Boolean continueForever = false;

  @XStreamOmitField
  private @AutoConvert(false) @Builder.Default @Getter Integer loops = 1;

  @Override
  public void init() {
    samplerController= LoopControllerWrapper.builder().withLoops(loops).withContinueForever(continueForever).build();
  }



  @Override
  public Class<ThreadGroup> getTestClass() {
    return ThreadGroup.class;
  }

  @Override
  public void afterConvert(ThreadGroup dest) {
    super.afterConvert(dest);
    LoopControllerWrapper loopControllerWrapper =
        LoopControllerWrapper.builder()
            .withLoops(this.getLoops())
            .withContinueForever(this.getContinueForever())
            .build();

    dest.setSamplerController(loopControllerWrapper.convert());
    dest.initialize();
  }

  @Override
  public ThreadGroup newTarget() {
    return new ThreadGroup();
  }

  @Override
  public Class<ThreadGroupGui> getGuiClass() {
    return ThreadGroupGui.class;
  }
}
