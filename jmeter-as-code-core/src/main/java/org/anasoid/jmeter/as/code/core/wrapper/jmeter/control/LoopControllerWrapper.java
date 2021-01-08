package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.LoopControlPanel;

@SuperBuilder(setterPrefix = "with")
public class LoopControllerWrapper
    extends GenericControllerWrapper<LoopController, LoopControlPanel> {

  @Builder.Default @Getter Boolean continueForever = false;

  @Getter private Integer loops;

  @Override
  public Class<LoopController> getTestClass() {
    return LoopController.class;
  }

  @Override
  public LoopController convert() {
    return super.convert();
  }

  @Override
  public LoopController newTarget() {
    return new LoopController();
  }

  @Override
  public Class<LoopControlPanel> getGuiClass() {
    return LoopControlPanel.class;
  }
}
