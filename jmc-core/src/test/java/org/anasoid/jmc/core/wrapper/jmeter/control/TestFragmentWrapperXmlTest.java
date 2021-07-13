package org.anasoid.jmc.core.wrapper.jmeter.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Test;

class TestFragmentWrapperXmlTest extends AbstractJmcCoreTest {
  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/control";

  private static final String NODE_NAME = "TestFragmentController";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(TestFragmentWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder().addTestFragment(TestFragmentWrapper.builder().build()).build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/testFragment.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addTestFragment(
                TestFragmentWrapper.builder()
                    .addController(SimpleControllerWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/testFragment.inverse.jmx", NODE_NAME);
  }
}
