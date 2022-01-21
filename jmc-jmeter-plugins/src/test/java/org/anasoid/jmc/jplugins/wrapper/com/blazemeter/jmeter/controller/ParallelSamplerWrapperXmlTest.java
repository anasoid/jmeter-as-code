package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.controller;

import com.blazemeter.jmeter.controller.ParallelSampler;
import java.io.IOException;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.junit.jupiter.api.Test;

class ParallelSamplerWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH = ROOT_PATH + "/com/blazemeter/jmeter/controller";

  private static final String NODE_NAME = ParallelSampler.class.getName();

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ParallelSamplerWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/ParallelController.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(
                        ParallelSamplerWrapper.builder()
                            .withGenerateParent(true)
                            .withLimitMaxThreadNumber(true)
                            .withMaxThreadNumber(10)
                            .build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/ParallelController.inverse.jmx", NODE_NAME);
  }
}
