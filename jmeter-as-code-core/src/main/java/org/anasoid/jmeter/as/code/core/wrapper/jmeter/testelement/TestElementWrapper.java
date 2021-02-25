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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement;

import java.util.List;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.apache.jmeter.testelement.TestElement;

/** Main TestElementWrapper Interface. */
public interface TestElementWrapper<T extends TestElement> {

  /** Init wrapper before conversion. */
  void init();

  /** Test Class used by Jmeter TestElement.TEST_CLASS. @See TestElement. */
  Class<T> getTestClass();

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement. */
  @JmcMethodAlias("testclass")
  @JmcAsAttribute
  String getTestClassAsString();

  /**
   * Get tree children.
   *
   * @return list of child.
   */
  List<TestElementWrapper<?>> getChilds();
}
