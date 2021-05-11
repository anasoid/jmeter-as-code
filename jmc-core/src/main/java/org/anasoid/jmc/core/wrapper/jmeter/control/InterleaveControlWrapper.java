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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.InterleaveControl;
import org.apache.jmeter.control.gui.InterleaveControlGui;

/**
 * Wrapper for InterleaveControl.
 *
 * @see InterleaveControl
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Interleave Controller")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class InterleaveControlWrapper
    extends GenericControllerWrapper<InterleaveControl, InterleaveControlGui> {

  /**
   * ignore sub-controller blocks : If checked, the interleave controller will treat sub-controllers
   * like single request elements and only allow one request per controller at a time.
   */
  @XStreamOmitField @Getter @Setter @Default private boolean ignoreSubController = false;

  /**
   * Interleave across threads. If checked, the interleave controller will alternate among each of
   * its children controllers for each loop iteration but across all threads, for example in a
   * configuration with 4 threads and 3 child controllers, on first iteration thread 1 will run
   * first child, thread 2 second child, thread 3 third child, thread 4 first child, on next
   * iteration each thread will run the following child controller
   */
  @JmcProperty("InterleaveControl.accrossThreads")
  @Getter
  @Setter
  @Default
  private boolean accrossThreads = false;

  /** ignoreSubContoller is perssisted as int. */
  @JmcProperty("InterleaveControl.style")
  protected Integer style() {
    if (ignoreSubController) {
      return 0;
    } else {
      return 1;
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return InterleaveControlGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return InterleaveControl.class;
  }
}
