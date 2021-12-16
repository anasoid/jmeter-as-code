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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmc.core.application;

import com.thoughtworks.xstream.XStream;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.anasoid.jmc.core.xstream.io.xml.JmcXppDriver;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.save.converters.MultiPropertyConverter;
import org.apache.jmeter.save.converters.SampleSaveConfigurationConverter;
import org.apache.jmeter.save.converters.StringPropertyConverter;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.StringProperty;
import org.apache.jorphan.collections.HashTree;

/** Jmc Xstream serializer. */
public class JmcXstream extends XStream {
  private static List<Class<?>> listClazz;

  /** constructor. */
  public JmcXstream() {
    super(new JmcXppDriver());
    this.setMode(XStream.NO_REFERENCES);
    this.alias("hashTree", HashTree.class);
    List<Class<?>> clazzs = new ArrayList<>();
    clazzs.add(ScriptWrapper.class);
    clazzs.addAll(getProcessClazz());
    Class<?>[] clazzArray = new Class[clazzs.size()];
    clazzArray = clazzs.toArray(clazzArray);
    this.processAnnotations(clazzArray);
    this.registerConverter(new StringPropertyConverter());
    this.alias(JMeterProperty.STRING.value, StringProperty.class);
    this.alias(JMeterProperty.COLLECTION.value, CollectionProperty.class);
    this.registerConverter(new MultiPropertyConverter(this.getMapper()));

    this.registerConverter(new SampleSaveConfigurationConverter(this.getMapper()));
    this.alias(SampleSaveConfiguration.class.getSimpleName(), SampleSaveConfiguration.class);

    this.addImplicitCollection(CollectionProperty.class, "value");
  }

  @SuppressWarnings("PMD.AvoidSynchronizedAtMethodLevel")
  private static synchronized List<Class<?>> getProcessClazz() {
    if (listClazz == null) {
      try (ScanResult scanResult =
          new ClassGraph()
              .enableClassInfo()
              .rejectJars(
                  "gradle-*.jar",
                  "kotlin-*.jar",
                  "junit-*.jar",
                  "log4j-*.jar",
                  "jetty-*.jar",
                  "ApacheJMeter_*-*.jar",
                  "groovy-*.jar",
                  "commons-*.jar",
                  "wiremock-*.jar",
                  "guava-*.jar",
                  "assertj-core-*.jar",
                  "Saxon-*.jar",
                  "saxon-*.jar",
                  "serializer-*.jar",
                  "xalan-*.jar",
                  "byte-buddy-*.jar",
                  "darklaf-*.jar")
              .scan()) {
        ClassInfoList classInfoList =
            scanResult.getSubclasses(AbstractTestElementWrapper.class.getName());
        listClazz = classInfoList.stream().map(c -> c.loadClass(true)).collect(Collectors.toList());
      }
    }
    return listClazz;
  }
}
