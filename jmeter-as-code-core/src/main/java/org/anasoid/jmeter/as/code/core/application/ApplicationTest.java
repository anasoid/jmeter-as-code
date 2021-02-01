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
 * Date :   31-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.application;

import com.google.common.io.Files;
import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.apache.jorphan.collections.HashTree;

/** Main application for Test. */
public class ApplicationTest {

  private final TestPlanWrapper testPlanWrapper;

  public ApplicationTest(TestPlanWrapper testPlanWrapper) {
    this.testPlanWrapper = testPlanWrapper;
  }

  /**
   * Generate Jmx file.
   *
   * @param out Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public void toJmx(Writer out) throws IOException {
    ScriptWrapper script = new ScriptWrapper().setTesPlan(testPlanWrapper);
    try (out) {
      out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + System.lineSeparator());
      getXstream().toXML(script, out);
    }
  }

  /**
   * Generate Jmx file.
   *
   * @param file Destination output.
   * @throws IOException – If an I/O error occurs.
   */
  public void toJmx(File file) throws IOException {
    this.toJmx(Files.newWriter(file, StandardCharsets.UTF_8));
  }

  private XStream getXstream() {
    XStream xstream = new XStream();
    xstream.setMode(XStream.NO_REFERENCES);
    xstream.aliasSystemAttribute(null, "class");
    xstream.alias("hashTree", HashTree.class);
    xstream.processAnnotations(
        new Class[] {
          LoopControllerWrapper.class,
          HTTPSamplerProxyWrapper.class,
          TestPlanWrapper.class,
          ThreadGroupWrapper.class,
          ScriptWrapper.class
        });
    return xstream;
  }
}
