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

package org.anasoid.jmc.plugins.component.java.assertions;

import org.anasoid.jmc.plugins.component.java.AbstractJavaTestElement;
import org.apache.jmeter.assertions.Assertion;
import org.apache.jmeter.assertions.AssertionResult;
import org.apache.jmeter.samplers.SampleResult;

/** Java Assertion. */
public class JavaAssertion extends AbstractJavaTestElement<JavaAssertionExecutor>
    implements Assertion {

  private static final long serialVersionUID = 8119460180648610163L;

  @Override
  public AssertionResult getResult(SampleResult response) {
    AssertionResult result = new AssertionResult(getName());
    return getExecutor()
        .execute(
            getLabel(),
            getJMeterContext(),
            getJMeterVariables(),
            getJMeterProperties(),
            getParameters(),
            getExecutorLogger(),
            getCurrentSampler(),
            response,
            result);
  }
}
