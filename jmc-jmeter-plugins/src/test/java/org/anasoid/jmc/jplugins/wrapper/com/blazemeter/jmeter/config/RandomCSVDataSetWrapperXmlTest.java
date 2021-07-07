package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.config;

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
 * Date :   07-Jul-2021
 */

import com.blazemeter.jmeter.RandomCSVDataSetConfig;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.jplugins.AbstractJmcJmeterPluginTest;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Test;

class RandomCSVDataSetWrapperXmlTest extends AbstractJmcJmeterPluginTest {

  private static final String PARENT_PATH = ROOT_PATH + "/com/blazemeter/jmeter/config";

  private static final String NODE_NAME = RandomCSVDataSetConfig.class.getName();

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(RandomCSVDataSetWrapper.builder().withFilename("/myfile"));
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                RandomCSVDataSetWrapper.builder()
                    .addvariable(new Variable("var"))
                    .withFilename("/myfile")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/RandomCSVDataSetConfig.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addConfig(
                RandomCSVDataSetWrapper.builder()
                    .withFilename("/myfile")
                    .withDelimiter(";")
                    .withFileEncoding("UTF-16")
                    .withIgnoreFirstLine(true)
                    .withIndependentListPerThread(true)
                    .withRecycle(false)
                    .withRandomOrder(false)
                    .addvariable(new Variable("var1"))
                    .addvariable(new Variable("var2"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/RandomCSVDataSetConfig.inverse.jmx", NODE_NAME);
  }
}
