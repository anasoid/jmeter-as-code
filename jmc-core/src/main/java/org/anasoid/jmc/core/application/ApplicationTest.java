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

package org.anasoid.jmc.core.application;

import com.thoughtworks.xstream.XStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.anasoid.jmc.core.application.interceptors.PrepareInterceptor;
import org.anasoid.jmc.core.application.validator.NodeValidatorManager;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.save.SaveService;
import org.apache.jorphan.collections.HashTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Main application for Test. */
public class ApplicationTest {

  private static final Logger LOG = LoggerFactory.getLogger(ApplicationTest.class);

  static {
    LOG.info("ApplicationTest Initialization");
    init();
  }

  private final TestPlanWrapper testPlanWrapper;
  private final TestElementWrapper<?> testElement;
  private final List<PrepareInterceptor> defaultPostInterceptors = new ArrayList<>();
  private final List<PrepareInterceptor> defaultPreInterceptors =
      Arrays.asList(new InitInterceptor(), new PrepareModuleControllerInterceptor());
  private final List<PrepareInterceptor> prepareInterceptors;
  private boolean testMode;

  /**
   * create application test.
   *
   * @param testPlanWrapper test plan.
   */
  public ApplicationTest(TestPlanWrapper testPlanWrapper) {
    this(testPlanWrapper, new ArrayList<>());
  }

  /**
   * create application test.
   *
   * @param testPlanWrapper test plan.
   * @param prepareInterceptors interceptors.
   */
  @SuppressWarnings("PMD.NullAssignment")
  public ApplicationTest(
      TestPlanWrapper testPlanWrapper, List<PrepareInterceptor> prepareInterceptors) {
    this.testPlanWrapper = testPlanWrapper;
    this.testElement = null;
    List<PrepareInterceptor> interceptors = new ArrayList<>(defaultPreInterceptors);
    if (prepareInterceptors != null) {
      interceptors.addAll(prepareInterceptors);
    }
    interceptors.addAll(defaultPostInterceptors);
    this.prepareInterceptors = interceptors;
  }

  /** Only for Test. */
  @SuppressWarnings("PMD.NullAssignment")
  protected ApplicationTest(
      TestElementWrapper<?> testElement, List<PrepareInterceptor> prepareInterceptors) {
    this.testElement = testElement;
    this.testPlanWrapper = null;
    List<PrepareInterceptor> interceptors = new ArrayList<>(defaultPreInterceptors);
    if (prepareInterceptors != null) {
      interceptors.addAll(prepareInterceptors);
    }
    interceptors.addAll(defaultPostInterceptors);
    this.prepareInterceptors = interceptors;
    testMode = true;
  }

  /** Only for Test. */
  protected ApplicationTest(TestElementWrapper<?> testElement) {
    this(testElement, new ArrayList<>());
  }

  private static void init() {
    JMeterHome.getInstance().init();
  }

  private static void isInit() {
    if (!JMeterHome.getInstance().isInit()) {
      throw new IllegalStateException("Jmeter is not correctly initialized");
    }
  }

  /**
   * Generate Jmx file.
   *
   * @param out Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public TestElementWrapper toJmx(Writer out) throws IOException { // NOSONAR
    ScriptWrapper script = createScript();
    try (out) {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator());
      getXstream().toXML(script, out);
    }
    if (testMode) {
      return testElement;
    } else {
      return script.getTestPlan();
    }
  }

  /**
   * Generate Jmx file.
   *
   * @param file Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public TestElementWrapper toJmx(File file) throws IOException { // NOSONAR
    Path path = Paths.get(file.getPath());
    path.toFile().getParentFile().mkdirs();
    return this.toJmx(Files.newBufferedWriter(path, StandardCharsets.UTF_8));
  }

  protected ScriptWrapper createScript() {
    ScriptWrapper script;
    if (testMode) {
      script = new ScriptWrapper().setTestPlan(testElement);
    } else {

      try {
        script = new ScriptWrapper().setTestPlan(clone(testPlanWrapper));
      } catch (IOException | ClassNotFoundException e) {
        throw new ConversionException(e);
      }
    }
    prepare(script);
    validate(script);
    return script;
  }

  protected void validate(ScriptWrapper script) {
    NodeValidatorManager.validate(script.getTestPlan());
  }

  private void prepare(
      TestElementWrapper<?> testPlan,
      TestElementWrapper<?> testElement,
      PrepareInterceptor interceptor,
      Set<TestElementWrapper<?>> history) {
    if (!history.contains(testElement)) {

      if (interceptor.support(testElement)) {
        interceptor.prepare(testPlan, testElement);
      }

      history.add(testElement);
      if (CollectionUtils.isNotEmpty(testElement.getChildren())) {
        for (TestElementWrapper<?> childElement : testElement.getChildren()) {
          prepare(testPlan, childElement, interceptor, history);
        }
      }
    }
  }

  protected void prepare(ScriptWrapper script) {
    for (PrepareInterceptor interceptor : prepareInterceptors) {
      Set<TestElementWrapper<?>> history = new HashSet<>(); // NOPMD
      if (CollectionUtils.isNotEmpty(prepareInterceptors)) {
        prepare(script.getTestPlan(), script.getTestPlan(), interceptor, history);
      }
    }
  }

  private TestPlanWrapper clone(TestPlanWrapper in) throws IOException, ClassNotFoundException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(baos);
    oos.writeObject(in);
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bais);
    return (TestPlanWrapper) ois.readObject();
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

  /**
   * Check test, Parsing by jmeter.
   *
   * @throws IOException – If an I/O error occurs.
   */
  public void check() throws IOException {
    if (!testMode) {
      StandardJMeterEngine jmeter = new StandardJMeterEngine();

      File tmp = File.createTempFile("check_jmeter_", ".jmx");
      LOG.info("Check tmp file : {}", tmp);
      this.toJmx(Files.newBufferedWriter(Paths.get(tmp.getPath()), StandardCharsets.UTF_8));

      HashTree testPlanTree = SaveService.loadTree(tmp);

      // Run Test Plan
      jmeter.configure(testPlanTree);
    }
  }

  private XStream getXstream() {
    return new JmcXstream();
  }
}
