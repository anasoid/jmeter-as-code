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
 * Date :   06-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.test;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.SamplerJmxIncludeWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcParam;

/** for testing . */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class ParamSamplerJmxIncludeWrapperTesting extends SamplerJmxIncludeWrapper {

  private static final String PARENT_PATH =
      "org/anasoid/jmeter/as/code/core/wrapper/jmeter/jmc/generic";

  @Override
  protected String getDefaultPath() {
    return PARENT_PATH + "/node.http.sampler.param.jmx";
  }

  @JmcParam("path")
  private static String myPath = "mypath";
}