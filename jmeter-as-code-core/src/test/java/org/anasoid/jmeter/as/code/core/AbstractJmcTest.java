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
 * Date :   05-Feb-2021
 */

package org.anasoid.jmeter.as.code.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.transform.Source;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmeter.as.code.core.util.FileUtils;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.junit.jupiter.api.Assertions;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

/** Abstract Class for tests. */
@SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
public abstract class AbstractJmcTest {

  protected static final String DEFAULT_TEST_PLAN = "Test Plan";

  protected static final String DEFAULT_HTTP_REQUEST = "HTTP Request";

  protected static final String DEFAULT_THREAD_GROUP = "Thread Group";

  protected Source getSource(String content) {
    return Input.fromString(content).build();
  }

  protected void println(String content) {
    System.out.println(content); // NOPMD
  }

  protected void checkWrapper(TestPlanWrapper testPlanWrapper, String jmxFile, String node)
      throws IOException {

    String wrapperContent = toTmpFile(testPlanWrapper, node + "_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, node);
    String expectedContent = readFile(jmxFile);
    String expectedContentFragment = getFragmentSingleNode(expectedContent, node);
    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(JmcXmlComparator.hasDifferences(diff), node + "  not identical " + diff);
  }

  /**
   * save testplan to tmp file.
   *
   * @return conetnat as string.
   */
  protected String toTmpFile(TestPlanWrapper testPlanWrapper, String tmpFilename)
      throws IOException {

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
    Path tmpPath = Files.createTempFile(tmpFilename, ".jmx");

    applicationTest.toJmx(tmpPath.toFile());

    String wrapperContent = Files.readString(tmpPath);
    println("content :" + wrapperContent);

    return wrapperContent;
  }

  /**
   * read file from resource as string.
   *
   * @param resource resource path.
   * @return file content as string.
   */
  protected String readFile(String resource) throws IOException {
    return FileUtils.readResource(resource);
  }

  /**
   * Extract XML tag by node name.
   *
   * @param content source.
   * @param nodes list of hierarchical node name.
   */
  protected String getFragmentSingleNode(String content, String... nodes) {
    String result = content;
    for (String node : nodes) {
      String startTag = "<" + node;
      String endTag = "</" + node + ">";
      String cleanStart = content.substring(content.indexOf(startTag));
      if (cleanStart.contains(endTag)) {
        result = cleanStart.substring(0, cleanStart.indexOf(endTag) + endTag.length());
      } else {
        result = cleanStart.substring(0, cleanStart.indexOf("/>") + 2);
      }
    }
    return result;
  }
}
