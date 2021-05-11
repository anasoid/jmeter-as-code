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
 * Date :   28-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.extractor.FieldToCheck;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.extractor.RegexExtractor;
import org.apache.jmeter.extractor.gui.RegexExtractorGui;

/**
 * Wrapper for RegexExtractor.
 *
 * @see RegexExtractor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Regular Expression Extractor")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class RegexExtractorWrapper
    extends AbstractScopedTestElementWrapper<RegexExtractor, RegexExtractorGui>
    implements PostProcessorWrapper<RegexExtractor>, Validator {

  /**
   * The name of the JMeter variable in which to store the result. Also note that each group is
   * stored as [refname]_g#, where [refname] is the string you entered as the reference name, and #
   * is the group number, where group 0 is the entire match, group 1 is the match from the first set
   * of parentheses, etc.
   */
  @JmcProperty("RegexExtractor.refname")
  @Getter
  @Setter
  @JmcMandatory
  private String refName;

  /**
   * The regular expression used to parse the response data. This must contain at least one set of
   * parentheses "()" to capture a portion of the string, unless using the group $0$. Do not enclose
   * the expression in / / - unless of course you want to match these characters as well.
   */
  @JmcProperty("RegexExtractor.regex")
  @Getter
  @Setter
  @JmcMandatory
  private String regex;

  /**
   * Indicates which match to use. The regular expression may match multiple times.
   *
   * <p>Use a value of zero to indicate JMeter should choose a match at random. A positive number N
   * means to select the nth match. Negative numbers are used in conjunction with the ForEach
   * Controller - see below.
   */
  @JmcProperty("RegexExtractor.match_number")
  @Getter
  @Setter
  @JmcMandatory
  private String matchNumber;

  /**
   * If true and Default Value is empty, then JMeter will set the variable to empty string instead
   * of not setting it.
   */
  @JmcProperty("RegexExtractor.default_empty_value")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean defaultEmpty = false;

  /**
   * If the regular expression does not match, then the reference variable will be set to the
   * default value. This is particularly useful for debugging tests. If no default is provided, then
   * it is difficult to tell whether the regular expression did not match, or the RE element was not
   * processed or maybe the wrong variable is being used.
   *
   * <p>However, if you have several test elements that set the same variable, you may wish to leave
   * the variable unchanged if the expression does not match. In this case, remove the default value
   * once debugging is complete.
   */
  @JmcProperty("RegexExtractor.default")
  @Getter
  @Setter
  @JmcNullAllowed
  private String defaultValue;

  /**
   * The template used to create a string from the matches found. This is an arbitrary string with
   * special elements to refer to groups within the regular expression. The syntax to refer to a
   * group is: '$1$' to refer to group 1, '$2$' to refer to group 2, etc. $0$ refers to whatever the
   * entire expression matches.
   */
  @JmcProperty("RegexExtractor.template")
  @Getter
  @Setter
  @JmcMandatory
  private String template;

  /** Field To check. */
  @JmcProperty("RegexExtractor.useHeaders")
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
    return RegexExtractorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RegexExtractor.class;
  }
}
