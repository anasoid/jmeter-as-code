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
 * Date :   20-Jan-2021
 */

package org.anasoid.jmc.core.xstream.exceptions;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/** Conversion exception. */
public class ConversionException extends RuntimeException {

  static final long serialVersionUID = -7034897186545466939L;
  private transient Object node;
  private transient AccessibleObject field;

  public ConversionException(String message) {
    super(message);
  }

  public ConversionException(Throwable cause) {
    super(cause);
  }

  public ConversionException(Throwable cause, Object node) {
    super(cause);
    setNode(node);
  }

  public ConversionException(String message, Throwable cause) {
    super(message, cause);
  }

  public ConversionException(String message, Throwable cause, Object node) {
    super(message, cause);
    setNode(node);
  }

  protected final void setNode(Object node) {
    this.node = node;
  }

  public Object getNode() {
    return node;
  }

  public AccessibleObject getField() {
    return field;
  }

  protected void setField(AccessibleObject field) {
    this.field = field;
  }

  @Override
  public String toString() {
    String s = getClass().getName();
    String message = getLocalizedMessage();
    String tmp = (message != null) ? (s + ": " + message) : s;
    String fieldName = "";
    if (field instanceof Field) {
      fieldName = ((Field) field).getName();
    } else if (field instanceof Method) {
      fieldName = ((Method) field).getName();
    }
    if (node == null) {
      return tmp;
    } else {
      return tmp + "(" + fieldName + ")-> " + node;
    }
  }
}
