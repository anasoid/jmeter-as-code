package org.anasoid.jmc.core.wrapper.jmeter.control;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Test;

class ModuleControllerWrapperTest extends AbstractJmcCoreTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/control";

  private static final String NODE_NAME = "ModuleController";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ModuleControllerWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/moduleController.default.jmx", NODE_NAME);
  }
}
