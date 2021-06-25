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

package org.anasoid.jmc.plugins.wrapper.java.timers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.timers.TimerWrapper;
import org.anasoid.jmc.plugins.component.java.timers.JavaTimer;
import org.anasoid.jmc.plugins.component.java.timers.JavaTimerExecutor;
import org.anasoid.jmc.plugins.component.java.timers.JavaTimerGui;
import org.anasoid.jmc.plugins.wrapper.java.AbstractJavaTestElementWrapper;

/** Base class for JavaTimer Wrapper. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractJavaTimerWrapper extends AbstractJavaTestElementWrapper<JavaTimer>
    implements TimerWrapper<JavaTimer>, JavaTimerExecutor {

  @Override
  public Class<?> getTestClass() {
    return JavaTimer.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return JavaTimerGui.class;
  }
}
