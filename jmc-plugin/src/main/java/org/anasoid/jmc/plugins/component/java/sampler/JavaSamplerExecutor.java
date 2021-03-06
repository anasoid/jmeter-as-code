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
 * Date :   15-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java.sampler;

import java.util.Map;
import java.util.Properties;
import org.anasoid.jmc.plugins.component.java.JavaTestElementExecutor;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jmeter.threads.JMeterContext;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;

/** JavaSampler Executor. */
public interface JavaSamplerExecutor extends JavaTestElementExecutor {

  /**
   * Main executor method.
   *
   * @param label Descriptive name for this element that is shown in the tree.
   * @param ctx gives access to the context.
   * @param vars gives read/write access to variables.
   * @param props gives read/write access to properties.
   * @param parameters Parameters to pass to the Executor.
   * @param log can be used to write to the log file.
   * @param sampler gives access to the current sampler.
   * @param sampleResult pointer to the current SampleResult, this the result.
   * @return The SampleResult ResponseData is set from the return value of the script. If the script
   *     returns null, it can set the response directly, by using the method
   *     SampleResult.setResponseData(data), where data is either a String or a byte array. The data
   *     type defaults to "text", but can be set to binary by using the method
   *     SampleResult.setDataType(SampleResult.BINARY).
   */
  Object execute(
      String label,
      JMeterContext ctx,
      JMeterVariables vars,
      Properties props,
      Map<String, String> parameters,
      Logger log,
      Sampler sampler,
      SampleResult sampleResult);
}
