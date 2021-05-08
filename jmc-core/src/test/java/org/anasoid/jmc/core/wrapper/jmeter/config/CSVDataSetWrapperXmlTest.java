package org.anasoid.jmc.core.wrapper.jmeter.config;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.test.utils.SetterTestUtils;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.config.ShareMode;
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
 * Date :   13-Apr-2021
 */

class CSVDataSetWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/config";

  private static final String NODE_NAME = "CSVDataSet";

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(CSVDataSetWrapper.builder().withFilename("/myfile"));
  }

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(
                CSVDataSetWrapper.builder()
                    .withName("CSV Data Set Config")
                    .addvariable(new Variable("var"))
                    .withFilename("/myfile")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/csv.config.default.jmx", NODE_NAME);
  }

  @Test
  void testInverseDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(
                CSVDataSetWrapper.builder()
                    .withName("CSV Data Set Config inverse")
                    .withFilename("/myfileinverse")
                    .withQuotedData(true)
                    .withDelimiter("|")
                    .withShareMode(ShareMode.SHARE_THREAD)
                    .withStopThread(true)
                    .withIgnoreFirstLine(true)
                    .withFileEncoding("myencoding")
                    .withRecycle(false)
                    .addvariable(new Variable("var1"))
                    .addvariable(new Variable("var2"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/csv.config.inverse.jmx", NODE_NAME);
  }

  @Test
  void testInverseVar() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addConfig(
                CSVDataSetWrapper.builder()
                    .withName("CSV Data Set Config inverse")
                    .withFilename("/myfileinverse")
                    .withQuotedData(new Variable("quoted"))
                    .withDelimiter(";")
                    .withShareMode(new Variable("shared"))
                    .withStopThread(new Variable("stop-eof"))
                    .withIgnoreFirstLine(new Variable("yes"))
                    .withFileEncoding("myencoding")
                    .withRecycle(new Variable("r-eof"))
                    .addvariables(Variable.asVariables("var1", "var2"))
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/csv.config.var.inverse.jmx", NODE_NAME);
  }
}
