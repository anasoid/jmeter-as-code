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
 * Date :   31-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.application;

import com.thoughtworks.xstream.XStream;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.apache.jorphan.collections.HashTree;

/** Main application for Test. */
public class ApplicationTest {

  private static List<Class<?>> listClazz;
  private final TestPlanWrapper testPlanWrapper;
  private final AbstractTestElementWrapper<?> testElement;
  private boolean testMode;

  @SuppressWarnings("PMD.NullAssignment")
  public ApplicationTest(TestPlanWrapper testPlanWrapper) {
    this.testPlanWrapper = testPlanWrapper;
    this.testElement = null;
  }

  /** Only for Test. */
  @SuppressWarnings("PMD.NullAssignment")
  protected ApplicationTest(AbstractTestElementWrapper<?> testElement) {
    this.testElement = testElement;
    this.testPlanWrapper = null;
    testMode = true;
  }

  /**
   * Generate Jmx file.
   *
   * @param out Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public void toJmx(Writer out) throws IOException {
    ScriptWrapper script = createScript();
    try (out) {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator());
      getXstream().toXML(script, out);
    }
  }

  /**
   * Generate Jmx file.
   *
   * @param file Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public void toJmx(File file) throws IOException {
    this.toJmx(Files.newBufferedWriter(Paths.get(file.getPath()), StandardCharsets.UTF_8));
  }

  protected ScriptWrapper createScript() {
    ScriptWrapper script;
    if (testMode) {
      script = new ScriptWrapper().setTesPlan(testElement);
    } else {
      script = new ScriptWrapper().setTesPlan(testPlanWrapper);
    }

    return script;
  }

  private XStream getXstream() {
    XStream xstream = new XStream();
    xstream.setMode(XStream.NO_REFERENCES);
    xstream.aliasSystemAttribute(null, "class");
    xstream.alias("hashTree", HashTree.class);
    List<Class<?>> clazzs = new ArrayList<>();
    clazzs.add(ScriptWrapper.class);
    clazzs.addAll(getProcessClazz());
    Class<?>[] clazzArray = new Class[clazzs.size()];
    clazzArray = clazzs.toArray(clazzArray);
    xstream.processAnnotations(clazzArray);
    return xstream;
  }

  @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
  private static synchronized List<Class<?>> getProcessClazz() {
    if (listClazz == null) {
      try (ScanResult scanResult =
          new ClassGraph()
              .enableClassInfo()
              .rejectJars(
                  "gradle-*.jar",
                  "junit-*.jar",
                  "log4j-*.jar",
                  "ApacheJMeter_*-*.jar",
                  "groovy-*.jar",
                  "commons-*.jar",
                  "wiremock-*.jar")
              .scan()) {
        ClassInfoList classInfoList =
            scanResult.getSubclasses(AbstractTestElementWrapper.class.getName());
        listClazz = classInfoList.stream().map(c -> c.loadClass(true)).collect(Collectors.toList());
      }
    }
    return listClazz;
  }
}
