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
 * Date :   15-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.xstream;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionMandatoryException;

/** Convert wrapper to Jmeter CLasses utils. */
@SuppressWarnings("PMD.GodClass")
public final class ConverterBeanUtils {

  private ConverterBeanUtils() {}

  /**
   * get All Method to be considered as field an will be included in XML , duplicate method on class
   * and super class will get the method on class. if method with the same name on sub class is not
   * considered as field method on super -class will be hidden.
   *
   * @param source object to be parsed.
   * @return list of methods.
   */
  public static List<Method> getMethods(Object source) {
    Map<String, Method> result = new HashMap<>(); // NOPMD
    Class<?> clazz = source.getClass();
    while (clazz != Object.class) {
      List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
      for (Method method : methods) {
        JmcMethodAlias jmcMethodAlias = method.getAnnotation(JmcMethodAlias.class);
        JmcProperty jmcProperty = method.getAnnotation(JmcProperty.class);
        if (((jmcMethodAlias != null) || (jmcProperty != null))
            && !result.containsKey(method.getName())) {
          if (method.getParameters().length > 0) {
            throw new ConversionException(
                "Invalid JmcMethodAlias on Method with Parameter : " + method);
          }
          result.put(method.getName(), method);
        }
      }
      clazz = clazz.getSuperclass();
    }
    return new ArrayList<>(result.values());
  }

  /**
   * get All field on object, duplicate field on class and super class will get the field on class.
   *
   * @param source object to be parsed.
   * @return list all field.
   */
  public static List<Field> getFields(Object source) {
    Map<String, Field> result = new HashMap<>(); // NOPMD
    Class<?> clazz = source.getClass();
    while (clazz != Object.class) {
      List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
      for (Field field : fields) {
        if (!result.containsKey(field.getName())) {
          result.put(field.getName(), field);
        }
      }
      clazz = clazz.getSuperclass();
    }
    return new ArrayList<>(result.values());
  }

  /** is field/method will be converted as property. */
  public static boolean isProperty(AccessibleObject field) {

    JmcProperty annotation = field.getAnnotation(JmcProperty.class);

    return (annotation != null);
  }

  /** is field/method will be converted as collection. */
  public static boolean isCollection(AccessibleObject field) {

    JmcCollection annotation = field.getAnnotation(JmcCollection.class);

    return (annotation != null);
  }

  /** get value of field or Method. */
  public static Object getValue(AccessibleObject field, Object source) {

    field.setAccessible(true); // NOSONAR
    try {
      if (field instanceof Field) {

        return ((Field) field).get(source);

      } else if (field instanceof Method) {

        return ((Method) field).invoke(source);
      }

    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ConversionException(e);
    }
    throw new ConversionException("Unknown type: " + field);
  }

  /** get Alias of field or Method. */
  public static String getAlias(AccessibleObject field) {

    XStreamAlias annotationXstream = field.getAnnotation(XStreamAlias.class);

    if (annotationXstream != null) {
      return annotationXstream.value();
    }
    JmcMethodAlias annotationJmc = field.getAnnotation(JmcMethodAlias.class);
    if (annotationJmc != null) {
      return annotationJmc.value();
    }
    if (field instanceof Field) {
      return ((Field) field).getName();
    }
    return null;
  }

  /** get Property Alias (intProp,stringProp,longProp .. ). */
  public static String getPropertyAlias(Object value, Class<?> clazz) {
    Class<?> ppClazz = (clazz == Void.class) ? value.getClass() : clazz;
    if ((value != null) && (value.getClass().isEnum())) {
      return getPropertyAlias(getEnumValue(value), clazz);
    }
    if (ppClazz == Integer.class) {
      return JMeterProperty.INTEGER.value();
    } else if (ppClazz == String.class) {
      return JMeterProperty.STRING.value();
    } else if (ppClazz == Long.class) {
      return JMeterProperty.LONG.value();
    } else if (ppClazz == Boolean.class) {
      return JMeterProperty.BOOL.value();

    } else if (ppClazz == Float.class) {
      return JMeterProperty.FLOAT.value();

    } else if (ppClazz == Double.class) {
      return JMeterProperty.DOUBLE.value();

    } else if (value instanceof AbstractTestElementWrapper) {
      return JMeterProperty.ELEMENT.value();
    }
    throw new IllegalStateException("Unknowen properties type for :" + value);
  }

  /** get num value. call value() method if present, else call toString. */
  public static Object getEnumValue(Object object) {

    try {
      return object.getClass().getMethod("value").invoke(object);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      return object.toString();
    }
  }

  /** Should skip field from XML conversion. */
  public static boolean shouldSkip(Object source, AccessibleObject field) {

    if (field.getAnnotation(XStreamOmitField.class) != null) {
      return true;
    }
    Object value = getValue(field, source);
    if (value == null) {
      if (field.getAnnotation(JmcNullAllowed.class) != null) {
        return false;
      }
      if (field.getAnnotation(JmcMandatory.class) != null) {
        throw new ConversionMandatoryException(source, field);
      }
      return true;
    }
    JmcSkipDefault jmcSkipDefault = field.getAnnotation(JmcSkipDefault.class);
    if ((jmcSkipDefault != null) && (jmcSkipDefault.value().equals(value.toString()))) {
      return true;
    }

    return (value instanceof Collection
        && ((Collection<?>) value).isEmpty()
        && field.getAnnotation(JmcEmptyAllowed.class) == null);
  }

  /** filter Fields to be converted as attribute on XML. */
  public static List<AccessibleObject> getAttributeOnly(List<AccessibleObject> accessibleObjects) {
    List<AccessibleObject> result = new ArrayList<>();
    for (AccessibleObject accessibleObject : accessibleObjects) {
      if (accessibleObject.getAnnotation(XStreamAsAttribute.class) != null
          || accessibleObject.getAnnotation(JmcAsAttribute.class) != null) {
        result.add(accessibleObject);
      }
    }
    return result;
  }
}
