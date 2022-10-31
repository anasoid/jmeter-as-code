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
 * Date :   14-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.anasoid.jmc.plugins.utils.ExecutorUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.engine.util.NoThreadClone;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestStateListener;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for Java TestElement.
 */
@SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
public abstract class AbstractJavaTestElement<T extends JavaTestElementExecutor>
    extends AbstractTestElement implements TestStateListener, NoThreadClone {

  public static final String PARAMETERS = "parameters";

  public static final String EXECUTOR_CLASS = "executorClass";

  private static final long serialVersionUID = -3117849471442160201L;

  public String getExecutorClass() {
    return getPropertyAsString(EXECUTOR_CLASS);
  }

  public void setExecutorClass(String clazz) {
    setProperty(EXECUTOR_CLASS, clazz);
  }

  protected AbstractJavaTestElement() {
    setArguments(new Arguments());
  }

  /**
   * Set the arguments (parameters) for the BackendListenerClient to be executed with.
   *
   * @param args the new arguments. These replace any existing arguments.
   */
  public void setArguments(Arguments args) {

    setProperty(new TestElementProperty(PARAMETERS, args));
  }

  /**
   * Get the arguments (parameters) for the BackendListenerClient to be executed with.
   *
   * @return the arguments
   */
  public Arguments getArguments() {
    return (Arguments) getProperty(PARAMETERS).getObjectValue();
  }

  private T executor;

  protected T getExecutor() {
    if (executor != null) {
      return executor;
    }

    synchronized (this) {
      if (executor == null) {
        executor = (T) ExecutorUtils.getExecutor(getExecutorClass(), this);
      }
    }
    return executor;
  }

  protected String getLabel() {
    return getName();
  }

  protected JMeterContext getJMeterContext() {
    return JMeterContextService.getContext();
  }

  protected JMeterVariables getJMeterVariables() {
    return JMeterContextService.getContext().getVariables();
  }

  protected Properties getJMeterProperties() {
    return JMeterUtils.getJMeterProperties();
  }

  protected Logger getExecutorLogger() {
    return LoggerFactory.getLogger(getExecutorClass() + "." + getName());
  }

  protected Sampler getCurrentSampler() {
    return getJMeterContext().getCurrentSampler();
  }

  protected SampleResult getPreviousResult() {
    return getJMeterContext().getPreviousResult();
  }

  protected Map<String, String> getParameters() {

    Map<String, String> result = new HashMap<>();
    JMeterProperty property = getProperty(PARAMETERS);
    if (property.getObjectValue() instanceof Arguments) {
      Arguments arguments = (Arguments) property.getObjectValue();
      Iterator iterator = arguments.iterator();
      while (iterator.hasNext()) {
        TestElementProperty argument = (TestElementProperty) iterator.next();
        result.put(argument.getName(), argument.getStringValue());
      }
    }
    return result;
  }

  @Override
  public void testStarted() {
    executor = null; // NOPMD - Initialize executor
    getExecutor().onStartTest();
  }

  @Override
  public void testStarted(String host) {
    testStarted();
  }

  @Override
  public void testEnded() {
    getExecutor().onEndTest();
    executor = null; // NOPMD - Initialize executor
  }

  @Override
  public void testEnded(String host) {
    testEnded();
  }
}
