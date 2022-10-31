package org.anasoid.jmc.plugins.wrapper.java.sampler.executor;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.plugins.wrapper.java.sampler.AbstractJavaSamplerWrapper;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

/**
 * test class.
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class TestJavaSamplerOnStartEndWrapper extends AbstractJavaSamplerWrapper {

  public static boolean ON_START;
  public static boolean ON_END;

  private static int COUNTER_START;
  private static int COUNTER_END;
  private int counterStart;
  private int counterEnd;
  @Default
  private int numberInstance = 1;

  /**
   * init variable, should be executed before each test.
   */
  public static void initialize() {
    ON_START = false;
    ON_END = false;
    COUNTER_START = 0;
    COUNTER_END = 0;
  }

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

    log.info("TestJavaSamplerOnStartEndWrapper : ######################ME#####################");
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


  @Override
  public void onStartTest() {
    ON_START = true;

    COUNTER_START++;
    counterStart++;

    if (COUNTER_START > numberInstance || COUNTER_START <= 0) {
      throw new IllegalStateException("COUNTER_START already started : " + COUNTER_START);
    }
    if (counterStart != 1) {
      throw new IllegalStateException("counterStart already started : " + counterStart);
    }
  }

  @Override
  public void onEndTest() {
    ON_END = true;
    COUNTER_END--;
    counterEnd--;

    if (COUNTER_END >= numberInstance || COUNTER_END < 0) {
      throw new IllegalStateException("COUNTER_START already started : " + COUNTER_END);
    }
    if (counterEnd != 0) {
      throw new IllegalStateException("Counter already Ended : " + counterEnd);
    }
  }
}
