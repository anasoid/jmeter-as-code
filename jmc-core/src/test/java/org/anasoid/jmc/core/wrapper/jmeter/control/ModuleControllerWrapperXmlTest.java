package org.anasoid.jmc.core.wrapper.jmeter.control;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Test;

class ModuleControllerWrapperXmlTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/control";

  private static final String NODE_NAME = "ModuleController";

  @Test
  void testDefault() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(s1).build())
                    .build())
            .addTestFragment(TestFragmentWrapper.builder().addController(s1).build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/moduleController.default.jmx", NODE_NAME);
  }

  @Test
  void testDefaultName() throws IOException {

    SimpleControllerWrapper s1 =
        SimpleControllerWrapper.builder().withName("Simple Controller").build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(
                        ModuleControllerWrapper.builder()
                            .withName("Module Controller")
                            .withModule(t1, s1.getName())
                            .build())
                    .build())
            .addTestFragment(t1)
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/moduleController.default.jmx", NODE_NAME);
  }
}
