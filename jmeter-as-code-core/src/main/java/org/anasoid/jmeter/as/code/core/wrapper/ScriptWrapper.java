package org.anasoid.jmeter.as.code.core.wrapper;
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
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;

@XStreamAlias("jmeterTestPlan")
@XStreamConverter(TestElementConverter.class)
public class ScriptWrapper {
  @XStreamAsAttribute private String version = "1.2";
  @XStreamAsAttribute private String jmeter = "5.3";
  @XStreamAsAttribute private String properties = "5.0";

  @XStreamAlias("hashTree")
  List testPlan;

  public ScriptWrapper setTesPlan(TestPlanWrapper testPlanWrapper) {
    testPlan=new ArrayList();
    testPlan.add(testPlanWrapper);
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

  public List getTestPlan() {
    return testPlan;
  }
}
