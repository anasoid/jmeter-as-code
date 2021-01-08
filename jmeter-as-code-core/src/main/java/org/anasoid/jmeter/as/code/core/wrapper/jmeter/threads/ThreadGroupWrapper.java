package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.AutoConvert;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.apache.jmeter.threads.ThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;

@SuperBuilder(setterPrefix = "with")
public class ThreadGroupWrapper extends AbstractThreadGroupWrapper<ThreadGroup, ThreadGroupGui> {

  private @Getter Boolean scheduler;
  private @Getter Long duration;
  private @Getter Long delay;
  private @Builder.Default @Getter Integer rampUp = 1;

  // For Loop Controller

  private @AutoConvert(false) @Builder.Default @Getter Boolean continueForever = false;
  private @AutoConvert(false) @Builder.Default @Getter Integer loops = 1;

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
