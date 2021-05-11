package org.anasoid.jmc.core.wrapper.jmeter.visualizers.backend;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Test;
/*
 * Copyright 2020-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author : anas
 * Date :   07-May-2021
 */

class BackendListenerWrapperXmlTest extends AbstractJmcTest {
  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/visualizers/backend";

  private static final String NODE_NAME = "BackendListener";

  @Test
  void testDefault() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(
                BackendListenerWrapper.builder()
                    .withName("Backend Listener")
                    .withImplementation(BackendListenerClient.InfluxDBRawBackendListenerClient)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/backendListener.default.jmx", NODE_NAME);
  }

  @Test
  void testInverse() throws IOException {
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addListener(
                BackendListenerWrapper.builder()
                    .withName("Backend Listener")
                    .withImplementation(BackendListenerClient.GraphiteBackendListenerClient)
                    .addArgument("myvar", "var")
                    .addArgument("myempty", "")
                    .addArgument("useRegexpForSamplersList", "true")
                    .withQueueSize(5555)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/backendListener.inverse.jmx", NODE_NAME);
  }
}
