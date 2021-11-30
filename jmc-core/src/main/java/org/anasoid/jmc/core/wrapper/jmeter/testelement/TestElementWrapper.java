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
 * Date :   22-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.testelement;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import org.anasoid.jmc.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;

/** Main TestElementWrapper Interface. */
public interface TestElementWrapper<T> extends Serializable {

  /** Init wrapper before conversion. */
  void init();

  /** Test Class used by Jmeter TestElement.TEST_CLASS. @See TestElement. */
  Class<?> getTestClass();

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement. */
  @JmcMethodAlias("testclass")
  @JmcAsAttribute
  String getTestClassAsString();

  /**
   * Get tree children.
   *
   * @return list of child.
   */
  List<TestElementWrapper<?>> getChildren();

  /**
   * Get Tags. set of tags to categorize eleemnt, this field are not part of Jmeter and will not be
   * persisted.
   */
  Set<String> getTags();
}
