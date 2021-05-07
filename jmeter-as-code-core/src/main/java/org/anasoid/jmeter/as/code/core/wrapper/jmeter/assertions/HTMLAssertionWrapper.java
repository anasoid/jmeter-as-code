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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.config.JmcConfig;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.assertions.HTMLAssertion;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.HTMLAssertionGui;

/**
 * Wrapper for ResponseAssertion.
 *
 * @see ResponseAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public class HTMLAssertionWrapper extends AbstractBasicChildTestElementWrapper<HTMLAssertion>
    implements JMeterGUIWrapper<HTMLAssertionGui>, AssertionWrapper<HTMLAssertion>, Validator {

  /** Number of errors allowed before classing the response as failed. */
  @JmcProperty(value = "html_assertion_error_threshold", type = Long.class)
  @Getter
  @Setter
  @Default
  private Integer errorThreshold = 0;

  /** Number of errors allowed before classing the response as failed. */
  @JmcProperty(value = "html_assertion_warning_threshold", type = Long.class)
  @Getter
  @Setter
  @Default
  private Integer warningThreshold = 0;

  /** Number of warnings allowed before classing the response as failed. */
  @JmcProperty("html_assertion_format")
  @Getter
  @Setter
  @Default
  private Format format = Format.HTML;

  /** Number of warnings allowed before classing the response as failed. */
  @JmcProperty("html_assertion_errorsonly")
  @Getter
  @Setter
  @Default
  private boolean errorOnly = false;

  @JmcProperty("html_assertion_doctype")
  @Getter
  @Setter
  @Default
  private Doctype doctype = Doctype.OMIT;

  @XStreamOmitField @Getter @Setter private String filename;

  @JmcProperty("html_assertion_filename")
  protected String getFilePath() {

    if (filename == null) {
      return "";
    }
    return JmcConfig.getResultRootFolder() + filename;
  }

  @Override
  public Class<?> getGuiClass() {
    return HTMLAssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return HTMLAssertion.class;
  }

  @Override
  public void validate() throws ConversionIllegalStateException {

    if (errorOnly && (warningThreshold != 0)) {

      throw new ConversionIllegalStateException("warningThreshold can't be used with errorOnly");
    }
  }

  /** enum for Format. */
  public enum Format {
    HTML(0L),
    XHTML(1L),
    XML(2L);

    public final Long value;

    private Format(Long value) {
      this.value = value;
    }

    public Long value() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** enum for Field To Test. */
  public enum Doctype {
    OMIT("omit"),
    AUTO("auto"),
    STRICT("strict"),
    LOOSE("loose");

    public final String value;

    private Doctype(String value) {
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
