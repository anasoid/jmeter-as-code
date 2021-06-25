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
 * Date :   20-Jun-2021
 */

package org.anasoid.jmc.plugins.config.gui;

import java.awt.Color;
import org.apache.jmeter.config.gui.ArgumentsPanel;

/**
 * Read Only ArgumentsPanel.
 *
 * @see ArgumentsPanel
 */
public class ReadOnlyArgumentsPanel extends ArgumentsPanel {

  /**
   * Create a new ReadOnly ArgumentsPanel.
   *
   * @param label text for label
   */
  public ReadOnlyArgumentsPanel(String label) {
    this(label, true);
  }

  /**
   * Create a new ReadOnlyArgumentsPanel.
   *
   * @param label text for label
   * @param disable readonly or not ?.
   */
  public ReadOnlyArgumentsPanel(String label, boolean disable) {
    super(label, null, false, false, null, disable);
    if (disable) {
      this.setEnabled(false);
      this.getTable().setBackground(Color.LIGHT_GRAY);
      this.getTable().setEnabled(false);
      this.stopTableEditing();
    }
  }
}
