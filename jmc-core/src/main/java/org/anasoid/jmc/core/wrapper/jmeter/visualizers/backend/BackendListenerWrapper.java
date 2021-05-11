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
 * Date :   07-May-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.visualizers.backend;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SampleListenerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.visualizers.backend.BackendListener;
import org.apache.jmeter.visualizers.backend.BackendListenerGui;

/**
 * Wrapper for BackendListener.
 *
 * @see BackendListener
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Backend Listener")
public class BackendListenerWrapper extends AbstractBasicChildTestElementWrapper<BackendListener>
    implements JMeterGUIWrapper<BackendListenerGui>,
        SampleListenerWrapper<BackendListener>,
        Validator {

  @XStreamOmitField @Getter private final String className;
  @XStreamOmitField @Getter private final BackendListenerClient implementation;
  @XStreamOmitField @Default @Getter private final Map<String, String> arguments = new HashMap<>();

  @JmcProperty("QUEUE_SIZE")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("5000")
  private String queueSize = "5000";

  @JmcProperty("classname")
  @JmcMandatory
  protected String classNameProp() {
    if (className != null) {
      return className;
    }
    return implementation.value();
  }

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "arguments",
      elementType = Arguments.class,
      guiclass = ArgumentsPanel.class,
      testclass = Arguments.class)
  @JmcEmptyAllowed
  protected List<ArgumentWrapper> argumentsProp() {
    Map<String, String> argumentsMap = new HashMap<>();
    if (implementation != null) {
      argumentsMap.putAll(implementation.defaultValue());
    }
    argumentsMap.putAll(getArguments());

    return argumentsMap.entrySet().stream()
        .map(c -> ArgumentWrapper.builder().withName(c.getKey()).withValue(c.getValue()).build())
        .collect(Collectors.toList());
  }

  @Override
  public Class<?> getGuiClass() {
    return BackendListenerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return BackendListener.class;
  }

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (implementation == null && className == null) {
      throw new ConversionIllegalStateException("implementation is empty");
    }
  }

  /** Builder. */
  public abstract static class BackendListenerWrapperBuilder<
          C extends BackendListenerWrapper, B extends BackendListenerWrapperBuilder<C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          BackendListener, C, B> {

    /** Queue Size. */
    public B withQueueSize(String queueSize) {
      this.queueSize$value = queueSize;
      this.queueSize$set = true;
      return self();
    }

    /** Queue Size. */
    public B withQueueSize(Variable variable) {
      return withQueueSize(variable.getValue());
    }

    /** Queue Size. */
    public B withQueueSize(Integer queueSize) {

      return withQueueSize(String.valueOf(queueSize));
    }

    /** Chose client Implementation. */
    @SuppressWarnings("PMD.NullAssignment")
    public B withImplementation(BackendListenerClient implementation) {
      this.implementation = implementation;
      this.className = null;
      return self();
    }

    /** Chose client Implementation. */
    public B withImplementation(String className) {
      return withClassName(className);
    }

    @SuppressWarnings("PMD.NullAssignment")
    protected B withClassName(String className) {
      this.className = className;
      this.implementation = null;
      return self();
    }

    protected B withArguments(Map<String, String> arguments) {
      this.arguments$value = arguments;
      return self();
    }

    /**
     * Add arguments. Each argument consists of a key/value pair.
     *
     * @param arguments List of arguments.
     */
    public B addArguments(Map<String, String> arguments) {
      if (!this.arguments$set) {
        withArguments(new HashMap<>());
      }
      this.arguments$value.putAll(arguments);
      this.arguments$set = true;

      return self();
    }

    /**
     * Add argument, consists of a key/value pair.
     *
     * @param name name.
     * @param value value.
     */
    public B addArgument(String name, String value) {
      addArguments(Map.of(name, value));
      return self();
    }
  }
}
