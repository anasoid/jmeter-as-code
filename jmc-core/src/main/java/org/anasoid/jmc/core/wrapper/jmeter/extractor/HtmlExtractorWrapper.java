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
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.extractor.HtmlExtractor;
import org.apache.jmeter.extractor.gui.HtmlExtractorGui;

/**
 * Wrapper for HtmlExtractor. AKA : CSS Selector Extractor.
 *
 * @see HtmlExtractor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("CSS Selector Extractor")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class HtmlExtractorWrapper
    extends AbstractScopedTestElementWrapper<HtmlExtractor, HtmlExtractorGui>
    implements PostProcessorWrapper<HtmlExtractor>, Validator {

  @JmcProperty("HtmlExtractor.refname")
  @Getter
  @Setter
  @JmcMandatory
  private String refName;

  /**
   * The CSS/JQuery selector used to select nodes from the response data. Selector, selectors
   * combination and pseudo-selectors are supported.
   */
  @JmcProperty("HtmlExtractor.expr")
  @Getter
  @Setter
  @JmcMandatory
  private String queryExpression;

  /**
   * Name of attribute (as per HTML syntax) to extract from nodes that matched the selector. If
   * empty, then the combined text of this element and all its children will be returned.
   */
  @JmcProperty("HtmlExtractor.attribute")
  @Getter
  @Setter
  @JmcNullAllowed
  private String attribute;

  /**
   * Indicates which match to use. The CSS/JQuery selector may match multiple times.
   *
   * <p>Use a value of zero to indicate JMeter should choose a match at random. A positive number N
   * means to select the nth match. Negative numbers are used in conjunction with the ForEach
   * Controller - see below.
   */
  @JmcProperty("HtmlExtractor.match_number")
  @Getter
  @Setter
  @JmcMandatory
  private String matchNumber;
  /**
   * f the checkbox is checked and Default Value is empty, then JMeter will set the variable to
   * empty string instead of not setting it. Thus when you will for example use ${var} (if Reference
   * Name is var) in your Test Plan, if the extracted value is not found then ${var} will be equal
   * to empty string instead of containing ${var} which may be useful if extracted value is
   * optional.
   */
  @JmcProperty("HtmlExtractor.default_empty_value")
  @Getter
  @Setter
  @Default
  private boolean defaultEmpty = false;

  /**
   * If the expression does not match, then the reference variable will be set to the default value.
   * This is particularly useful for debugging tests. If no default is provided, then it is
   * difficult to tell whether the expression did not match, or the CSS/JQuery element was not
   * processed or maybe the wrong variable is being used.
   *
   * <p>However, if you have several test elements that set the same variable, you may wish to leave
   * the variable unchanged if the expression does not match. In this case, remove the default value
   * once debugging is complete. .
   */
  @JmcProperty("HtmlExtractor.default")
  @Getter
  @Setter
  @JmcNullAllowed
  private String defaultValue;

  @JmcProperty("HtmlExtractor.extractor_impl")
  @Getter
  @Setter
  @JmcNullAllowed
  private ExtractorImpl extractorImplementation;

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
    return HtmlExtractorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return HtmlExtractor.class;
  }

  /** enum for extractor_impl. */
  public enum ExtractorImpl {
    JSOUP("JSOUP"),
    JODD("JODD");

    private final String value;

    ExtractorImpl(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }

    @Override
    public String toString() {
      return value;
    }
  }
}
