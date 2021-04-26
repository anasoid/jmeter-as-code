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

package org.anasoid.jmeter.as.code.core.xstream.exceptions;

import java.lang.reflect.AccessibleObject;

/** Error when Mandatory field. */
public class ConversionMandatoryException extends ConversionException {

  static final long serialVersionUID = -703454586545466939L;

  public ConversionMandatoryException(Object source, AccessibleObject field) {
    this(source, field.toString());
  }

  public ConversionMandatoryException(Object source, String fieldName) {
    super("Field (" + fieldName + ") is mandatory on " + source.toString());
  }
}
