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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.test;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestPlan;

/** Wrapper for testing. Parent Element. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamAlias("parent")
public class ParentTestElementWrapperTesting extends AbstractTestElementWrapper<TestPlan>
    implements JMeterGUIWrapper<TestPlanGui> {

  /** Name. */
  @XStreamAsAttribute
  @XStreamAlias("testname")
  @Getter
  @JmcMandatory
  private String name;

  /** Functional Test Mode (i.e. save Response Data and Sampler Data) */
  @JmcProperty("Parent.bb1")
  @Getter
  private boolean bb1;

  @JmcProperty("Parent.ii1")
  @Getter
  private Integer ii1;

  @JmcProperty("Parent.ll1")
  @Getter
  private Long ll1;

  @JmcProperty("Parent.defaultLong")
  @Getter
  @JmcSkipDefault("10")
  @Default
  private Long defaultLong = 10L;

  @JmcProperty("Parent.method")
  public String getMethod() {
    return "method";
  }

  @Getter
  @XStreamAlias("field")
  private String field;

  @JmcProperty("Parent.ff1")
  @Getter
  @Default
  private Float ff1 = 10F;

  @JmcProperty("Parent.dd1")
  @Getter
  @Default
  private Double dd1 = 10D;

  @JmcProperty("Parent.child")
  @Getter
  @Default
  private SubChildTestingElementWrapperTesting child =
      SubChildTestingElementWrapperTesting.builder().withEnabled(true).build();

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "Parent.arguments",
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
  public abstract static class ParentTestElementWrapperTestingBuilder<
          C extends ParentTestElementWrapperTesting,
          B extends ParentTestElementWrapperTesting.ParentTestElementWrapperTestingBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<TestPlan, C, B> {

    public B addChild(ChildTestElementWrapperTesting<?, ?> child) { // NOSONAR
      return withChild(child);
    }

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void withArguments(List<ArgumentWrapper> child) {} // NOSONAR

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
