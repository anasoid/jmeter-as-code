package org.anasoid.jmc.core.wrapper.jmeter.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.Arrays;
import java.util.List;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.control.TestFragmentController;
import org.apache.jmeter.control.gui.TestFragmentControllerGui;

/**
 * Wrapper for TestFragmentController.
 *
 * @see TestFragmentController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName(TestFragmentWrapper.DEFAULT_NAME)
public class TestFragmentWrapper extends AbstractTestElementWrapper<TestFragmentController>
    implements JMeterGUIWrapper<TestFragmentControllerGui> {

  @XStreamOmitField public static final String DEFAULT_NAME = "Test Fragment";

  @Override
  public Boolean getEnabled() {
    return false;
  }

  @Override
  public Class<?> getGuiClass() {
    return TestFragmentControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return TestFragmentController.class;
  }

  /** builder. */
  public abstract static class TestFragmentWrapperBuilder<
          C extends TestFragmentWrapper, B extends TestFragmentWrapperBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<
          TestFragmentController, C, B> {

    /** Add Controller. */
    public B addController(ControllerWrapper<?> controller) { // NOSONAR
      return addControllers(Arrays.asList(controller));
    }

    /** Add Controller. */
    public <E extends ControllerWrapper<?>> B addController(JmcTemplate<E> template) { // NOSONAR
      return addController(template.generate());
    }

    /** Add Controllers as child in tree. */
    public B addControllers(List<ControllerWrapper<?>> controllers) { // NOSONAR
      return withChildren(controllers);
    }
  }
}
