package org.anasoid.jmc.plugins.wrapper.java.assertions.executor;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.assertions.AbstractJavaAssertionWrapper;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;

/** test class. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaAssertionWrapperWithField extends AbstractJavaAssertionWrapper {

  private int increment;
  @XStreamOmitField @Default private Map myMap = new HashMap();

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

    increment++;
    if (increment == 2 || increment == 102) {
      log.error("increment");
    }
    if (parameters.size() > 0) {
      log.error(parameters.keySet().iterator().next());
    }
    return assertionResult;
  }

  @Override
  public List<String> getParametersKey() {
    return Arrays.asList("me", "me1", "me2");
  }
}
