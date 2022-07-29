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
 * Date :   06-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.testelement.property;

import org.anasoid.jmc.core.wrapper.jmeter.JmeterConstants.JmeterProperty;

/** Enumeration representing Jmeter property types. */
public enum JMeterProperty {
  INTEGER(JmeterProperty.INTEGER_PROP),

  STRING(JmeterProperty.STRING_PROP),

  BOOL(JmeterProperty.BOOL_PROP),

  LONG(JmeterProperty.LONG_PROP),

  FLOAT(JmeterProperty.FLOAT_PROP),

  DOUBLE(JmeterProperty.DOUBLE_PROP),

  ELEMENT(JmeterProperty.ELEMENT_PROP),

  COLLECTION(JmeterProperty.COLLECTION_PROP);

  private final String value;

  JMeterProperty(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
