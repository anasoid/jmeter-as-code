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
 * Date :   09-Jun-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.arrivals;

import com.blazemeter.jmeter.threads.arrivals.ArrivalsThreadGroup;
import com.blazemeter.jmeter.threads.arrivals.ArrivalsThreadGroupGui;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.AbstractDynamicThreadGroupWrapper;

/**
 * Wrapper for ArrivalsThreadGroup.
 *
 * @see ArrivalsThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("bzm - Arrivals Thread Group")
public class ArrivalsThreadGroupWrapper
    extends AbstractDynamicThreadGroupWrapper<ArrivalsThreadGroup, ArrivalsThreadGroupGui> {

  /** Concurrency Limit. */
  @JmcProperty(value = ArrivalsThreadGroup.CONCURRENCY_LIMIT)
  @Getter
  @Setter
  @JmcNullAllowed
  private String concurrencyLimit;

  @Override
  public Class<?> getGuiClass() {
    return ArrivalsThreadGroupGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ArrivalsThreadGroup.class;
  }

  /** Builder. */
  public abstract static class ArrivalsThreadGroupWrapperBuilder<
          C extends ArrivalsThreadGroupWrapper, B extends ArrivalsThreadGroupWrapperBuilder<C, B>>
      extends AbstractDynamicThreadGroupWrapper.AbstractDynamicThreadGroupWrapperBuilder<
          ArrivalsThreadGroup, ArrivalsThreadGroupGui, C, B> {

    /** Concurrency Limit. */
    public B withConcurrencyLimit(String concurrencyLimit) {
      this.concurrencyLimit = concurrencyLimit;
      return self();
    }

    /** Concurrency Limit. */
    public B withConcurrencyLimit(int concurrencyLimit) {
      return withConcurrencyLimit(String.valueOf(concurrencyLimit));
    }

    /** Concurrency Limit. */
    public B withConcurrencyLimit(Variable concurrencyLimit) {
      return withConcurrencyLimit(String.valueOf(concurrencyLimit.getValue()));
    }
  }
}
