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
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.generic.AbstractJmxIncludeWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.anasoid.jmeter.as.code.core.xstream.ConverterBeanUtils;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.anasoid.jmeter.as.code.core.xstream.io.xml.JmcXstreamWriter;

/** Main xstream converter. */
@SuppressWarnings("PMD.TooManyMethods")
public class TestElementConverter implements Converter {

  private boolean inElementConversion;

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

    init(source);

    List<AccessibleObject> allFieldsMethods = new ArrayList<>();
    allFieldsMethods.addAll(ConverterBeanUtils.getFields(source));
    allFieldsMethods.addAll(ConverterBeanUtils.getMethods(source));

    List<AccessibleObject> attributes = ConverterBeanUtils.getAttributeOnly(allFieldsMethods);
    List<AccessibleObject> nonAttributes = new ArrayList<>(allFieldsMethods);
    nonAttributes.removeAll(attributes);

    // first convert attributes
    for (AccessibleObject accessibleObject : attributes) {
      convertField(source, accessibleObject, writer, context);
    }
    // second convert non attributes
    for (AccessibleObject accessibleObject : nonAttributes) {
      convertField(source, accessibleObject, writer, context);
    }

    appendChild(source, writer, context);
  }

  /** Convert field/method. */
  protected void convertField(
      Object source,
      AccessibleObject accessibleObject,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {
    if (!ConverterBeanUtils.shouldSkip(source, accessibleObject)) {
      Object value = ConverterBeanUtils.getValue(accessibleObject, source);
      if (ConverterBeanUtils.isAttribute(accessibleObject)) {
        convertAttributeField(value, accessibleObject, writer, context);
      } else {
        if (ConverterBeanUtils.isProperty(accessibleObject)) {
          convertProperty(value, accessibleObject, writer, context);
        } else if (ConverterBeanUtils.isCollection(accessibleObject)) {

          convertCollection(value, accessibleObject, writer, context);
        } else {
          convertChildField(value, accessibleObject, writer, context);
        }
      }
    }
  }

  protected void appendChild(
      Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
    if (inElementConversion) {

      return;
    }
    if (source instanceof TestElementWrapper) {

      List<TestElementWrapper<?>> childs = ((TestElementWrapper) source).getChilds();
      writer.endNode();
      writer.startNode("hashTree");
      if (childs != null) {
        int i = 0;
        for (TestElementWrapper<?> child : childs) {

          if (child instanceof AbstractJmxIncludeWrapper) {
            include((AbstractJmxIncludeWrapper) child, writer, i);
          } else {
            writer.startNode(child.getTestClassAsString());
            context.convertAnother(child);
            writer.endNode();
          }
          i++;
        }
      }
    }
  }

  private void include(
      AbstractJmxIncludeWrapper includeWrapper, HierarchicalStreamWriter writer, int count) {

    try {
      Method method = includeWrapper.getClass().getMethod("toXml");
      String result = (String) method.invoke(includeWrapper);
      if (writer instanceof JmcXstreamWriter) {
        JmcXstreamWriter jmcXstreamWriter = (JmcXstreamWriter) writer;
        if (count == 0) {
          jmcXstreamWriter.setValue("");
        }
        jmcXstreamWriter.writeRaw("\n");
        jmcXstreamWriter.writeRaw(result);
      }

    } catch (NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException
        | IOException e) {
      throw new ConversionException(e);
    }
  }

  protected void init(Object source) {

    if (source instanceof TestElementWrapper) {
      ((TestElementWrapper) source).init();
    }
  }

  protected void convertAttributeField(
      Object value,
      AccessibleObject accessibleObject,
      HierarchicalStreamWriter writer,
      MarshallingContext context) { // NOSONAR
    String alias = ConverterBeanUtils.getAlias(accessibleObject);
    writer.addAttribute(alias, value.toString());
  }

  protected void convertChildField(
      Object value,
      AccessibleObject accessibleObject,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {
    String alias = ConverterBeanUtils.getAlias(accessibleObject);
    writer.startNode(alias);
    context.convertAnother(value);
    writer.endNode();
  }

  protected void convertProperty(
      Object value,
      AccessibleObject accessibleObject,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {
    JmcProperty jmcProperty = ConverterBeanUtils.getAnnotation(accessibleObject, JmcProperty.class);
    String name = jmcProperty.value();

    boolean changed = false;
    if (!inElementConversion) {
      inElementConversion = true;
      changed = true;
    }

    writer.startNode(
        ConverterBeanUtils.getPropertyAlias(
            value, ConverterBeanUtils.getPropertyType(accessibleObject)));
    writer.addAttribute("name", name);
    if (value instanceof TestElementWrapper) {
      TestElementWrapper<?> testElement = (TestElementWrapper) value;
      writer.addAttribute(
          ConvertConstant.ATTRIBUTE_ELEMENT_TYPE, testElement.getTestClass().getSimpleName());
    }
    if (value != null) {
      if (value.getClass().isEnum()) {
        context.convertAnother(ConverterBeanUtils.getEnumValue(value));
      } else {
        context.convertAnother(value);
      }
    } else {
      context.convertAnother("");
    }
    writer.endNode();
    if (changed) {
      inElementConversion = false;
    }
  }

  @SuppressWarnings("PMD.NPathComplexity")
  protected void convertCollection(
      Object value,
      AccessibleObject accessibleObject,
      HierarchicalStreamWriter writer,
      MarshallingContext context) {
    JmcCollection annotation =
        ConverterBeanUtils.getAnnotation(accessibleObject, JmcCollection.class);
    if (annotation.withElementProp()) {
      writer.startNode(JMeterProperty.ELEMENT.value);
      if (!annotation.name().isBlank()) {
        writer.addAttribute("name", annotation.name());
      }
      if (!annotation.testname().isBlank()) {
        writer.addAttribute("testname", annotation.testname());
      }
      if (!annotation.elementType().equals(Void.class)) {
        writer.addAttribute(
            ConvertConstant.ATTRIBUTE_ELEMENT_TYPE, annotation.elementType().getSimpleName());
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

        if (object instanceof TestElementWrapper) {
          TestElementWrapper<?> testElement = (TestElementWrapper) object;
          if (testElement.getTestClass() != null) {
            writer.addAttribute(
                ConvertConstant.ATTRIBUTE_ELEMENT_TYPE, testElement.getTestClass().getSimpleName());
          }
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

    return TestElementWrapper.class.isAssignableFrom(type)
        || "ScriptWrapper".equals(type.getClass().getSimpleName());
  }
}
