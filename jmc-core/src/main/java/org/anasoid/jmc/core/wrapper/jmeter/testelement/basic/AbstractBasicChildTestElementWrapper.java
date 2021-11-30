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
 * Date :   27-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementTreeNodeWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;

/**
 * Class represent a node without Children. Comment attribute (name , comment, enabled) are present.
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractBasicChildTestElementWrapper<T extends AbstractTestElement>
    extends AbstractBasicTestElementWrapper<T> implements TestElementTreeNodeWrapper<T> {

  /** Name. */
  @XStreamAsAttribute
  @XStreamAlias("testname")
  @Getter
  @Setter
  private String name;

  /** Comments. */
  @JmcProperty(TestElement.COMMENTS)
  @Getter
  @Setter
  @Builder.Default
  @JmcSkipDefault("")
  private String comment = "";

  /** enabled. */
  @XStreamAsAttribute @Builder.Default @Getter @Setter private boolean enabled = true;

  @Override
  public String toString() {
    return this.getClass().getSimpleName()
        + "{"
        + "name='"
        + name
        + '\''
        + ", comment='"
        + comment
        + '\''
        + '}';
  }
}
