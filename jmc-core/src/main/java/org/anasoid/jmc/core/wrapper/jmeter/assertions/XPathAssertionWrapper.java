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
 * Date :   30-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.assertions.XPathAssertion;
import org.apache.jmeter.assertions.gui.XPathAssertionGui;

/**
 * Wrapper for XPathAssertion.
 *
 * @see XPathAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidFieldNameMatchingMethodName"})
public class XPathAssertionWrapper
    extends AbstractScopedTestElementWrapper<XPathAssertion, XPathAssertionGui>
    implements AssertionWrapper<XPathAssertion>, Validator {

  /** Element query in XPath language. Can return more than one match. */
  @JmcProperty("XPath.xpath")
  @Getter
  @Setter
  @JmcMandatory
  private String xpath;

  /** Will fail if xpath expression returns true or matches, succeed otherwise. */
  @JmcProperty("XPath.negate")
  @Getter
  @Setter
  @Default
  private boolean negate = false;

  /** Check the document against its schema. (If Tidy is not selected). */
  @JmcProperty("XPath.validate")
  @Getter
  @Setter
  @Default
  private boolean validate = false;

  /**
   * If checked, then the XML parser will use namespace resolution.(see note below on NAMESPACES)
   * Note that currently only namespaces declared on the root element will be recognised. See below
   * for user-definition of additional workspace names. (If Tidy is not selected).
   */
  @JmcProperty("XPath.namespace")
  @Getter
  @Setter
  @Default
  private boolean namespace = false;

  /** Ignore Element Whitespace. (If Tidy is not selected). */
  @JmcProperty("XPath.whitespace")
  @Getter
  @Setter
  @Default
  private boolean whitespace = false;

  /** If selected, external DTDs are fetched. (If Tidy is not selected). */
  @JmcProperty("XPath.download_dtds")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean downloadDtds = false;

  /**
   * If checked use Tidy to parse HTML response into XHTML.
   *
   * <p>"Use Tidy" should be checked on for HTML response. Such response is converted to valid XHTML
   * (XML compatible HTML) using Tidy "Use Tidy" should be unchecked for both XHTML or XML response
   * (for example RSS)
   */
  @JmcProperty("XPath.tolerant")
  @Getter
  @Setter
  @Default
  private boolean useTidy = false;

  /** Sets the Tidy Quiet flag. */
  @JmcProperty("XPath.quiet")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("true")
  private boolean tidyQuiet = true;

  /** If a Tidy error occurs, then set the Assertion accordingly. */
  @JmcProperty("XPath.report_errors")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean tidyReportErrors = false;

  /** Sets the Tidy showWarnings option. */
  @JmcProperty("XPath.show_warnings")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean tidyShowWarnings = false;

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (useTidy) {
      if (validate || namespace || downloadDtds || whitespace) {
        throw new ConversionIllegalStateException(
            "validate , namespace , downloadDtds , whitespace can't be used with useTidy");
      }
    } else {
      if (!tidyQuiet || tidyReportErrors || tidyShowWarnings) {
        throw new ConversionIllegalStateException(
            "tidyQuiet , tidyReportErrors , tidyShowWarnings can't be used without useTidy");
      }
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return XPathAssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return XPathAssertion.class;
  }
}
