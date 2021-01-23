package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;

@SuperBuilder(setterPrefix = "with")
public class LoopControllerWrapper
    extends GenericControllerWrapper<LoopController, LoopControlPanel> {

  /**
   * Infinite.
   */
  @JmcProperty("LoopController.continue_forever")
  @Builder.Default
  @Getter
  Boolean continueForever = false;
  /** Loop Count. */
  @JmcProperty(LoopController.LOOPS)
  @Getter
  private Integer loops;

  @Override
  public Class<LoopController> getTestClass() {
    return LoopController.class;
  }

  @Override
  public Class<LoopControlPanel> getGuiClass() {
    return LoopControlPanel.class;
  }
}
