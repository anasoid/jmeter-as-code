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
 * Date :   14-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java.sampler;

import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElement;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.samplers.Sampler;

/** Java Post Processor. */
@SuppressWarnings("PMD.MoreThanOneLogger")
public class JavaSampler extends AbstractJavaTestElement<JavaSamplerExecutor>
    implements Cloneable, Sampler {

  private static final long serialVersionUID = 8119460180648610163L;

  @Override
  public SampleResult sample(Entry e) {

    SampleResult result = new SampleResult();
    result.setSampleLabel(getName());
    result.setSuccessful(true);
    result.setResponseCodeOK();
    result.setResponseMessageOK();
    result.setDataType(SampleResult.TEXT);
    result.sampleStart();
    Object ret =
        getExecutor()
            .execute(
                getLabel(),
                getJMeterContext(),
                getJMeterVariables(),
                getJMeterProperties(),
                getParameters(),
                getExecutorLogger(),
                getCurrentSampler(),
                result);
    if (ret != null && (result.getResponseData() == null || result.getResponseData().length == 0)) {
      result.setResponseData(ret.toString(), null);
    }
    result.sampleEnd();
    return result;
  }
}
