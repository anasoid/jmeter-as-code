package org.anasoid.jmc.core.application;

import java.util.ArrayList;
import java.util.List;
import org.anasoid.jmc.core.application.interceptors.PrepareInterceptor;
import org.anasoid.jmc.core.wrapper.jmeter.control.ControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.ModuleControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.TestFragmentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;

/** Preparing ModuleController. */
class PrepareModuleControllerInterceptor implements PrepareInterceptor {

  @Override
  public boolean support(TestElementWrapper<?> testElementWrapper) {
    return testElementWrapper instanceof ModuleControllerWrapper;
  }

  @Override
  public void prepare(TestElementWrapper<?> testPlan, TestElementWrapper<?> testElementWrapper) {

    ModuleControllerWrapper moduleControllerWrapper = (ModuleControllerWrapper) testElementWrapper;
    moduleControllerWrapper.setNodePath(new ArrayList<>());
  }

  List<TestElementWrapper<?>> findTarget(
      TestFragmentWrapper rootParent, ControllerWrapper<?> module) {
    return null;
  }
}
