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

package org.anasoid.jmeter.as.code.core.xstream; // NOPMD

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcInherited;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionMandatoryException;
import org.anasoid.jmeter.as.code.core.xstream.types.TypeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Convert wrapper to Jmeter CLasses utils. */
@SuppressWarnings({"PMD.GodClass", "PMD.ExcessiveImports"})
public final class ConverterBeanUtils {

  private static final Logger LOG = LoggerFactory.getLogger(ConverterBeanUtils.class);

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
    Class<?> item = source.getClass();
    for (Class<?> clazz : getSuperClasses(item)) {
      for (Method method : clazz.getDeclaredMethods()) {
        JmcMethodAlias jmcMethodAlias = getAnnotation(method, JmcMethodAlias.class);
        JmcProperty jmcProperty = getAnnotation(method, JmcProperty.class);
        JmcInherited jmcInherited = method.getAnnotation(JmcInherited.class);
        JmcCollection jmcCollection = method.getAnnotation(JmcCollection.class);
        if (((jmcMethodAlias != null)
                || (jmcProperty != null)
                || (jmcInherited != null)
                || (jmcCollection != null))
            && !result.containsKey(method.getName())) {
          if (method.getParameters().length > 0) {
            throw new ConversionException(
                "Invalid JmcMethodAlias on Method with Parameter : " + method);
          }
          result.put(method.getName(), method);
        }
      }
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
    for (Class<?> sclazz : getSuperClasses(clazz)) {
      for (Field field : sclazz.getDeclaredFields()) {
        if (!result.containsKey(field.getName())) {
          result.put(field.getName(), field);
        }
      }
    }
    return new ArrayList<>(result.values());
  }

  /** is field/method will be converted as property. */
  public static boolean isProperty(AccessibleObject accessibleObject) {

    JmcProperty annotation = getAnnotation(accessibleObject, JmcProperty.class);

    return (annotation != null);
  }

  /** is field/method will be converted as property. */
  @SuppressWarnings({"PMD.EmptyCatchBlock", "PMD.AvoidDeeplyNestedIfStmts"})
  public static <T extends Annotation> T getAnnotation(
      AccessibleObject accessibleObject, Class<T> annotation) {

    if (accessibleObject instanceof Method) {
      Method method = (Method) accessibleObject;
      JmcInherited jmcInherited = accessibleObject.getAnnotation(JmcInherited.class);
      if (jmcInherited != null) {
        boolean first = true;
        for (Class<?> clazz : getSuperClasses(method.getDeclaringClass())) {
          if (!first) {
            try {
              Method superMethod = clazz.getMethod(method.getName());
              return superMethod.getAnnotation(annotation);
            } catch (NoSuchMethodException e) {
              // NEXT
            }
          }
          first = false;
          Class<?>[] interfaces = clazz.getInterfaces();
          for (Class<?> interf : interfaces) {

            try {
              Method superMethod = interf.getMethod(method.getName());
              return superMethod.getAnnotation(annotation);
            } catch (NoSuchMethodException noSuchMethodException) {
              // NEXT
            }
          }
        }
      }
    }
    return accessibleObject.getAnnotation(annotation);
  }

  /**
   * get super classes of class, include it self. Object is not included.
   *
   * @param clazz class input.
   * @return list of super classes
   */
  public static List<Class<?>> getSuperClasses(Class<?> clazz) {
    List<Class<?>> result = new ArrayList<>();
    Class<?> item = clazz;
    while (item != Object.class) {
      result.add(item);
      item = item.getSuperclass();
    }
    return result;
  }

  /** is field/method will be converted as collection. */
  public static boolean isCollection(AccessibleObject field) {

    JmcCollection annotation = getAnnotation(field, JmcCollection.class);

    return (annotation != null);
  }

  /** get value of field or Method. */
  public static Object getValue(AccessibleObject field, Object source) {

    field.setAccessible(true); // NOSONAR
    try { // NOSONAR
      if (field instanceof Field) {

        String fieldName = ((Field) field).getName();
        String getMethodName =
            "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);

        try { // NOSONAR
          Method getMethod = source.getClass().getMethod(getMethodName);
          return getMethod.invoke(source);
        } catch (NoSuchMethodException e) {
          if (Boolean.class.equals(((Field) field).getType())
              || boolean.class.equals(((Field) field).getType())) {
            getMethodName =
                "is" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
            try { // NOSONAR
              Method getMethod = source.getClass().getMethod(getMethodName);
              return getMethod.invoke(source);
            } catch (NoSuchMethodException ex) {
              LOG.warn("Getter not found for field {} on {}", ((Field) field).getName(), source);
              return ((Field) field).get(source);
            }
          }
          LOG.warn("Getter not found for field {} on {}", ((Field) field).getName(), source);
          return ((Field) field).get(source);
        }

      } else if (field instanceof Method) {

        return ((Method) field).invoke(source);
      }

    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new ConversionException(e);
    }
    throw new ConversionException("Unknown type: " + field);
  }

  /** get Alias of field or Method. */
  public static String getAlias(AccessibleObject accessibleObject) {

    XStreamAlias annotationXstream = getAnnotation(accessibleObject, XStreamAlias.class);

    if (annotationXstream != null) {
      return annotationXstream.value();
    }
    JmcMethodAlias annotationJmc = getAnnotation(accessibleObject, JmcMethodAlias.class);
    if (annotationJmc != null) {
      return annotationJmc.value();
    }
    if (accessibleObject instanceof Field) {
      return ((Field) accessibleObject).getName();
    }
    return null;
  }

  /** get Property class type. */
  public static Class<?> getPropertyType(AccessibleObject accessibleObject) {

    JmcProperty jmcProperty = getAnnotation(accessibleObject, JmcProperty.class);

    if (jmcProperty.type() != Void.class) {
      return jmcProperty.type();
    }
    if (accessibleObject instanceof Field) {
      return ((Field) accessibleObject).getType();
    }
    if (accessibleObject instanceof Method) {
      return ((Method) accessibleObject).getReturnType();
    }
    throw new IllegalStateException("Unknown accessibleObject type :" + accessibleObject);
  }

  /** get Property Alias (intProp,stringProp,longProp .. ). */
  @SuppressWarnings("PMD.NPathComplexity")
  public static String getPropertyAlias(Object value, Class<?> clazz) {
    Class<?> ppClazz = (clazz == Void.class && value != null) ? value.getClass() : clazz;
    if ((value != null) && (value.getClass().isEnum())) {
      Object enumValue = getEnumValue(value);
      return getPropertyAlias(
          enumValue, (clazz == Void.class || clazz.isEnum()) ? enumValue.getClass() : clazz);
    }
    if (TypeManager.class.isAssignableFrom(clazz)) {

      try {
        return getPropertyAlias(
            value,
            ((TypeManager) clazz.getDeclaredConstructor().newInstance()).getType((String) value));
      } catch (InstantiationException
          | NoSuchMethodException
          | IllegalAccessException
          | InvocationTargetException e) {
        throw new ConversionException(e);
      }
    }

    if (ppClazz == Integer.class) {
      return JMeterProperty.INTEGER.value();
    } else if (ppClazz == String.class) {
      return JMeterProperty.STRING.value();
    } else if (ppClazz == Long.class) {
      return JMeterProperty.LONG.value();
    } else if (ppClazz == Boolean.class || ppClazz == boolean.class) {
      return JMeterProperty.BOOL.value();

    } else if (ppClazz == Float.class) {
      return JMeterProperty.FLOAT.value();

    } else if (ppClazz == Double.class) {
      return JMeterProperty.DOUBLE.value();

    } else if (ppClazz == Variable.class) {
      return JMeterProperty.STRING.value();

    } else if (ppClazz.isEnum()) {
      return JMeterProperty.STRING.value();

    } else if (value instanceof TestElementWrapper) {
      return JMeterProperty.ELEMENT.value();
    }
    throw new IllegalStateException("Unknown properties type for :" + value);
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
  public static boolean shouldSkip(Object source, AccessibleObject accessibleObject) {

    if (getAnnotation(accessibleObject, XStreamOmitField.class) != null) {
      return true;
    }
    Object value = getValue(accessibleObject, source);
    if (value == null) {
      if (getAnnotation(accessibleObject, JmcNullAllowed.class) != null) {
        return false;
      }
      if (getAnnotation(accessibleObject, JmcMandatory.class) != null) {
        throw new ConversionMandatoryException(source, accessibleObject);
      }
      return true;
    }
    JmcSkipDefault jmcSkipDefault = getAnnotation(accessibleObject, JmcSkipDefault.class);
    if ((jmcSkipDefault != null) && (jmcSkipDefault.value().equals(value.toString()))) {
      return true;
    }

    return (value instanceof Collection
        && ((Collection<?>) value).isEmpty()
        && getAnnotation(accessibleObject, JmcEmptyAllowed.class) == null);
  }

  /** filter Fields to be converted as attribute on XML. */
  public static List<AccessibleObject> getAttributeOnly(List<AccessibleObject> accessibleObjects) {
    List<AccessibleObject> result = new ArrayList<>();
    for (AccessibleObject accessibleObject : accessibleObjects) {
      if (isAttribute(accessibleObject)) {
        result.add(accessibleObject);
      }
    }
    return result;
  }

  /** is field/method will be converted as attribute. */
  public static boolean isAttribute(AccessibleObject accessibleObject) {

    return (getAnnotation(accessibleObject, XStreamAsAttribute.class) != null
        || getAnnotation(accessibleObject, JmcAsAttribute.class) != null);
  }
}
