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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.control.RandomController;
import org.apache.jmeter.control.gui.RandomControlGui;

/**
 * Wrapper for RandomController.
 *
 * @see RandomController
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class RandomControllerWrapper
    extends GenericControllerWrapper<RandomController, RandomControlGui> {

  /**
   * ignore sub-controller blocks : If checked, the interleave controller will treat sub-controllers
   * like single request elements and only allow one request per controller at a time.
   */
  @XStreamOmitField @Getter @Setter @Default private boolean ignoreSubController = false;

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
    return RandomControlGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RandomController.class;
  }
}
