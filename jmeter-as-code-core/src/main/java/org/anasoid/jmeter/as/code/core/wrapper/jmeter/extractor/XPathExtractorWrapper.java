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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.extractor;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.extractor.XPathExtractor;
import org.apache.jmeter.extractor.gui.XPathExtractorGui;

/**
 * Wrapper for XPathExtractor.
 *
 * @see XPathExtractor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({
  "PMD.RedundantFieldInitializer",
  "PMD.AvoidFieldNameMatchingMethodName",
  "PMD.AvoidUncheckedExceptionsInSignatures"
})
public class XPathExtractorWrapper
    extends AbstractScopedTestElementWrapper<XPathExtractor, XPathExtractorGui>
    implements PostProcessorWrapper<XPathExtractor>, Validator {

  /** The name of the JMeter variable in which to store the result. */
  @JmcProperty("XPathExtractor.refname")
  @Getter
  @Setter
  @JmcMandatory
  private String refName;

  /** Element query in XPath language. Can return more than one match. */
  @JmcProperty("XPathExtractor.xpathQuery")
  @Getter
  @Setter
  @JmcMandatory
  private String xpathQuery;

  /**
   * If the XPath Path query leads to many results, you can choose which one(s) to extract as
   * Variables:
   *
   * <p>0: means random -1 means extract all results (default value), they will be named as
   * &#x3C;variable name&#x3E;_N (where N goes from 1 to Number of results) X: means extract the Xth
   * result. If this Xth is greater than number of matches, then nothing is returned. Default value
   * will be used
   */
  @JmcProperty("XPathExtractor.matchNumber")
  @Getter
  @Setter
  @JmcMandatory
  @Default
  private String matchNumber = "-1";

  /**
   * Default value returned when no match found. It is also returned if the node has no value and
   * the fragment option is not selected.
   */
  @JmcProperty("XPathExtractor.default")
  @Getter
  @Setter
  @JmcNullAllowed
  private String defaultValue;

  /**
   * If selected, the fragment will be returned rather than the text content. For example //title
   * would return "<title>Apache JMeter</title>" rather than "Apache JMeter". In this case,
   * //title/text() would return "Apache JMeter".
   */
  @JmcProperty("XPathExtractor.fragment")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean fragment = false;

  /** Check the document against its schema. (If Tidy is not selected). */
  @JmcProperty("XPathExtractor.validate")
  @Getter
  @Setter
  @Default
  private boolean validate = false;

  /**
   * If checked, then the XML parser will use namespace resolution.(see note below on NAMESPACES)
   * Note that currently only namespaces declared on the root element will be recognised. See below
   * for user-definition of additional workspace names. (If Tidy is not selected).
   */
  @JmcProperty("XPathExtractor.namespace")
  @Getter
  @Setter
  @Default
  private boolean namespace = false;

  /** Ignore Element Whitespace. (If Tidy is not selected). */
  @JmcProperty("XPathExtractor.whitespace")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean whitespace = false;

  /** If selected, external DTDs are fetched. (If Tidy is not selected). */
  @JmcProperty("XPathExtractor.download_dtds")
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
  @JmcProperty("XPathExtractor.tolerant")
  @Getter
  @Setter
  @Default
  private boolean useTidy = false;

  /** Sets the Tidy Quiet flag. */
  @JmcProperty("XPathExtractor.quiet")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("true")
  private boolean tidyQuiet = true;

  /** If a Tidy error occurs, then set the Assertion accordingly. */
  @JmcProperty("XPathExtractor.report_errors")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean tidyReportErrors = false;

  /** Sets the Tidy showWarnings option. */
  @JmcProperty("XPathExtractor.show_warnings")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean tidyShowWarnings = false;

  @Override
  public Class<XPathExtractorGui> getGuiClass() {
    return XPathExtractorGui.class;
  }

  @Override
  public Class<XPathExtractor> getTestClass() {
    return XPathExtractor.class;
  }

  @Override
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
}
