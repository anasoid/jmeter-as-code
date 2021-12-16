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
 * Date :   22-Mar-2021
 */

package org.anasoid.jmc.test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper.AbstractBasicTestElementWrapperBuilder;
import org.anasoid.jmc.core.xstream.ConverterBeanUtils;
import org.junit.jupiter.api.Assertions;

/** Utils to test @setter to have also coverage on Setter. */
@SuppressWarnings({
  "PMD.JUnit4TestShouldUseTestAnnotation",
  "PMD.EmptyCatchBlock",
  "PMD.AvoidInstantiatingObjectsInLoops"
})
public final class SetterTestUtils {

  private SetterTestUtils() {}

  /**
   * get All field with setter annotation.
   *
   * @param source object to be parsed.
   * @return list all field.
   */
  private static List<Field> getSetterFields(Object source) {
    Map<String, Field> result = new HashMap<>(); // NOPMD
    Class<?> clazz = source.getClass();
    for (Class<?> sclazz : ConverterBeanUtils.getSuperClasses(clazz)) {
      for (Field field : sclazz.getDeclaredFields()) {
        if (!result.containsKey(field.getName())) {

          try {
            getSetterMethod(source, field);
          } catch (NoSuchMethodException e) {
            continue;
          }

          result.put(field.getName(), field);
        }
      }
    }
    return new ArrayList<>(result.values());
  }

  /**
   * get All field with setter annotation.
   *
   * @param source object to be parsed.
   * @return list all field.
   */
  private static Method toBuildMethod(Object source) {
    Class<?> clazz = source.getClass();
    for (Class<?> sclazz : ConverterBeanUtils.getSuperClasses(clazz)) {
      try {
        return sclazz.getDeclaredMethod("toBuilder");
      } catch (NoSuchMethodException e) {
        // Nothing
      }
    }
    return null;
  }

  /**
   * get All field with setter annotation.
   *
   * @param source object to be parsed.
   * @return list all field.
   */
  private static List<Field> getGetterFields(Object source) {
    Map<String, Field> result = new HashMap<>(); // NOPMD
    Class<?> clazz = source.getClass();
    for (Class<?> sclazz : ConverterBeanUtils.getSuperClasses(clazz)) {
      for (Field field : sclazz.getDeclaredFields()) {
        if (!result.containsKey(field.getName())) {

          try {
            getGetterMethod(source, field);
          } catch (NoSuchMethodException e) {
            continue;
          }

          result.put(field.getName(), field);
        }
      }
    }
    return new ArrayList<>(result.values());
  }

  /**
   * get All field with setter annotation.
   *
   * @return list all field.
   */
  private static Method getSetterMethod(Object source, Field field) throws NoSuchMethodException {
    String fieldName = field.getName();
    String getMethodName =
        "set" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);

    return source.getClass().getMethod(getMethodName, field.getType());
  }

  /**
   * get All field with Getter annotation.
   *
   * @return list all field.
   */
  private static Method getGetterMethod(Object source, Field field) throws NoSuchMethodException {
    String fieldName = field.getName();
    String getMethodName =
        "get" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);

    return source.getClass().getMethod(getMethodName);
  }

  /** test all setter. */
  public static void testSetter(
      AbstractBasicTestElementWrapperBuilder testElementBuilder, String... ignoreFields)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    testElementBuilder.toString();
    testSetter(testElementBuilder.build(), ignoreFields);
  }

  /** test all setter. */
  @SuppressWarnings({"PMD.CognitiveComplexity", "PMD.AvoidAccessibilityAlteration"})
  private static void testSetter(TestElementWrapper testElement, String... ignoreFields)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    // Coverage toString
    testElement.toString();

    Method toBuilder = SetterTestUtils.toBuildMethod(testElement);
    if (toBuilder != null) {
      toBuilder.invoke(testElement);
    }
    List<Field> fields = SetterTestUtils.getSetterFields(testElement);
    Random rnd = new Random(999999);
    for (Field field : fields) {
      if (Arrays.stream(ignoreFields).anyMatch(c -> c.equalsIgnoreCase(field.getName()))) {
        continue;
      }
      Method method = getSetterMethod(testElement, field);
      Object value;

      if (field.getType() == Integer.class || field.getType() == int.class) {
        value = rnd.nextInt();
        method.invoke(testElement, value);
      } else if (field.getType() == Long.class || field.getType() == long.class) {
        value = rnd.nextLong();
        method.invoke(testElement, value);
      } else if (field.getType() == Float.class || field.getType() == float.class) {
        value = rnd.nextFloat();
        method.invoke(testElement, value);
      } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
        value = rnd.nextBoolean();
        method.invoke(testElement, value);
      } else if (field.getType() == String.class) {
        byte[] array = new byte[7];
        rnd.nextBytes(array);
        value = new String(array, StandardCharsets.UTF_8);
        method.invoke(testElement, value);
      } else if (field.getType() == Variable.class) {
        byte[] array = new byte[7];
        rnd.nextBytes(array);
        value = new Variable(new String(array, StandardCharsets.UTF_8));
        method.invoke(testElement, value);
      } else if (field.getType().isEnum()) {
        byte[] array = new byte[7];
        rnd.nextBytes(array);
        value = field.getType().getEnumConstants()[0];
        method.invoke(testElement, value);
      } else if (List.class.isAssignableFrom(field.getType())) {
        value = new ArrayList<>();
        method.invoke(testElement, value);
      } else {

        throw new AssertionError("Type not found for :" + field);
      }
      field.setAccessible(true);
      Assertions.assertEquals(value, field.get(testElement));
    }

    testGetter(testElement);
  }

  /** test all Getter. */
  public static void testGetter(TestElementWrapper testElement)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<Field> fields = SetterTestUtils.getGetterFields(testElement);

    for (Field field : fields) {
      Method method = getGetterMethod(testElement, field);

      method.invoke(testElement);
    }
  }
}
