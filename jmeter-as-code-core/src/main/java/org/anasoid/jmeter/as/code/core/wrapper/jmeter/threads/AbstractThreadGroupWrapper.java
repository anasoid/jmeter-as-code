package org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.apache.jmeter.threads.AbstractThreadGroup;
import org.apache.jmeter.threads.gui.AbstractThreadGroupGui;

@SuperBuilder(setterPrefix = "with")
public abstract class AbstractThreadGroupWrapper<
        T extends AbstractThreadGroup, G extends AbstractThreadGroupGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  @Builder.Default private @Getter Boolean isSameUserOnNextIteration = true;
  @Builder.Default private @Getter Integer numThreads = 1;

  public abstract static class AbstractThreadGroupWrapperBuilder<
          T extends AbstractThreadGroup,
          G extends AbstractThreadGroupGui,
          C extends AbstractThreadGroupWrapper<T, G>,
          B extends AbstractThreadGroupWrapper.AbstractThreadGroupWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    public B addChild(AbstractSamplerWrapper child) {
      return super.addChild(child);
    }

    public B addChild(GenericControllerWrapper child) {
      return super.addChild(child);
    }
  }
}
