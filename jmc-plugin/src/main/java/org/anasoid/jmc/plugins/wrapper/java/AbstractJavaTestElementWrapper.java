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
 * Date :   15-Jun-2021
 */

package org.anasoid.jmc.plugins.wrapper.java;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcOmitField;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElement;
import org.anasoid.jmc.plugins.component.java.JavaTestElementExecutor;
import org.anasoid.jmc.plugins.utils.JavaTestElementWrapperUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Main abstract class for all Java(JSR223) wrapper.
 *
 * @param <T> JmeterTestElement implementation.
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractJavaTestElementWrapper<T extends AbstractJavaTestElement>
    extends AbstractBasicChildTestElementWrapper<T>
    implements JMeterGUIWrapper<TestBeanGUI>, Validator, JavaTestElementExecutor {

  @JmcOmitField @Default @Getter private Map<String, String> parameters = new HashMap<>();

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "parameters",
      elementType = Arguments.class,
      guiclass = ArgumentsPanel.class,
      testclass = Arguments.class)
  @JmcEmptyAllowed
  protected List<ArgumentWrapper> parametersProp() {
    return JavaTestElementWrapperUtils.mapToArgumentList(parameters);
  }

  @JmcProperty("executorClass")
  protected String getExecutorClass() {
    return this.getClass().getName();
  }

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {

    JavaTestElementWrapperUtils.validate(getParametersKey(), getParameters());
  }

  /** builder. */
  public abstract static class AbstractJavaTestElementWrapperBuilder<
          T extends AbstractJavaTestElement,
          C extends AbstractJavaTestElementWrapper<T>,
          B extends AbstractJavaTestElementWrapperBuilder<T, C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          T, C, B> {

    protected B withParameters(Map<String, String> parameters) {
      this.parameters$value = parameters;
      this.parameters$set = true;
      return self();
    }

    /**
     * Add list of parameters as Map.
     *
     * @param parameters map of parameters.
     */
    public B addParameters(Map<String, String> parameters) {
      if (!parameters$set) {
        withParameters(new HashMap<>());
      }
      parameters$value.putAll(parameters);
      return self();
    }

    /**
     * Add argument, consists of a name/value pair, default metadata is '='.
     *
     * @param name name.
     * @param value value.
     */
    public B addParameter(String name, String value) {
      return addParameters(Collections.singletonMap(name, value));
    }
  }
}
