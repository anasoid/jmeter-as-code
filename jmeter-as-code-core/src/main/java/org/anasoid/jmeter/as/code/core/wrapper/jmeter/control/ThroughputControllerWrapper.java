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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.types.IntegerManager;
import org.apache.jmeter.control.ThroughputController;
import org.apache.jmeter.control.gui.ThroughputControllerGui;

/**
 * Wrapper for ThroughputController.
 *
 * @see ThroughputController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class ThroughputControllerWrapper
    extends GenericControllerWrapper<ThroughputController, ThroughputControllerGui> {

  /** Whether the controller will run in percent executions or total executions mode. */
  @JmcProperty("ThroughputController.style")
  @Getter
  @Setter
  @Default
  @JmcMandatory
  private ExecutionStyle executionStyle = ExecutionStyle.PercentExecutions;

  /**
   * A number. For percent execution mode, a number from 0-100 that indicates the percentage of
   * times the controller will execute. "50" means the controller will execute during half the
   * iterations through the test plan. For total execution mode, the number indicates the total
   * number of times the controller will execute.
   */
  @JmcProperty(value = "ThroughputController.maxThroughput", type = IntegerManager.class)
  @Getter
  @Setter
  @JmcMandatory
  private String maxThroughput;

  /**
   * If checked, per user will cause the controller to calculate whether it should execute on a per
   * user (per thread) basis. If unchecked, then the calculation will be global for all users. For
   * example, if using total execution mode, and uncheck "per user", then the number given for
   * throughput will be the total number of executions made. If "per user" is checked, then the
   * total number of executions would be the number of users times the number given for throughput.
   */
  @JmcProperty("ThroughputController.perThread")
  @Getter
  @Setter
  @Default
  private boolean perUser = false;

  @Override
  public Class<?> getGuiClass() {
    return ThroughputControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ThroughputController.class;
  }

  /** enum for Format. */
  public enum ExecutionStyle {
    PercentExecutions(1),
    TotalExecutions(0);

    public final Integer value;

    private ExecutionStyle(Integer value) {
      this.value = value;
    }

    public Integer value() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** Builder. */
  public abstract static class ThroughputControllerWrapperBuilder<
          C extends ThroughputControllerWrapper, B extends ThroughputControllerWrapperBuilder<C, B>>
      extends GenericControllerWrapper.GenericControllerWrapperBuilder<
          ThroughputController, ThroughputControllerGui, C, B> {

    /**
     * A number. For percent execution mode, a number from 0-100 that indicates the percentage of
     * times the controller will execute. "50" means the controller will execute during half the
     * iterations through the test plan. For total execution mode, the number indicates the total
     * number of times the controller will execute.
     */
    public B withMaxThroughput(String maxThroughput) {
      this.maxThroughput = maxThroughput;
      return self();
    }

    /**
     * A number. For percent execution mode, a number from 0-100 that indicates the percentage of
     * times the controller will execute. "50" means the controller will execute during half the
     * iterations through the test plan. For total execution mode, the number indicates the total
     * number of times the controller will execute.
     */
    public B withMaxThroughput(int maxThroughput) {
      return withMaxThroughput(String.valueOf(maxThroughput));
    }

    /**
     * A number. For percent execution mode, a number from 0-100 that indicates the percentage of
     * times the controller will execute. "50" means the controller will execute during half the
     * iterations through the test plan. For total execution mode, the number indicates the total
     * number of times the controller will execute.
     */
    public B withMaxThroughput(Variable maxThroughput) {
      return withMaxThroughput(String.valueOf(maxThroughput.getValue()));
    }
  }
}
