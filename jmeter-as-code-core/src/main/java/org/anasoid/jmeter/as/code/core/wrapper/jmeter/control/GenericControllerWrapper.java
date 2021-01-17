package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.gui.AbstractControllerGui;

@SuperBuilder(setterPrefix = "with")
public abstract class GenericControllerWrapper<
        T extends GenericController, G extends AbstractControllerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  public abstract static class GenericControllerWrapperBuilder<
          T extends GenericController,
          G extends AbstractControllerGui,
          C extends GenericControllerWrapper<T, G>,
          B extends GenericControllerWrapperBuilder<T, G, C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<T, C, B> {

    public B addChild(AbstractSamplerWrapper child) {
      return super.addChild(child);
    }
  }
}
