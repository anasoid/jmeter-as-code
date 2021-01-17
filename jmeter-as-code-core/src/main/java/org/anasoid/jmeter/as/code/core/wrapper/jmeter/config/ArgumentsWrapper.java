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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;

@SuperBuilder(setterPrefix = "with")
public class ArgumentsWrapper extends AbstractTestElementWrapper<Arguments>
    implements JMeterGUIWrapper<ArgumentsPanel> {

  @Getter
  @Builder.Default
  @JmcCollection(Arguments.ARGUMENTS)
  private List<ArgumentWrapper> arguments = new ArrayList<>();

  @Override
  public Class<Arguments> getTestClass() {
    return Arguments.class;
  }

  @Override
  public Class<ArgumentsPanel> getGuiClass() {
    return ArgumentsPanel.class;
  }

  public abstract static class ArgumentsWrapperBuilder<
          C extends ArgumentsWrapper, B extends ArgumentsWrapper.ArgumentsWrapperBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<Arguments, C, B> {

    protected B withArguments(ArgumentWrapper child) {
      return super.addChild(child);
    }

    public B addArguments(Collection<ArgumentWrapper> arguments) {
      if (!this.arguments$set) {
        this.arguments$value = new ArrayList<>();
      }
      this.arguments$value.addAll(arguments);
      this.arguments$set = true;

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
