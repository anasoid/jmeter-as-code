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

package org.anasoid.jmeter.as.code.core.converter.xstream;

import com.thoughtworks.xstream.XStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import org.anasoid.jmeter.as.code.core.wrapper.ScriptWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.Test;

public class XstreamTest {

  @Test
  public void testWrapper() throws Exception {

    // First HTTP Sampler - open example.com
    HTTPSamplerProxyWrapper examplecomSamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("example.com")
            .withPort(80)
            .withPath("/")
            .withMethod("GET")
            .withName("Open example.com")
            .build();

    // Second HTTP Sampler - open blazemeter.com
    HTTPSamplerProxyWrapper blazemetercomSamplerWrapper =
        HTTPSamplerProxyWrapper.builder()
            .withDomain("blazemeter.com")
            .withPort(80)
            .withPath("/")
            .withMethod("GET")
            .withName("Open blazemeter.com")
            .build();

    // Thread Group
    ThreadGroupWrapper threadGroupWrapper =
        ThreadGroupWrapper.builder()
            .withName("Example Thread Group")
            .withNumThreads(1)
            .withDelay(9l)
            .withRampUp(7)
            .withLoops(7)
            .withContinueForever(false)
            .addChild(examplecomSamplerWrapper)
            .addChild(blazemetercomSamplerWrapper)
            .build();

    // Test Plan
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .withName("Create JMeter Script From Java Code")
            .addArgument("testplanArg1","valuearg1")
            .addArgument("testplanArg2","valuearg2")
            .addChild(threadGroupWrapper)
            .build();

    ScriptWrapper script=new ScriptWrapper().setTesPlan(testPlanWrapper);
    // Construct Test Plan from previously initialized elements
    // JMeter Test Plan, basically JOrphan HashTree
    // HashTree testPlanTree = testPlanWrapper.convertAll();

    Writer wr = new FileWriter(System.getProperty("user.dir") + "/build/jmeter/xtream.jmx");
    XStream xStream = new XStream();
    xStream.aliasSystemAttribute(null, "class");
    xStream.alias("hashTree",HashTree.class);
    xStream.processAnnotations(
        new Class[] {
          LoopControllerWrapper.class,
          HTTPSamplerProxyWrapper.class,
          TestPlanWrapper.class,
          ThreadGroupWrapper.class, ScriptWrapper.class
        });
    xStream.toXML(script, wr);
  }
}
