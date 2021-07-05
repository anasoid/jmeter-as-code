package org.anasoid.jmc.plugins.wrapper.java.visualizers.executor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.visualizers.AbstractJavaListenerWrapper;
import org.apache.jmeter.samplers.SampleEvent;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaSampleListenerWrapperWithParametersKey
    extends AbstractJavaListenerWrapper {

  @Override
  public void execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler,
      SampleResult prev,
      SampleEvent event) {

    log.info("TestJavaSampleListenerWrapperWithParametersKey : #########ME###############");
    Assertions.assertNotNull(label);
    Assertions.assertNotNull(ctx);
    Assertions.assertNotNull(vars);
    Assertions.assertNotNull(props);
    Assertions.assertNotNull(parameters);
    Assertions.assertNotNull(log);
    Assertions.assertNotNull(sampler);
    Assertions.assertNotNull(prev);
    Assertions.assertNotNull(event);
  }

  @Override
  public List<String> getParametersKey() {

    return Arrays.asList("me", "me1", "me4");
  }
}
