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
}
