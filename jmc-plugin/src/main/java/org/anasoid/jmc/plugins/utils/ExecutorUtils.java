package org.anasoid.jmc.plugins.utils;

import com.thoughtworks.xstream.converters.ConversionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElement;
import org.anasoid.jmc.plugins.component.java.JavaTestElementExecutor;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.property.JMeterProperty;
import org.apache.jmeter.testelement.property.PropertyIterator;

/**
 * Executor utils, Helper to get instance of JavaTestElementExecutor from class name.
 *
 * @see JavaTestElementExecutor
 */
public final class ExecutorUtils {

  private static final List<String> IGNORED_ATTRIBUTES =
      Arrays.asList(
          "parameters",
          "TestElement.gui_class",
          "TestElement.test_class",
          "TestElement.name",
          "TestElement.enabled",
          "executorClass",
          "TestPlan.comments");

  private ExecutorUtils() {}

  /**
   * Extract attribute Properties.
   *
   * @param testElement testElement.
   * @return filtered properties , converted to arguments
   */
  public static Arguments extractAttributeArguments(AbstractJavaTestElement<?> testElement) {
    Arguments defaultArgs = new Arguments();

    PropertyIterator propertyIterator = testElement.propertyIterator();
    while (propertyIterator.hasNext()) {
      JMeterProperty property = propertyIterator.next();
      if (!IGNORED_ATTRIBUTES.contains(property.getName())) {
        defaultArgs.addArgument(property.getName(), property.getStringValue());
      }
    }

    return defaultArgs;
  }

  /**
   * get {@link JavaTestElementExecutor} instance.
   *
   * @param className className of {@link JavaTestElementExecutor}
   * @param testElement testElement to initialize value.
   * @return JavaTestElementExecutor instance.
   */
  public static JavaTestElementExecutor getExecutor(String className, TestElement testElement) {
    Map<String, Object> values = new HashMap<>();
    PropertyIterator propertyIterator = testElement.propertyIterator();
    while (propertyIterator.hasNext()) {
      JMeterProperty property = propertyIterator.next();
      values.put(property.getName(), property.getObjectValue());
    }
    return getExecutor(className, values);
  }

  /**
   * get {@link JavaTestElementExecutor} instance.
   *
   * @param className className of {@link JavaTestElementExecutor}
   * @param values fields values.
   * @return JavaTestElementExecutor instance.
   */
  @SuppressWarnings("PMD.AvoidAccessibilityAlteration")
  public static JavaTestElementExecutor getExecutor(String className, Map<String, Object> values) {

    try {

      Class<?> wrapper = Class.forName(className);
      Method builderMethod = wrapper.getDeclaredMethod("builder");
      builderMethod.setAccessible(true); // NOSONAR
      Object builder = builderMethod.invoke(null);
      setFields(builder, values);
      Method buildMethod = builder.getClass().getDeclaredMethod("build");
      buildMethod.setAccessible(true); // NOSONAR
      return (JavaTestElementExecutor) buildMethod.invoke(builder);

    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new ConversionException("Loading executor ", e);
    }
  }

  private static void setFields(Object builder, Map<String, Object> values) {
    Iterator<Entry<String, Object>> iterator = values.entrySet().iterator();
    while (iterator.hasNext()) {
      Entry<String, Object> property = iterator.next();
      setValue(builder, property.getKey(), property.getValue());
    }
  }

  /**
   * get All field with setter annotation.
   *
   * @return list all field.
   */
  @SuppressWarnings("PMD.EmptyCatchBlock")
  private static void setValue(Object source, String name, Object value) {

    Method[] methods = source.getClass().getMethods();
    String methodName = getWithMethod(name);
    List<Method> methodList =
        Arrays.stream(methods)
            .filter(m -> m.getName().equals(methodName))
            .collect(Collectors.toList());

    if (methodList.isEmpty()) {
      return;
    }

    Method methodFound = null;
    for (Method method : methodList) {
      try {
        method.invoke(source, value);
        methodFound = method;
        break;
      } catch (IllegalAccessException | InvocationTargetException e) {
        // Nothing
      }
    }

    if (methodFound == null) {
      throw new IllegalStateException("No Mapping property found for : " + name);
    }
  }

  /**
   * get All field with setter annotation.
   *
   * @return list all field.
   */
  private static String getWithMethod(String fieldName) {
    return "with" + fieldName.substring(0, 1).toUpperCase(Locale.ROOT) + fieldName.substring(1);
  }
}
