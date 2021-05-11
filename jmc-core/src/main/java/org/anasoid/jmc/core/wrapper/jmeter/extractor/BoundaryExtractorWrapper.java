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

package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.extractor.FieldToCheck;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.extractor.BoundaryExtractor;
import org.apache.jmeter.extractor.gui.BoundaryExtractorGui;

/**
 * Wrapper for BoundaryExtractor.
 *
 * @see BoundaryExtractor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Boundary Extractor")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class BoundaryExtractorWrapper
    extends AbstractScopedTestElementWrapper<BoundaryExtractor, BoundaryExtractorGui>
    implements PostProcessorWrapper<BoundaryExtractor>, Validator {

  /**
   * The name of the JMeter variable in which to store the result. Also note that each group is
   * stored as [refname]_g#, where [refname] is the string you entered as the reference name, and #
   * is the group number, where group 0 is the entire match, group 1 is the match from the first set
   * of parentheses, etc.
   */
  @JmcProperty("BoundaryExtractor.refname")
  @Getter
  @Setter
  @JmcMandatory
  private String refName;

  /**
   * Indicates which match to use. The boundaries may match multiple times.
   *
   * <p>Use a value of zero to indicate JMeter should choose a match at random. A positive number N
   * means to select the nth match. Negative numbers are used in conjunction with the ForEach
   * Controller - see below.
   */
  @JmcProperty("BoundaryExtractor.match_number")
  @Getter
  @Setter
  @JmcMandatory
  private String matchNumber;

  /** Left boundary of value to find. */
  @JmcProperty("BoundaryExtractor.lboundary")
  @Getter
  @Setter
  @JmcNullAllowed
  private String leftBoundary;

  /** Right boundary of value to find. */
  @JmcProperty("BoundaryExtractor.rboundary")
  @Getter
  @Setter
  @JmcNullAllowed
  private String rightBoundary;

  /** Use empty default value. */
  @JmcProperty("BoundaryExtractor.default_empty_value")
  @Getter
  @Setter
  @Default
  private boolean defaultEmpty = false;

  /**
   * If the boundaries do not match, then the reference variable will be set to the default value.
   */
  @JmcProperty("BoundaryExtractor.default")
  @Getter
  @Setter
  @JmcNullAllowed
  private String defaultValue;

  /** Field To check. */
  @JmcProperty("BoundaryExtractor.useHeaders")
  @Getter
  @Setter
  @Default
  private FieldToCheck fieldToCheck = FieldToCheck.BODY;

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (defaultEmpty && defaultValue != null) {
      throw new ConversionIllegalStateException(
          "Can't have default value and use default value empty");
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return BoundaryExtractorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return BoundaryExtractor.class;
  }
}
