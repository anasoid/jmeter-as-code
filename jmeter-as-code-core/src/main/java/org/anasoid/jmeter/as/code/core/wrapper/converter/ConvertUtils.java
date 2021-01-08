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
 * Date :   05-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.AutoConvert;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.Mandatory;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConvertUtils {
  private static final Logger LOG = LoggerFactory.getLogger(ConvertUtils.class);

  private static final PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
  /**
   * Copy property values from the origin bean to the destination bean for all cases where the
   * property names are the same.
   *
   * <p>For more details see <code>BeanUtilsBean</code>.
   *
   * @param dest Destination bean whose properties are modified
   * @param source Origin bean whose properties are retrieved
   * @throws IllegalAccessException if the caller does not have access to the property accessor
   *     method
   * @throws IllegalArgumentException if the <code>dest</code> or <code>orig</code> argument is null
   *     or if the <code>dest</code> property type is different from the source type and the
   *     relevant converter has not been registered.
   * @throws InvocationTargetException if the property accessor method throws an exception
   * @see BeanUtilsBean#copyProperties
   */
  public static <D extends AbstractTestElement, S extends AbstractTestElementWrapper>
      void copyProperties(final D dest, final S source)
          throws IllegalAccessException, InvocationTargetException, NoSuchMethodException,
              NoSuchFieldException {

    // Validate existence of the specified beans
    if (dest == null) {
      throw new IllegalArgumentException("No destination bean specified");
    }
    if (source == null) {
      throw new IllegalArgumentException("No origin bean specified");
    }

    LOG.debug("ConvertUtils.copyProperties({}, {})", dest, source);

    // Copy the properties, converting as necessary
    final PropertyDescriptor[] origDescriptors = getPropertyUtils().getPropertyDescriptors(source);
    for (PropertyDescriptor origDescriptor : origDescriptors) {
      copyProperty(origDescriptor, dest, source);
    }
  }

  private static <D extends AbstractTestElement, S extends AbstractTestElementWrapper>
      void copyProperty(PropertyDescriptor origDescriptor, D dest, S source)
          throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

    final String name = origDescriptor.getName();
    if (!autoConvert(source, name)) {
      return;
    }
    if ("class".equals(name) || "guiClass".equals(name) || "testClass".equals(name)) {
      return;
    }

    Method readMethod = getPropertyUtils().getReadMethod(origDescriptor);
    Object value = readMethod.invoke(source);
    if (value != null) {
      Method writeMethod = getWriteMethod(dest, name, readMethod.getReturnType());
      writeMethod.invoke(dest, value);
    } else {
      if (isMandatory(source, name)) {
        throw new IllegalStateException(
            MessageFormat.format("Mandatory field ({0}) is not filled on : {1}", name, source));
      }
    }
  }

  private static <S extends AbstractTestElementWrapper> boolean autoConvert(
      S source, String field) {

    Class clazz = source.getClass();
    while (clazz != Object.class) {
      try {
        Field dField = clazz.getDeclaredField(field);
        AutoConvert autoConvertField = dField.getAnnotation(AutoConvert.class);
        if (autoConvertField != null) {
          return autoConvertField.value();
        }
        AutoConvert autoConvertClazz = (AutoConvert) clazz.getDeclaredAnnotation(AutoConvert.class);
        if (autoConvertClazz != null) {
          return autoConvertClazz.value();
        }
        clazz = clazz.getSuperclass();
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      }
    }
    return true;
  }

  private static <S extends AbstractTestElementWrapper> boolean isMandatory(
      S source, String field) {

    Mandatory mandatory = null;
    // check field
    Class clazz = source.getClass();
    while (clazz != Object.class && mandatory == null) {
      try {
        mandatory = clazz.getDeclaredField(field).getAnnotation(Mandatory.class);
        break;
      } catch (NoSuchFieldException e) {
        clazz = clazz.getSuperclass();
      }
    }
    // Check class
    clazz = source.getClass();
    while (clazz != Object.class) {

      Mandatory mandatoryClazz = (Mandatory) clazz.getDeclaredAnnotation(Mandatory.class);
      if (mandatoryClazz != null && Arrays.asList(mandatoryClazz.fields()).contains(field)) {
        return true;
      } else {
        clazz = clazz.getSuperclass();
      }
    }
    return mandatory != null;
  }

  private static <D extends AbstractTestElement> Method getWriteMethod(
      D dest, String name, Class<?> clazz) throws NoSuchMethodException {
    String start = name.substring(0, 1).toUpperCase(Locale.ROOT);
    String methodName = "set" + start + name.substring(1);
    try {
      return dest.getClass().getMethod(methodName, clazz);
    } catch (NoSuchMethodException e) {
      Class<?> primitiveClazz = getPrimitive(clazz);
      if (primitiveClazz != null) {
        return dest.getClass().getMethod(methodName, primitiveClazz);
      } else {
        throw e;
      }
    }
  }

  private static Class<?> getPrimitive(Class<?> clazz) throws NoSuchMethodException {
    Class<?> result = null;
    if (clazz.equals(Integer.class)) {
      result = int.class;
    } else if (clazz.equals(Long.class)) {
      result = long.class;
    } else if (clazz.equals(Float.class)) {
      result = float.class;
    } else if (clazz.equals(Double.class)) {
      result = double.class;
    } else if (clazz.equals(Boolean.class)) {
      result = boolean.class;
    }
    return result;
  }

  public static PropertyUtilsBean getPropertyUtils() {
    return propertyUtilsBean;
  }
}
