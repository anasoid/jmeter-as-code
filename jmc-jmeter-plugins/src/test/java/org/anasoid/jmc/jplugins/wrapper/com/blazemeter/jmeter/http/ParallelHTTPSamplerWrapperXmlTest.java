package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.http;

import com.blazemeter.jmeter.http.ParallelHTTPSampler;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Test;

class ParallelHTTPSamplerWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH = ROOT_PATH + "/com/blazemeter/jmeter/http";

  private static final String NODE_NAME = ParallelHTTPSampler.class.getName();

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(ParallelHTTPSamplerWrapper.builder());
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(ParallelHTTPSamplerWrapper.builder().build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/ParallelHTTPRequests.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addSampler(
                        ParallelHTTPSamplerWrapper.builder().addUrl("url1").addUrl("url2").build())
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/ParallelHTTPRequests.inverse.jmx", NODE_NAME);
  }
}
