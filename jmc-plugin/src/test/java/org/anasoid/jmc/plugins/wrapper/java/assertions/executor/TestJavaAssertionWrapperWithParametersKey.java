package org.anasoid.jmc.plugins.wrapper.java.assertions.executor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.assertions.AbstractJavaAssertionWrapper;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaAssertionWrapperWithParametersKey extends AbstractJavaAssertionWrapper {

  @Override
  public AssertionResult execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler,
      SampleResult prev,
      AssertionResult assertionResult) {

    log.info("TestJavaAssertionWrapperWithParametersKey : #########ME###############");
    Assertions.assertNotNull(label);
    Assertions.assertNotNull(ctx);
    Assertions.assertNotNull(vars);
    Assertions.assertNotNull(props);
    Assertions.assertNotNull(parameters);
    Assertions.assertNotNull(log);
    Assertions.assertNotNull(sampler);
    Assertions.assertNotNull(prev);
    Assertions.assertNotNull(assertionResult);
    return assertionResult;
  }

  @Override
  public List<String> getParametersKey() {

    return Arrays.asList("me", "me1", "me4");
  }
}
