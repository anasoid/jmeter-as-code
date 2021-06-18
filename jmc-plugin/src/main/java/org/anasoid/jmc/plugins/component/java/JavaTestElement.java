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

import com.thoughtworks.xstream.converters.ConversionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;
import org.apache.jmeter.testelement.property.TestElementProperty;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterContextService;
import org.apache.jmeter.threads.JMeterVariables;
import org.apache.jmeter.util.JMeterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Base class for Java TestElement. */
public abstract class JavaTestElement<T extends JavaTestElementExecutor>
    extends AbstractTestElement {

  private static final long serialVersionUID = -3117849471442160201L;

  private String getExecutorClass() {
    return getPropertyAsString("executorClass");
  }

  private T executor;

  protected T getExecutor() {

    try {
      synchronized (this) {
        if (executor == null) {
          Class<?> wrapper = Class.forName(getExecutorClass());
          Method builderMethod = wrapper.getDeclaredMethod("builder");
          builderMethod.setAccessible(true); // NOSONAR
          Object builder = builderMethod.invoke(null);
          setFields(builder);
          Method buildMethod = builder.getClass().getDeclaredMethod("build");
          buildMethod.setAccessible(true); // NOSONAR
          executor = (T) buildMethod.invoke(builder);
        }
      }
      return executor;
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new ConversionException("Loading executor ", e);
    }
  }

  private void setFields(Object builder) {
    PropertyIterator propertyIterator = this.propertyIterator();
    while (propertyIterator.hasNext()) {
      JMeterProperty property = propertyIterator.next();
      setValue(builder, property);
    }
  }

  /**
   * get All field with setter annotation.
   *
   * @return list all field.
   */
  private String getWithMethod(String fieldName) {
    return "with" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
  }

  /**
   * get All field with setter annotation.
   *
   * @return list all field.
   */
  @SuppressWarnings("PMD.EmptyCatchBlock")
  private void setValue(Object source, JMeterProperty property) {

    Method[] methods = source.getClass().getMethods();
    String methodName = getWithMethod(property.getName());
    List<Method> methodList =
        Arrays.stream(methods)
            .filter(m -> m.getName().equals(methodName))
            .collect(Collectors.toList());

    if (methodList.isEmpty()) {
      return;
    }

    Method methodFound = null;
    for (Method method : methodList) {
      try {
        method.invoke(source, property.getObjectValue());
        methodFound = method;
        break;
      } catch (IllegalAccessException | InvocationTargetException e) {
        // Nothing
      }
    }

    if (methodFound == null) {
      throw new IllegalStateException(
          "No Mapping property found for : " + property + " on " + this.getName());
    }
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
    return LoggerFactory.getLogger(getExecutor().getClass().getName() + "." + getName());
  }

  protected Sampler getCurrentSampler() {
    return getJMeterContext().getCurrentSampler();
  }

  protected SampleResult getPreviousResult() {
    return getJMeterContext().getPreviousResult();
  }

  protected Map<String, String> getParameters() {

    Map<String, String> result = new HashMap<>();
    JMeterProperty property = getProperty("parameters");
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
}
