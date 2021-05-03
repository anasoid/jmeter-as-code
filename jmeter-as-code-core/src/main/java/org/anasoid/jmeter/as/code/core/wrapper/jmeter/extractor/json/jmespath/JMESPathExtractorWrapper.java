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
 * Date :   25-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.extractor.json.jmespath;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.extractor.json.jmespath.JMESPathExtractor;
import org.apache.jmeter.extractor.json.jmespath.gui.JMESPathExtractorGui;

/**
 * Wrapper for JMESPathExtractorWrapper.
 *
 * @see JMESPathExtractorWrapper
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class JMESPathExtractorWrapper
    extends AbstractScopedTestElementWrapper<JMESPathExtractor, JMESPathExtractorGui>
    implements PostProcessorWrapper<JMESPathExtractor> {

  /** Element query in JMESPath query language. Can return the matched result. */
  @JmcProperty("JMESExtractor.jmesPathExpr")
  @JmcMandatory
  @Getter
  @Setter
  private String jmesPathExpr;

  /** Name of created variable. The name of the JMeter variable in which to store the result. */
  @JmcProperty("JMESExtractor.referenceName")
  @JmcMandatory
  @Getter
  @Setter
  private String refName;

  /**
   * If the JMESPath query leads to many results, you can choose which one(s) to extract as
   * Variables:
   *
   * <p>0: means random -1 means extract all results (default value), they will be named as
   * &#x3C;variable name&#x3E;_N (where N goes from 1 to Number of results) X: means extract the Xth
   * result. If this Xth is greater than number of matches, then nothing is returned. Default value
   * will be used
   */
  @JmcProperty("JMESExtractor.matchNumber")
  @Getter
  @Setter
  @JmcNullAllowed
  private String matchNumber;

  /**
   * Default value returned when no match found. It is also returned if the node has no value and
   * the fragment option is not selected.
   */
  @JmcProperty("JMESExtractor.defaultValue")
  @Getter
  @Setter
  private String defaultValue;

  @Override
  public Class<?> getGuiClass() {
    return JMESPathExtractorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return JMESPathExtractor.class;
  }
}
