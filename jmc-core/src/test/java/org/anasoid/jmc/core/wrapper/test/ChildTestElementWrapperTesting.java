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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.test;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.threads.OnSampleError;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.GenericController;
import org.apache.jmeter.control.gui.AbstractControllerGui;

/** Wrapper for testing. abstract child Element. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.AbstractClassWithoutAnyMethod")
public abstract class ChildTestElementWrapperTesting<
        T extends GenericController, G extends AbstractControllerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {

  @JmcProperty("child.onSampleError")
  @Builder.Default
  @Getter
  private final OnSampleError onSampleError = OnSampleError.ON_SAMPLE_ERROR_CONTINUE;

  @Builder.Default @Getter
  private final OnSampleError onSampleError2 = OnSampleError.ON_SAMPLE_ERROR_STOPTEST;

  @JmcProperty("child.field")
  @Builder.Default
  @Getter
  String field = "super";

  @Builder.Default
  @Getter
  @XStreamAlias("fieldChild")
  String fieldChild = "super";
}
