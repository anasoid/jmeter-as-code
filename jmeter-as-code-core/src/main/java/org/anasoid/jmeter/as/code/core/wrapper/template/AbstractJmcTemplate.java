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
 * Date :   28-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.template;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper.AbstractBasicTestElementWrapperBuilder;

/**
 * Abstract template to generate test element.
 *
 * @param <W> testElement.
 */
public abstract class AbstractJmcTemplate<W extends AbstractBasicTestElementWrapper<?>>
    implements JmcTemplate<W> {

  protected abstract AbstractBasicTestElementWrapperBuilder<?, W, ?> init();

  protected void prepare(AbstractBasicTestElementWrapperBuilder<?, W, ?> builder) {}

  @Override
  public final W generate() {
    AbstractBasicTestElementWrapperBuilder<?, W, ?> b = init();
    prepare(b);
    return b.build();
  }
}
