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

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.anasoid.jmeter.as.code.core.xstream.ConverterBeanUtils;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;

public class TestElementConverter implements Converter {

  public static final String ATTRIBUTE_ELEMENT_TYPE = "elementType";
  private boolean inElementConversion;

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

    init(source);
    List<Field> allFields = ConverterBeanUtils.getFields(source);
    List<Method> methods = ConverterBeanUtils.getMethods(source);

    List<AccessibleObject> allFieldsMethods = new ArrayList<>();
    allFieldsMethods.addAll(allFields);
    allFieldsMethods.addAll(methods);
    List<AccessibleObject> attributes = ConverterBeanUtils.getAttributeOnly(allFieldsMethods);
    List<AccessibleObject> nonAttributes = new ArrayList<>(allFieldsMethods);
    nonAttributes.removeAll(attributes);

    for (AccessibleObject accessibleObject : attributes) {
      if (!ConverterBeanUtils.shouldSkip(source, accessibleObject)) {
        Object value = ConverterBeanUtils.getValue(accessibleObject, source);
        convertAttributeField(
            value, ConverterBeanUtils.getAlias(accessibleObject), writer, context);
      }
    }

    for (AccessibleObject accessibleObject : nonAttributes) {

      if (!ConverterBeanUtils.shouldSkip(source, accessibleObject)) {
        Object value = ConverterBeanUtils.getValue(accessibleObject, source);
        if (ConverterBeanUtils.isProperty(accessibleObject)) {
          convertProperty(
              value, accessibleObject.getAnnotation(JmcProperty.class).value(), writer, context);
        } else if (ConverterBeanUtils.isCollection(accessibleObject)) {

          JmcCollection annotation = accessibleObject.getAnnotation(JmcCollection.class);
          convertCollection(value, annotation, writer, context);
        } else {
          convertField(value, ConverterBeanUtils.getAlias(accessibleObject), writer, context);
        }
      }
    }

    appendChild(source, writer, context);
  }

  protected void appendChild(
      Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    if (inElementConversion) {

      return;
    }
    if (source instanceof AbstractTestElementWrapper) {

      List<AbstractTestElementWrapper<?>> childs =
          ((AbstractTestElementWrapper) source).getChilds();
      writer.endNode();
      writer.startNode("hashTree");
      for (AbstractTestElementWrapper<?> child : childs) {
        writer.startNode(child.getTestClassAsString());
        context.convertAnother(child);
        writer.endNode();
      }
    }
  }

  protected void init(Object source) {

    if (source instanceof AbstractTestElementWrapper) {
      ((AbstractTestElementWrapper) source).init();
    }
  }

  protected void convertAttributeField(
      Object value,
      String alias,
      HierarchicalStreamWriter writer,
      MarshallingContext context) { // NOSONAR

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
    boolean changed = false;
    if (!inElementConversion) {
      inElementConversion = true;
      changed = true;
    }

    writer.startNode(ConverterBeanUtils.getPropertyAlias(value));
    writer.addAttribute("name", name);
    if (value instanceof AbstractTestElementWrapper) {
      AbstractTestElementWrapper<?> testElement = (AbstractTestElementWrapper) value;
      writer.addAttribute(ATTRIBUTE_ELEMENT_TYPE, testElement.getTestClass().getSimpleName());
    }
    if (value.getClass().isEnum()) {
      context.convertAnother(ConverterBeanUtils.getEnumValue(value));
    } else {
      context.convertAnother(value);
    }
    writer.endNode();
    if (changed) {
      inElementConversion = false;
    }
  }

  @SuppressWarnings("PMD.NPathComplexity")
  protected void convertCollection(
      Object value,
      JmcCollection annotation,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {
    if (annotation.withElementProp()) {
      writer.startNode(JMeterProperty.ELEMENT.value);
      if (!annotation.name().isBlank()) {
        writer.addAttribute("name", annotation.name());
      }
      if (!annotation.testname().isBlank()) {
        writer.addAttribute("testname", annotation.testname());
      }
      if (!annotation.elementType().equals(Void.class)) {
        writer.addAttribute(ATTRIBUTE_ELEMENT_TYPE, annotation.elementType().getSimpleName());
      }
      if (!annotation.guiclass().equals(Void.class)) {
        writer.addAttribute("guiclass", annotation.guiclass().getSimpleName());
      }
      if (!annotation.testclass().equals(Void.class)) {
        writer.addAttribute("testclass", annotation.testclass().getSimpleName());
      }
      if (annotation.enabled()) {
        writer.addAttribute("enabled", "true");
      }
    }
    writer.startNode("collectionProp");
    boolean changed = false;
    if (!inElementConversion) {
      inElementConversion = true;
      changed = true;
    }
    writer.addAttribute("name", annotation.value());

    Collection<?> values = (Collection) value;
    if (values != null) {
      for (Object object : values) {
        writer.startNode(JMeterProperty.ELEMENT.value);

        if (object instanceof AbstractTestElementWrapper) {
          AbstractTestElementWrapper<?> testElement = (AbstractTestElementWrapper) object;
          writer.addAttribute(ATTRIBUTE_ELEMENT_TYPE, testElement.getTestClass().getSimpleName());
        }
        context.convertAnother(object);
        writer.endNode();
      }
    }

    writer.endNode();
    if (annotation.withElementProp()) {
      writer.endNode();
    }
    if (changed) {
      inElementConversion = false;
    }
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return null;
  }

  @Override
  public boolean canConvert(Class type) {

    return AbstractTestElementWrapper.class.isAssignableFrom(type)
        || "ScriptWrapper".equals(type.getClass().getSimpleName());
  }
}
