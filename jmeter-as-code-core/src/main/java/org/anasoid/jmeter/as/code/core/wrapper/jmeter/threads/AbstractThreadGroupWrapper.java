package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

@SuperBuilder(setterPrefix = "with")
public abstract class AbstractThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  @JmcProperty(AbstractThreadGroup.IS_SAME_USER_ON_NEXT_ITERATION)
  @Builder.Default
  private @Getter Boolean isSameUserOnNextIteration = true;

  @JmcProperty(AbstractThreadGroup.NUM_THREADS)
  @Builder.Default
  private @Getter Integer numThreads = 1;

  @JmcProperty(AbstractThreadGroup.MAIN_CONTROLLER)
  protected @Getter GenericControllerWrapper samplerController;

  public abstract static class AbstractThreadGroupWrapperBuilder<
          T extends AbstractThreadGroup,
          G extends AbstractThreadGroupGui,
          C extends AbstractThreadGroupWrapper<T, G>,
          B extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    private B withSamplerController(GenericControllerWrapper controller) {
      // hide samplerController
      return self();
    }

    public B addChild(AbstractSamplerWrapper child) {
      return super.addChild(child);
    }

    public B addChild(GenericControllerWrapper child) {
      return super.addChild(child);
    }
  }
}
