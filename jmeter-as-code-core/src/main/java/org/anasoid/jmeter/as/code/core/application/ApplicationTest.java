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
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main application for Test. */
public class ApplicationTest {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);
  private static final String SLASH = System.getProperty("file.separator");
  private static boolean initialized;
  private static List<Class<?>> listClazz;
  private final TestPlanWrapper testPlanWrapper;
  private final AbstractTestElementWrapper<?> testElement;
  private boolean testMode;

  static {
    LOG.info("ApplicationTest Initialization");
    init();
  }

  private static void init() {
    String jmeterHomeKey = "JMETER_HOME";
    String jmeterHomePath = System.getProperty(jmeterHomeKey);
    if (jmeterHomePath == null) {
      jmeterHomePath = System.getenv(jmeterHomeKey);
    }
    if (jmeterHomePath != null && new File(jmeterHomePath).exists()) {
      File jmeterHome = new File(jmeterHomePath);
      File jmeterProperties =
          new File(jmeterHome.getPath() + SLASH + "bin" + SLASH + "jmeter.properties");
      if (jmeterProperties.exists()) {

        // JMeter initialization (properties, log levels, locale, etc)
        JMeterUtils.setJMeterHome(jmeterHome.getPath());
        JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
        JMeterUtils.initLocale();
        initialized = true;
      } else {
        LOG.error(
            "Jmeter is not correctly configured, missing jmeter.properties, on : {}",
            jmeterProperties);
      }
    } else {
      LOG.error(
          "Jmeter is not correctly configured $JMETER_HOME is not correct : {} , {}",
          System.getProperty(jmeterHomeKey), System.getenv(jmeterHomeKey));
    }
  }

  private static void isInit() {
    if (!initialized) {
      throw new IllegalStateException("Jmeter is not correctly initialized");
    }
  }

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

  /**
   * Execute test.
   *
   * @throws IOException – If an I/O error occurs.
   */
  public void run() throws IOException {
    isInit();
    StandardJMeterEngine jmeter = new StandardJMeterEngine();

    File tmp = File.createTempFile("run_jmeter_", ".jmx");
    LOG.info("Run tmp file : {}", tmp);
    this.toJmx(Files.newBufferedWriter(Paths.get(tmp.getPath()), StandardCharsets.UTF_8));

    HashTree testPlanTree = SaveService.loadTree(tmp);

    // Run Test Plan
    jmeter.configure(testPlanTree);
    jmeter.run();
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
