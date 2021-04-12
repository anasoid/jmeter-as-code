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
 * Date :   12-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.config;

import java.util.List;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.Variable;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for CSVDataSet.
 *
 * @see CSVDataSet
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class CSVDataSetWrapper extends ConfigTestElementWrapper<CSVDataSet, TestBeanGUI> {

  private String filename;
  private String fileEncoding;
  private List<Variable> variableName;
  private String ignoreFirstLine;
  private String delimiter;
  private String recycle;
  private String stopThread;
  private String quotedData;
  private String shareMode;

  @Override
  public Class<TestBeanGUI> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<CSVDataSet> getTestClass() {
    return CSVDataSet.class;
  }
}
