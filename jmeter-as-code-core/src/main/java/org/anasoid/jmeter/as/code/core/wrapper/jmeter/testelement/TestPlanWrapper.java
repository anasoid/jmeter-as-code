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
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentsWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.AbstractThreadGroupWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestPlan;

@SuperBuilder(setterPrefix = "with")
@XStreamAlias("TestPlan")
public class TestPlanWrapper extends AbstractTestElementWrapper<TestPlan>
    implements JMeterGUIWrapper<TestPlanGui> {

  @JmcProperty("TestPlan.functional_mode")
  @Getter
  private boolean functionalMode;

  @JmcProperty("TestPlan.serialize_threadgroups")
  @Getter
  private boolean serialized;

  @JmcProperty("TestPlan.tearDown_on_shutdown")
  @Getter
  private boolean tearDownOnShutdown;

  @Getter @Default @XStreamOmitField private List<ArgumentWrapper> arguments = new ArrayList<>();

  @JmcProperty("TestPlan.user_defined_variables")
  protected ArgumentsWrapper getArgument() {
    return ArgumentsWrapper.builder().addArguments(arguments).build();
  }

  @Getter @XStreamOmitField
  // @JmcProperty("TestPlan.user_defined_variables")
  private ArgumentsWrapper argumentsWrapper;

  @Override
  public void init() {
    argumentsWrapper = ArgumentsWrapper.builder().addArguments(arguments).build();
  }

  @Override
  public Class<TestPlanGui> getGuiClass() {
    return TestPlanGui.class;
  }

  @Override
  public Class<TestPlan> getTestClass() {
    return TestPlan.class;
  }

  public abstract static class TestPlanWrapperBuilder<
          C extends TestPlanWrapper, B extends TestPlanWrapperBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<TestPlan, C, B> {

    public B addChild(AbstractThreadGroupWrapper child) {
      return super.addChild(child);
    }

    protected B withArguments(ArgumentWrapper child) {
      return self();
    }

    public B addArguments(Collection<ArgumentWrapper> arguments) {
      if (!this.arguments$set) {
        this.arguments$value = new ArrayList<>();
      }
      this.arguments$value.addAll(arguments);
      this.arguments$set = true;

      return self();
    }

    public B addArgument(String name, String value) {
      addArgument(ArgumentWrapper.builder().withName(name).withValue(value).build());
      return self();
    }

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
