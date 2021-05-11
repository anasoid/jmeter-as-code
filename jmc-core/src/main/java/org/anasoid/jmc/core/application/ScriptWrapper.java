package org.anasoid.jmc.core.application;
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
 * Date :   17-Jan-2021
 */

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.util.ArrayList;
import java.util.List;
import org.anasoid.jmc.core.config.JmcConfig;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.xstream.converters.TestElementConverter;

@XStreamAlias("jmeterTestPlan")
@XStreamConverter(TestElementConverter.class)
@SuppressWarnings("PMD.FinalFieldCouldBeStatic")
class ScriptWrapper {

  @XStreamAsAttribute private final String version = JmcConfig.getVersion(); // NOSONAR
  @XStreamAsAttribute private final String jmeter = JmcConfig.getJMeterVersion(); // NOSONAR
  @XStreamAsAttribute private final String properties = JmcConfig.getPropertiesVersion(); // NOSONAR

  @XStreamAlias("hashTree")
  List<TestElementWrapper<?>> testPlan;

  /** getTestPlan. */
  protected TestElementWrapper<?> getTestPlan() {

    return testPlan.get(0);
  }

  /**
   * Set main test Plan.
   *
   * @param testPlanWrapper test plan.
   */
  public ScriptWrapper setTestPlan(TestPlanWrapper testPlanWrapper) {
    testPlan = new ArrayList<>();
    testPlan.add(testPlanWrapper);
    return this;
  }

  /** Only for Test. */
  protected ScriptWrapper setTestPlan(TestElementWrapper<?> testElement) {
    testPlan = new ArrayList<>();
    testPlan.add(testElement);
    return this;
  }

  public String getVersion() {
    return version;
  }

  public String getJmeter() {
    return jmeter;
  }

  public String getProperties() {
    return properties;
  }
}
