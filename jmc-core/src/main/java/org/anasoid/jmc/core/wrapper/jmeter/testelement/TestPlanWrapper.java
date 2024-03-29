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

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.ControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.TestFragmentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
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
@JmcChildrenTypes(type = {ThreadWrapper.class, TestFragmentWrapper.class})
@JmcDefaultName("Test Plan")
public class TestPlanWrapper extends AbstractTestElementWrapper<TestPlan>
    implements JMeterGUIWrapper<TestPlanGui> {

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
  private final List<ArgumentWrapper> arguments = new ArrayList<>();
  /** Functional Test Mode (i.e. save Response Data and Sampler Data) */
  @JmcProperty("TestPlan.functional_mode")
  @Getter
  @Setter
  private boolean functionalMode;
  /** Run Thread Groups consecutively (i.e. one at a time). */
  @JmcProperty("TestPlan.serialize_threadgroups")
  @Getter
  @Setter
  private boolean serialized;
  /** Run tearDown Thread Groups after shutdown of main threads. */
  @JmcProperty("TestPlan.tearDown_on_shutdown")
  @JmcSkipDefault("false")
  @Getter
  @Setter
  @Default
  private boolean tearDownOnShutdown = true;
  /**
   * Set the classpath for the test plan. If the classpath is made up from more then one path, the
   * parts must be separated with CLASSPATH_SEPARATOR.
   */
  @JmcProperty("TestPlan.user_define_classpath")
  @Getter
  @Setter
  @Default
  private String testPlanClasspath = "";

  @Override
  public Class<?> getGuiClass() {
    return TestPlanGui.class;
  }

  @Override
  public Class<?> getTestClass() {
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
     * @param thread thread.
     */
    public B addThread(ThreadWrapper<?> thread) { // NOSONAR
      return addThreads(Arrays.asList(thread));
    }

    /**
     * Add ThreadGroup as child in tree.
     *
     * @param template ThreadGroup template.
     */
    public <T extends ThreadWrapper<?>> B addThread(JmcTemplate<T> template) {
      return addThread(template.generate());
    }

    /**
     * Add ThreadGroup as child in tree.
     *
     * @param threads list of threads.
     */
    public B addThreads(List<ThreadWrapper<?>> threads) { // NOSONAR
      return super.withChildren(threads);
    }

    protected B withArguments(List<ArgumentWrapper> arguments) {
      this.arguments$value = arguments;
      this.arguments$set = true;
      return self();
    }

    /**
     * Add arguments. Each argument consists of a name/value pair, as well as (optional) metadata.
     *
     * @param arguments List of arguments.
     */
    public B addArguments(Collection<ArgumentWrapper> arguments) {
      if (!this.arguments$set) {
        withArguments(new ArrayList<>());
      }
      this.arguments$value.addAll(arguments);

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
      return addArguments(Arrays.asList(argument));
    }

    /** Add TestFragment. */
    public B addTestFragment(TestFragmentWrapper fragment) {
      return addTestFragments(Arrays.asList(fragment));
    }

    /** Add TestFragment. */
    public B addTestFragment(JmcTemplate<TestFragmentWrapper> template) {
      return addTestFragment(template.generate());
    }

    /** Add TestFragment as child in tree. */
    public B addTestFragments(List<TestFragmentWrapper> fragments) {
      return withChildren(fragments);
    }
  }
}
