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
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;

public class PropertyConverter implements Converter {

  String name;

  public PropertyConverter(Object o, String name) {

    this.name = name;
  }

  @Override
  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

    writer.addAttribute("name", name);
    if (!(source instanceof AbstractTestElementWrapper)) {
      writer.setValue(source.toString());
    } else {
      AbstractTestElementWrapper testElement = (AbstractTestElementWrapper) source;
      writer.addAttribute("enabled", testElement.isEnabled() ? "true" : "false");
      writer.addAttribute("elementType", testElement.getTestClass().getSimpleName());
      writer.addAttribute("testclass", testElement.getTestClass().getSimpleName());
      if(source instanceof JMeterGUIWrapper){
        JMeterGUIWrapper gui = (JMeterGUIWrapper) source;
        writer.addAttribute("guiclass", gui.getGuiClass().getSimpleName());
      }
      context.convertAnother(source);
    }
  }

  @Override
  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    return null;
  }

  @Override
  public boolean canConvert(Class type) {

    return true;
  }
}
