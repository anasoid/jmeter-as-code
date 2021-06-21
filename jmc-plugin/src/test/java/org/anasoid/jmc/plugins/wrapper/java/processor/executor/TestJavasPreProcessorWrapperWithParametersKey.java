package org.anasoid.jmc.plugins.wrapper.java.processor.executor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.processor.AbstractJavaPreProcessorWrapper;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavasPreProcessorWrapperWithParametersKey extends AbstractJavaPreProcessorWrapper {

  @Override
  public void execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler) {

    log.info("TestJavaPreProcessorWrapperWithParametersKey : #########ME###############");
    Assertions.assertNotNull(label);
    Assertions.assertNotNull(ctx);
    Assertions.assertNotNull(vars);
    Assertions.assertNotNull(props);
    Assertions.assertNotNull(parameters);
    Assertions.assertNotNull(log);
    Assertions.assertNotNull(sampler);
  }

  @Override
  public List<String> getParametersKey() {

    return Arrays.asList("me", "me1", "me4");
  }
}
