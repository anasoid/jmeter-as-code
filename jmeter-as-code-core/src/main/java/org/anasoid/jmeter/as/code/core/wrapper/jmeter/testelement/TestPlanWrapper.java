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

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.AutoConvert;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ArgumentsWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.AbstractThreadGroupWrapper;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.testelement.TestPlan;

@SuperBuilder(setterPrefix = "with")
public class TestPlanWrapper extends AbstractTestElementWrapper<TestPlan>
    implements JMeterGUIWrapper<TestPlanGui> {

  @Getter
  @AutoConvert(false)
  private ArgumentsWrapper argumentsWrapper;

  @Override
  protected void beforeconvert() {
    super.beforeconvert();
    if (argumentsWrapper == null) {
      argumentsWrapper = ArgumentsWrapper.builder().build();
    }
  }

  @Override
  protected TestPlan internalConvert() {
    TestPlan testPlan= super.internalConvert();
    testPlan.setUserDefinedVariables(argumentsWrapper.convert());
    return testPlan;
  }

  @Override
  public TestPlan newTarget() {
    return new TestPlan();
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
  }
}
