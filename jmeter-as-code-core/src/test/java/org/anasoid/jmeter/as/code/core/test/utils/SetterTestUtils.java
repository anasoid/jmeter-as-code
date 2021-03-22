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

package org.anasoid.jmeter.as.code.core.test.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.ConverterBeanUtils;
import org.junit.jupiter.api.Assertions;

/** Utils to test @setter to have also coverage on Setter. */
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
            Method method = getSetterMethod(source, field);
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

  /** test all setter. */
  public static void testSetter(TestElementWrapper testElement)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

    List<Field> fields = SetterTestUtils.getSetterFields(testElement);
    Random rnd = new Random(999999);
    for (Field field : fields) {
      Method method = getSetterMethod(testElement, field);
      Object value;

      if (field.getType() == Integer.class) {
        value = rnd.nextInt();
        method.invoke(testElement, value);
      } else if (field.getType() == Long.class) {
        value = rnd.nextLong();
        method.invoke(testElement, value);
      } else if (field.getType() == Float.class) {
        value = rnd.nextFloat();
        method.invoke(testElement, value);
      } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
        value = rnd.nextBoolean();
        method.invoke(testElement, value);
      } else if (field.getType() == String.class) {
        byte[] array = new byte[7];
        rnd.nextBytes(array);
        value = new String(array, Charset.forName("UTF-8"));
        method.invoke(testElement, value);
      } else if (field.getType().isEnum()) {
        byte[] array = new byte[7];
        rnd.nextBytes(array);
        value = field.getType().getEnumConstants()[0];
        method.invoke(testElement, value);
      } else {

        throw new AssertionError("Type not found for :" + field);
      }
      field.setAccessible(true);
      Assertions.assertEquals(value, field.get(testElement));
    }
  }
}
