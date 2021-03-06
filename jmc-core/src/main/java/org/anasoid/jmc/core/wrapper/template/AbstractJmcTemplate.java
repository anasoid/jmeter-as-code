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

package org.anasoid.jmc.core.wrapper.template;

import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;

/**
 * Abstract template to generate test element.
 *
 * @param <W> testElement.
 */
public abstract class AbstractJmcTemplate<
        W extends AbstractBasicTestElementWrapper<?>, T extends JmcWrapperBuilder>
    implements JmcTemplate<W> {

  /**
   * Init builder, and customize it.
   *
   * @return builder.
   */
  protected abstract JmcWrapperBuilder<?> init();

  /**
   * Prepare build to custom more attributes.
   *
   * @param builder input is returned by method init().
   */
  protected void prepareBuilder(T builder) {}

  /**
   * finalize wrapper to custom more attributes.
   *
   * @param wrapper Wrapper.
   */
  protected void prepareWrapper(W wrapper) {}

  @Override
  public final W generate() {
    T b = (T) init();
    prepareBuilder(b);
    W result = (W) b.build();
    prepareWrapper(result);
    return result;
  }
}
