package org.anasoid.jmeter.as.code.core.wrapper.jmeter.modifiers;

import java.io.IOException;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.script.ScriptLanguage;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.xmlunit.diff.Diff;
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
 * Date :   23-Apr-2021
 */

class JSR223PreProcessorWrapperXmlTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/modifiers/preprocessor";

  private static final String SCRIPT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/script";

  private static final String NODE_NAME = "JSR223PreProcessor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPreProcessor(
                JSR223PreProcessorWrapper.builder()
                    .withName("JSR223 PreProcessor")
                    .withScriptFile(SCRIPT_PATH + "/myscript.special.txt")
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, NODE_NAME + "_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/JSR223.preprocessor.defaut.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), NODE_NAME + "  not identical " + diff);
  }

  @Test
  void testDefaultFile() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPreProcessor(
                JSR223PreProcessorWrapper.builder()
                    .withName("JSR223 PreProcessor")
                    .withScriptFile("/myfile")
                    .withScriptFileResource(false)
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, NODE_NAME + "_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/JSR223.preprocessor.file.defaut.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), NODE_NAME + "  not identical " + diff);
  }

  @Test
  void testDefaultInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Test Plan")
            .addPreProcessor(
                JSR223PreProcessorWrapper.builder()
                    .withName("JSR223 PreProcessor inverse")
                    .addParameter("arg1")
                    .addParameter("arg2")
                    .withCacheKey(false)
                    .withScriptLanguage(ScriptLanguage.JAVA)
                    .withScriptFile(SCRIPT_PATH + "/script.txt")
                    .build())
            .build();
    String wrapperContent = toTmpFile(testPlanWrapper, NODE_NAME + "_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, NODE_NAME);
    String expectedContent = readFile(PARENT_PATH + "/JSR223.preprocessor.inverse.jmx");
    String expectedContentFragment = getFragmentSingleNode(expectedContent, NODE_NAME);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff), NODE_NAME + "  not identical " + diff);
  }
}