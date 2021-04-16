package org.anasoid.jmeter.as.code.core.wrapper.jmeter.config;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.junit.jupiter.api.Assertions;
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
 * Date :   16-Apr-2021
 */

class CSVDataSetWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH = "org/anasoid/jmeter/as/code/core/wrapper/jmeter/config";

  @Test
  void testResouce() throws IOException {

    CSVDataSetWrapper csvDataSetWrapper =
        CSVDataSetWrapper.builder()
            .withName("CSV Data Set Config")
            .addvariable(new Variable("var"))
            .withResourceFile(PARENT_PATH + "/csv.config.default.jmx")
            .build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(csvDataSetWrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    csvDataSetWrapper = (CSVDataSetWrapper) applicationTest.toJmx(wr);
    Assertions.assertTrue(csvDataSetWrapper.getFilePath().endsWith("/csv.config.default.jmx"));
  }

  @Test
  void testResourceNotFound() throws IOException {

    CSVDataSetWrapper csvDataSetWrapper =
        CSVDataSetWrapper.builder()
            .withName("CSV Data Set Config")
            .addvariable(new Variable("var"))
            .withResourceFile(PARENT_PATH + "/notfound.jmx")
            .build();
    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(csvDataSetWrapper);
    StringWriter wr = new StringWriter(); // NOPMD
    try {
      applicationTest.toJmx(wr);
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("ResourceFile"));
    }
  }
}
