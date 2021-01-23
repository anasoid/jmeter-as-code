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

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcElement;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.config.Argument;

@SuperBuilder(setterPrefix = "with")
@JmcElement
public abstract class AbstractArgumentWrapper<T extends Argument>
    extends AbstractTestElementWrapper<T> {

  @XStreamAsAttribute
  @XStreamAlias("name")
  @Getter
  private String name;

  @XStreamOmitField @Builder.Default @Getter private boolean enabled = true;

  @JmcProperty(Argument.VALUE)
  private @Getter String value;

  @JmcProperty(Argument.METADATA)
  @Default
  private @Getter String metadata = "=";

  @JmcProperty(Argument.DESCRIPTION)
  private @Getter String description;

  @JmcProperty(Argument.ARG_NAME)
  public String getNameStr() {
    return name;
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }
}
