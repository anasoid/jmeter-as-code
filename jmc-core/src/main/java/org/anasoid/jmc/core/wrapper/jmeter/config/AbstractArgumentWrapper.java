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

package org.anasoid.jmc.core.wrapper.jmeter.config;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.config.Argument;

/**
 * Wrapper for Argument.
 *
 * @see Argument
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractArgumentWrapper<T extends Argument>
    extends AbstractBasicTestElementWrapper<T> {

  @XStreamAsAttribute
  @XStreamAlias("name")
  @Getter
  private String name;

  @JmcProperty(Argument.VALUE)
  @JmcNullAllowed
  private @Getter String value;

  @JmcProperty(Argument.METADATA)
  @Default
  private @Getter String metadata = "=";

  @JmcProperty(Argument.DESCRIPTION)
  private @Getter String description;

  @JmcProperty(Argument.ARG_NAME)
  @JmcSkipDefault("")
  public String getNameProperty() {
    return name;
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }
}
