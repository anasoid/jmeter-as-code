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
 * Date :   08-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.xstream.converters;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.ReferencingMarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.anasoid.jmeter.as.code.core.wrapper.ScriptWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;

public class TestElementConverter implements Converter {

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

    init(source);
    List<Field> allFields = getFields(source);
    List<Field> attributeField = getAttributeFields(allFields);
    List<Field> nonAttributeField = new ArrayList<>(allFields);
    nonAttributeField.removeAll(attributeField);

    List<Method> methods = getMethods(source);
    List<Method> attributeMethods = getAttributeMethods(methods);
    List<Method> nonAttributeMethods = new ArrayList<>(methods);
    nonAttributeMethods.removeAll(attributeMethods);
    try {

      for (Field field : attributeField) {
        if (!shouldSkip(source, field)) {
          field.setAccessible(true);
          convertAttributeField(field.get(source), getFieldAlias(field), writer, context);
        }
      }
      for (Method method : attributeMethods) {
        method.setAccessible(true);
        Object value = method.invoke(source);
        if (value != null) {
          convertAttributeField(value, getMethodAlias(method), writer, context);
        }
      }
      for (Field field : nonAttributeField) {
        if (!shouldSkip(source, field)) {
          field.setAccessible(true);
          if (isProperty(field)) {
            convertProperty(
                field.get(source), field.getAnnotation(JmcProperty.class).value(), writer, context);
          } else if (isCollection(field)) {
            JmcCollection annotation = field.getAnnotation(JmcCollection.class);
            convertCollection(field.get(source), annotation, writer, context);
          } else {
            convertField(field.get(source), getFieldAlias(field), writer, context);
          }
        }
      }

      for (Method method : nonAttributeMethods) {
        method.setAccessible(true);
        Object value = method.invoke(source);
        if (value != null) {
          if (isProperty(method)) {
            convertProperty(
                value, method.getAnnotation(JmcProperty.class).value(), writer, context);
          } else if (isCollection(method)) {
            JmcCollection annotation = method.getAnnotation(JmcCollection.class);
            convertCollection(value, annotation, writer, context);
          } else {
            convertField(value, getMethodAlias(method), writer, context);
          }
        }
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (InvocationTargetException e) {
      throw new RuntimeException(e);
    }

    appendChild(source, writer, context);
  }

  protected void appendChild(
      Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    if (source instanceof AbstractTestElementWrapper) {
      List<AbstractTestElementWrapper> childs = ((AbstractTestElementWrapper) source).getChilds();
      String currentPath = ((ReferencingMarshallingContext) context).currentPath().toString();
      if (currentPath.contains("/elementProp") || currentPath.contains("/collectionProp")) {
        return;
      }
      writer.endNode();
      writer.startNode("hashTree");
      for (AbstractTestElementWrapper child : childs) {
        writer.startNode(child.getTestClassAsString());
        context.convertAnother(child);
        writer.endNode();
      }
    }
  }

  protected boolean shouldSkip(Object source, Field field) {
    field.setAccessible(true);
    try {

      if (field.getAnnotation(XStreamOmitField.class) != null) {
        return true;
      }
      Object value = field.get(source);
      if (value == null) {
        return true;
      }
      if (value instanceof Collection) {
        if (((Collection) value).isEmpty()) {
          return true;
        }
      }
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    return false;
  }

  protected void init(Object source) {

    if (source instanceof AbstractTestElementWrapper) {
      ((AbstractTestElementWrapper) source).init();
    }
  }

  protected void convertAttributeField(
      Object value, String alias, HierarchicalStreamWriter writer, MarshallingContext context) {

    writer.addAttribute(alias, value.toString());
  }

  protected void convertField(
      Object value, String alias, HierarchicalStreamWriter writer, MarshallingContext context) {

    writer.startNode(alias);
    context.convertAnother(value);
    writer.endNode();
  }

  protected void convertProperty(
      Object value, String name, HierarchicalStreamWriter writer, MarshallingContext context) {

    writer.startNode(getPropertyAlias(value));
    writer.addAttribute("name", name);
    if (value instanceof AbstractTestElementWrapper) {
      AbstractTestElementWrapper testElement = (AbstractTestElementWrapper) value;
      writer.addAttribute("elementType", testElement.getTestClass().getSimpleName());
    }
    context.convertAnother(value);
    writer.endNode();
  }

  protected void convertCollection(
      Object value,
      JmcCollection annotation,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {

    writer.startNode("collectionProp");

    writer.addAttribute("name", annotation.value());
    if (value instanceof AbstractTestElementWrapper) {
      AbstractTestElementWrapper testElement = (AbstractTestElementWrapper) value;
      writer.addAttribute("elementType", testElement.getTestClass().getSimpleName());
    }
    Collection<?> values = (Collection) value;
    if (values != null) {
      for (Object object : values) {
        writer.startNode("elementProp");

        if (object instanceof AbstractTestElementWrapper) {
          AbstractTestElementWrapper testElement = (AbstractTestElementWrapper) object;
          writer.addAttribute("elementType", testElement.getTestClass().getSimpleName());
        }
        context.convertAnother(object);
        writer.endNode();
      }
    }

    writer.endNode();
  }

  protected List<Method> getMethods(Object source) {
    Map<String, Method> result = new HashMap<>();
    Class clazz = source.getClass();
    while (clazz != Object.class) {
      List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
      for (Method method : methods) {
        JmcMethodAlias jmcMethodAlias = method.getAnnotation(JmcMethodAlias.class);
        JmcProperty jmcProperty = method.getAnnotation(JmcProperty.class);
        if (((jmcMethodAlias != null) | (jmcProperty != null))
            && !result.containsKey(method.getName())) {
          if (method.getParameters().length > 0) {
            throw new RuntimeException(
                "Invalid JmcMethodAlias on Method with Parameter : " + method);
          }
          result.put(method.getName(), method);
        }
      }
      clazz = clazz.getSuperclass();
    }
    return new ArrayList<>(result.values());
  }

  protected List<Field> getFields(Object source) {
    Map<String, Field> result = new HashMap<>();
    Class clazz = source.getClass();
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

  protected boolean isProperty(Field field) {

    JmcProperty annotation = field.getAnnotation(JmcProperty.class);

    if (annotation != null) {
      return true;
    }
    return false;
  }

  protected boolean isProperty(Method method) {

    JmcProperty annotation = method.getAnnotation(JmcProperty.class);

    if (annotation != null) {
      return true;
    }
    return false;
  }

  protected boolean isCollection(Method method) {

    JmcCollection annotation = method.getAnnotation(JmcCollection.class);

    if (annotation != null) {
      return true;
    }
    return false;
  }

  protected boolean isCollection(Field field) {

    JmcCollection annotation = field.getAnnotation(JmcCollection.class);

    if (annotation != null) {
      return true;
    }
    return false;
  }

  protected String getFieldAlias(Field field) {

    XStreamAlias annotation = field.getAnnotation(XStreamAlias.class);

    if (annotation != null) {
      return annotation.value();
    }
    return field.getName();
  }

  protected String getMethodAlias(Method method) {

    JmcMethodAlias annotation = method.getAnnotation(JmcMethodAlias.class);

    return annotation.value();
  }

  protected String getPropertyAlias(Object value) {

    if (value instanceof Integer) {
      return "intProp";
    } else if (value instanceof String) {
      return "stringProp";
    } else if (value instanceof Long) {
      return "longProp";
    } else if (value instanceof Boolean) {
      return "boolProp";

    } else if (value instanceof AbstractTestElementWrapper) {
      return "elementProp";
    }
    throw new IllegalStateException("Unknowen properties type for :" + value);
  }

  protected List<Field> getAttributeFields(List<Field> fields) {
    List<Field> result = new ArrayList<>();
    for (Field field : fields) {
      if (field.getAnnotation(XStreamAsAttribute.class) != null) {
        result.add(field);
      }
    }
    return result;
  }

  protected List<Method> getAttributeMethods(List<Method> methods) {
    List<Method> result = new ArrayList<>();
    for (Method method : methods) {
      if (method.getAnnotation(JmcAsAttribute.class) != null) {
        result.add(method);
      }
    }
    return result;
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return null;
  }

  @Override
  public boolean canConvert(Class type) {

    return AbstractTestElementWrapper.class.isAssignableFrom(type)
        || ScriptWrapper.class.isAssignableFrom(type);
  }
}
