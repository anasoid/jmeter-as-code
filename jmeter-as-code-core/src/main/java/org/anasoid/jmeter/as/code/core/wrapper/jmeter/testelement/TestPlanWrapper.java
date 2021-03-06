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
 * Date :   05-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.AbstractThreadGroupWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestPlan;

/**
 * Wrapper for TestPlan.
 *
 * @see TestPlan
 */
@SuperBuilder(setterPrefix = "with")
@XStreamAlias("TestPlan")
public class TestPlanWrapper extends AbstractTestElementWrapper<TestPlan>
    implements JMeterGUIWrapper<TestPlanGui> {

  /** Functional Test Mode (i.e. save Response Data and Sampler Data) */
  @JmcProperty("TestPlan.functional_mode")
  @Getter
  private boolean functionalMode;

  /** Run Thread Groups consecutively (i.e. one at a time). */
  @JmcProperty("TestPlan.serialize_threadgroups")
  @Getter
  private boolean serialized;
  /** Run tearDown Thread Groups after shutdown of main threads. */
  @JmcProperty("TestPlan.tearDown_on_shutdown")
  @JmcSkipDefault("false")
  @Getter
  @Default
  private boolean tearDownOnShutdown = true;

  /**
   * Set the classpath for the test plan. If the classpath is made up from more then one path, the
   * parts must be separated with CLASSPATH_SEPARATOR.
   */
  @JmcProperty("TestPlan.user_define_classpath")
  @Getter
  @Default
  private String testPlanClasspath = "";

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "TestPlan.user_defined_variables",
      testname = "User Defined Variables",
      elementType = Arguments.class,
      guiclass = ArgumentsPanel.class,
      testclass = Arguments.class)
  @Builder.Default
  @Getter
  @JmcEmptyAllowed
  private List<ArgumentWrapper> arguments = new ArrayList<>();

  @Override
  public Class<TestPlanGui> getGuiClass() {
    return TestPlanGui.class;
  }

  @Override
  public Class<TestPlan> getTestClass() {
    return TestPlan.class;
  }

  /** Builder. */
  @SuppressWarnings("PMD.UselessOverridingMethod")
  public abstract static class TestPlanWrapperBuilder<
          C extends TestPlanWrapper, B extends TestPlanWrapperBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<TestPlan, C, B> {

    /**
     * Add ThreadGroup as child in tree.
     *
     * @param child child.
     */
    public B addChild(AbstractThreadGroupWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }

    /**
     * Add confElement as child in tree.
     *
     * @param child child.
     */
    public B addChild(ConfigTestElementWrapper<?, ?> child) { // NOSONAR
      return super.addChild(child);
    }

    protected B withArguments(ArgumentWrapper child) { // NOSONAR
      return self();
    }

    /**
     * Add arguments. Each argument consists of a name/value pair, as well as (optional) metadata.
     *
     * @param arguments List of arguments.
     */
    public B addArguments(Collection<ArgumentWrapper> arguments) {
      if (!this.arguments$set) {
        this.arguments$value = new ArrayList<>();
      }
      this.arguments$value.addAll(arguments);
      this.arguments$set = true;

      return self();
    }

    /**
     * Add argument, consists of a name/value pair, default metadata is '='.
     *
     * @param name name.
     * @param value value.
     */
    public B addArgument(String name, String value) {
      addArgument(ArgumentWrapper.builder().withName(name).withValue(value).build());
      return self();
    }

    /**
     * Add arguments. Each argument consists of a name/value pair, as well as (optional) metadata.
     *
     * @param argument argument. to be add
     */
    public B addArgument(ArgumentWrapper argument) {
      if (!this.arguments$set) {
        this.arguments$value = new ArrayList<>();
      }
      this.arguments$value.add(argument);
      this.arguments$set = true;

      return self();
    }
  }
}
