package org.anasoid.jmc.plugins.wrapper.java.sampler.executor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.sampler.AbstractJavaSamplerWrapper;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaSamplerWrapper extends AbstractJavaSamplerWrapper {

  @Override
  public String execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler,
      SampleResult sampleResult) {

    log.info("TestJavaSamplerWrapper : ######################ME#####################");
    Assertions.assertNotNull(label);
    Assertions.assertNotNull(ctx);
    Assertions.assertNotNull(vars);
    Assertions.assertNotNull(props);
    Assertions.assertNotNull(parameters);
    Assertions.assertNotNull(log);
    Assertions.assertNotNull(sampler);
    Assertions.assertNotNull(sampleResult);
    return "content";
  }

  @Override
  public List<String> getParametersKey() {
    return null;
  }
}
