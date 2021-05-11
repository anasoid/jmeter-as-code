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
 * Date :   11-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;

/**
 * Wrapper for Arguments.
 *
 * @see Arguments
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("User Defined Variables")
public class ArgumentsWrapper extends ConfigTestElementWrapper<Arguments, ArgumentsPanel> {

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      elementType = Arguments.class,
      guiclass = ArgumentsPanel.class,
      testclass = Arguments.class)
  @Builder.Default
  @Getter
  @JmcEmptyAllowed
  private final List<ArgumentWrapper> arguments = new ArrayList<>();

  @Override
  public Class<?> getGuiClass() {
    return ArgumentsPanel.class;
  }

  @Override
  public Class<?> getTestClass() {
    return Arguments.class;
  }

  /** Builder. */
  public abstract static class ArgumentsWrapperBuilder<
          C extends ArgumentsWrapper, B extends ArgumentsWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          Arguments, ArgumentsPanel, C, B> {

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
      return addArguments(Arrays.asList(argument));
    }
  }
}
