package org.anasoid.jmeter.as.code.core;

import com.thoughtworks.xstream.XStream;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import org.anasoid.jmeter.as.code.core.wrapper.ScriptWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.control.LoopControllerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.HTTPSamplerProxyWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.junit.jupiter.api.Test;

public class FirstTest {

  String jmeterHomePath = "/opt/jmeter/apache-jmeter-5.3";

  @Test
  public void testWrapper() throws Exception {

    String slash = System.getProperty("file.separator");

    File jmeterWork = new File(System.getProperty("user.dir") + slash + "build" + slash + "jmeter");

    if (!jmeterWork.exists()) {
      jmeterWork.mkdir();
    }

    File jmeterProperties = new File(jmeterHomePath + slash + "bin" + slash + "jmeter.properties");
    if (jmeterProperties.exists()) {
      // JMeter Engine
      StandardJMeterEngine jmeter = new StandardJMeterEngine();

      // JMeter initialization (properties, log levels, locale, etc)
      JMeterUtils.setJMeterHome(jmeterHomePath);
      JMeterUtils.loadJMeterProperties(jmeterProperties.getPath());
      JMeterUtils
          .initLogging(); // you can comment this line out to see extra log messages of i.e. DEBUG
      // level
      JMeterUtils.initLocale();

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
              .withNumThreads(2)
              .withRampUp(7)
              .withDelay(9l)
              .withLoops(3)
              .addChild(examplecomSamplerWrapper)
              .addChild(blazemetercomSamplerWrapper)
              .addChild(blazemetercomSamplerWrapper)
              .build();

      // Test Plan
      TestPlanWrapper testPlanWrapper =
          TestPlanWrapper.builder()
              .withName("Create JMeter Script From Java Code")
              .addChild(threadGroupWrapper)
              .build();
      ScriptWrapper script = new ScriptWrapper().setTesPlan(testPlanWrapper);

      // NEW

      Writer wr = new FileWriter(jmeterWork + slash + "exampleWrapper.jmx");
      XStream xStream = new XStream();
      xStream.setMode(XStream.NO_REFERENCES);
      xStream.aliasSystemAttribute(null, "class");
      xStream.alias("hashTree", HashTree.class);
      xStream.processAnnotations(
          new Class[] {
            LoopControllerWrapper.class,
            HTTPSamplerProxyWrapper.class,
            TestPlanWrapper.class,
            ThreadGroupWrapper.class,
            ScriptWrapper.class
          });
      xStream.toXML(script, wr);
      // add Summarizer output to get test progress in stdout like:
      // summary =      2 in   1.3s =    1.5/s Avg:   631 Min:   290 Max:   973 Err:     0 (0.00%)
      Summariser summer = null;
      String summariserName = JMeterUtils.getPropDefault("summariser.name", "summary");
      if (summariserName.length() > 0) {
        summer = new Summariser(summariserName);
      }

      // Store execution results into a .jtl file
      /**
      String logFile = jmeterWork + slash + "exampleWrapper.jtl";
      ResultCollector logger = new ResultCollector(summer);
      logger.setFilename(logFile);
      HashTree testPlanTree =
          SaveService.loadTree(new File(jmeterWork + slash + "exampleWrapper.jmx"));
      testPlanTree.add(testPlanTree.getArray()[0], logger);

      // Run Test Plan
      jmeter.configure(testPlanTree);
      //jmeter.run();

      System.out.println(
          "Test completed. See " + jmeterWork + slash + "example.jtl file for results");
      System.out.println(
          "JMeter .jmx script is available at " + jmeterWork + slash + "exampleWrapper.jmx");
      return;
       */
    }

    System.err.println("jmeter.home property is not set or pointing to incorrect location");
    throw new RuntimeException("jmeter.home property is not set or pointing to incorrect location");
  }
}
