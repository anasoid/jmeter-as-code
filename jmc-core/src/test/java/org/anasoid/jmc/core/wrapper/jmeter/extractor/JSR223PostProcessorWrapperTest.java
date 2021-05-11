package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
import org.anasoid.jmc.core.wrapper.jmc.script.ScriptLanguage;
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
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either expostss or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @author : anas
 * Date :   23-Apr-2021
 */

class JSR223PostProcessorWrapperTest extends AbstractJmcTest {

  private static final String PARENT_PATH =
      "org/anasoid/jmc/core/wrapper/jmeter/extractor/postprocessor";

  private static final String SCRIPT_PATH = "org/anasoid/jmc/core/wrapper/jmeter/script";

  private static final String NODE_NAME = "JSR223PostProcessor";

  @Test
  void testDefault() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JSR223PostProcessorWrapper.builder()
                    .withScriptFile(SCRIPT_PATH + "/myscript.special.txt")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/JSR223.postprocessor.defaut.jmx", NODE_NAME);
  }

  @Test
  void testDefaultFile() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JSR223PostProcessorWrapper.builder()
                    .withScriptFile("/myfile")
                    .withScriptFileResource(false)
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/JSR223.postprocessor.file.defaut.jmx", NODE_NAME);
  }

  @Test
  void testDefaultInverse() throws IOException {

    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addPostProcessor(
                JSR223PostProcessorWrapper.builder()
                    .withName("JSR223 PostProcessor inverse")
                    .addParameter("arg1")
                    .addParameter("arg2")
                    .withCacheKey(false)
                    .withScriptLanguage(ScriptLanguage.JAVA)
                    .withScriptFile(SCRIPT_PATH + "/script.txt")
                    .build())
            .build();

    checkWrapper(testPlanWrapper, PARENT_PATH + "/JSR223.postprocessor.inverse.jmx", NODE_NAME);
  }
}
