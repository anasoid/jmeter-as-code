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
 * Date :   07-Jul-2021
 */

package org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.control.sampler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kg.apc.jmeter.control.sampler.SetVariablesAction;
import kg.apc.jmeter.control.sampler.SetVariablesActionGui;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;

/**
 * Wrapper for SetVariablesAction.
 *
 * @see SetVariablesAction
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("jp@gc - Set Variables Action")
public class SetVariablesActionWrapper
    extends AbstractSamplerWrapper<SetVariablesAction, SetVariablesActionGui> {

  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "SetVariablesAction",
      testname = "User Defined Variables",
      elementType = Arguments.class,
      guiclass = ArgumentsPanel.class,
      testclass = Arguments.class)
  @Builder.Default
  @Getter
  @JmcEmptyAllowed
  private final List<ArgumentWrapper> arguments = new ArrayList<>();

  @Override
  public Class<?> getGuiClass() {
    return SetVariablesActionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return SetVariablesAction.class;
  }

  /** Builder. */
  public abstract static class SetVariablesActionWrapperBuilder<
          C extends SetVariablesActionWrapper,
          B extends SetVariablesActionWrapper.SetVariablesActionWrapperBuilder<C, B>>
      extends AbstractSamplerWrapper.AbstractSamplerWrapperBuilder<
          SetVariablesAction, SetVariablesActionGui, C, B> {

    /** Hide Lombok function. */
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
  }
}
