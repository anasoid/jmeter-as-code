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

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicParentTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;

/**
 * Wrapper for AbstractTestElement.
 *
 * @see AbstractTestElement
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractTestElementWrapper<T extends AbstractTestElement>
    extends AbstractBasicParentTestElementWrapper<T> {

  /** Name. */
  @XStreamAsAttribute
  @XStreamAlias("testname")
  @Getter @Setter
  private String name;

  /** Comments. */
  @JmcProperty(TestElement.COMMENTS)
  @Getter @Setter
  @Builder.Default
  @JmcSkipDefault("")
  private String comment = "";

  /** enabled. */
  @XStreamAsAttribute @Builder.Default @Getter @Setter private boolean enabled = true;


}
