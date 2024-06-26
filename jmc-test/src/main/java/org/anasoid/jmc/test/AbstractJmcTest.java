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

package org.anasoid.jmc.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import javax.xml.transform.Source;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.util.FileUtils;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.test.log.LogMonitor;
import org.anasoid.jmc.test.utils.xmlunit.JmcXmlComparator;
import org.anasoid.jmc.test.utils.xmlunit.filter.AttributesFilterManager;
import org.anasoid.jmc.test.utils.xmlunit.filter.JmcXmlFilterAttr;
import org.anasoid.jmc.test.utils.xmlunit.filter.JmcXmlFilterNode;
import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Diff;

/** Abstract Class for tests. */
@SuppressWarnings({"PMD.AbstractClassWithoutAbstractMethod", "PMD.TooManyMethods"})
public abstract class AbstractJmcTest {

  @BeforeEach
  void beforeEach() {
    LogMonitor.reset();
  }

  @AfterAll
  static void afterAll() {}

  protected Source getSource(String content) {
    return Input.fromString(content).build();
  }

  protected void println(String content) {
    System.out.println(content); // NOPMD
  }

  protected void checkWrapper(
      TestPlanWrapper testPlanWrapper, String jmxFile, String node, String... ignoreProperties)
      throws IOException {

    String wrapperContent = toTmpFile(testPlanWrapper, node + "_");
    String wrapperContentFragment = getFragmentSingleNode(wrapperContent, node);
    println("wrapperContentFragment :" + wrapperContentFragment);

    String expectedContent = readFile(jmxFile);
    String expectedContentFragment = getFragmentSingleNode(expectedContent, node);
    println("expectedContentFragment :" + expectedContentFragment);

    Diff diff = JmcXmlComparator.compare(expectedContentFragment, wrapperContentFragment);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff, ignoreProperties), node + "  not identical " + diff);
  }

  protected void checkWrapper(TestPlanWrapper testPlanWrapper, String jmxFile) throws IOException {
    checkWrapper(
        testPlanWrapper, jmxFile, null, Arrays.asList(AttributesFilterManager.getCommentFilter()));
  }

  protected void checkWrapper(
      TestPlanWrapper testPlanWrapper,
      String jmxFile,
      List<JmcXmlFilterAttr> filterAttrs,
      List<JmcXmlFilterNode> filterNodes,
      String... ignoreProperties)
      throws IOException {

    String wrapperContent = toTmpFile(testPlanWrapper, "node_");
    println("wrapperContent :" + wrapperContent);
    String expectedContent = readFile(jmxFile);
    println("expectedContent :" + expectedContent);

    Diff diff = JmcXmlComparator.compare(expectedContent, wrapperContent, filterAttrs, filterNodes);
    Assertions.assertFalse(
        JmcXmlComparator.hasDifferences(diff, ignoreProperties), "not identical " + diff);
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

    applicationTest.check();
    return Files.readString(tmpPath);
  }

  /**
   * return ApplicationTest.
   *
   * @return ApplicationTest.
   */
  protected ApplicationTest toApplicationTest(TestPlanWrapper testPlanWrapper, String tmpFilename)
      throws IOException {

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
    Path tmpPath = Files.createTempFile(tmpFilename, ".jmx");

    applicationTest.toJmx(tmpPath.toFile());
    String content = Files.readString(tmpPath);
    println("content :" + content);
    applicationTest.check();

    return applicationTest;
  }

  /**
   * return ApplicationTest.
   *
   * @return ApplicationTest.
   */
  @SuppressWarnings({"PMD.TooManyMethods", "PMD.LooseCoupling"})
  protected HashTree toHashTree(TestPlanWrapper testPlanWrapper, String tmpFilename)
      throws IOException {

    ApplicationTest applicationTest = new ApplicationTest(testPlanWrapper);
    Path tmpPath = Files.createTempFile(tmpFilename, ".jmx");

    applicationTest.toJmx(tmpPath.toFile());
    String content = Files.readString(tmpPath);
    println("content :" + content);
    applicationTest.check();

    return SaveService.loadTree(tmpPath.toFile());
  }

  /**
   * read file from resource as string.
   *
   * @param resource resource path.
   * @return file content as string.
   */
  protected String readFile(String resource) {
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
