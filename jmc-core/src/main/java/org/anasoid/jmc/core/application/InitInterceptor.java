package org.anasoid.jmc.core.application;

import org.anasoid.jmc.core.application.interceptors.PrepareInterceptor;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;

/** Initialize testElements. */
class InitInterceptor implements PrepareInterceptor {

  @Override
  public boolean support(TestElementWrapper<?> testElementWrapper) {
    return true;
  }

  @Override
  public void prepare(TestElementWrapper<?> testPlan, TestElementWrapper<?> testElementWrapper) {

    InitHelper.init(testElementWrapper);
  }
}
