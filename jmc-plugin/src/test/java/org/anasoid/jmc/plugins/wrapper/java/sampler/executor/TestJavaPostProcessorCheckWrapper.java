package org.anasoid.jmc.plugins.wrapper.java.sampler.executor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.extractor.AbstractJavaPostProcessorWrapper;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaPostProcessorCheckWrapper extends AbstractJavaPostProcessorWrapper {

  @Override
  public void execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler,
      SampleResult prev) {

    log.info("TestJavaPostProcessorCheckWrapper");
    if ("content".equals(prev.getResponseDataAsString())) {
      log.info("TestJavaPostProcessorCheckWrapper:success");
    } else {
      log.error("TestJavaPostProcessorCheckWrapper:error");
    }
  }

  @Override
  public List<String> getParametersKey() {
    return null;
  }
}
