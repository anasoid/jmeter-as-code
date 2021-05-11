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

package org.anasoid.jmc.core.wrapper.jmeter.assertions;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.assertions.AssertionField;
import org.anasoid.jmc.core.wrapper.jmc.assertions.MatchingRule;
import org.anasoid.jmc.core.wrapper.jmeter.JmeterConstants.JmeterProperty;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedAssertionWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.gui.AssertionGui;
import org.apache.jmeter.testelement.property.CollectionProperty;

/**
 * Wrapper for ResponseAssertion.
 *
 * @see ResponseAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class ResponseAssertionWrapper
    extends AbstractScopedAssertionWrapper<ResponseAssertion, AssertionGui> {

  @XStreamOmitField private static final long serialVersionUID = -6980208040607992323L;

  @XStreamOmitField private static final int NOT = 1 << 2;
  @XStreamOmitField private static final int OR = 1 << 5;

  @JmcProperty("Assertion.custom_message")
  @Getter
  @Default
  private String customMessage = "";

  @JmcProperty("Assertion.assume_success")
  @Getter
  @Default
  private Boolean ignoreStatus = false;

  @JmcProperty("Assertion.test_field")
  @Getter
  @Default
  private AssertionField fieldToTest = AssertionField.RESPONSE_DATA;

  @Getter @XStreamOmitField @Default private MatchingRule matchingRule = MatchingRule.SUBSTRING;

  @Getter @XStreamOmitField @Default private boolean not = false; // NOSONAR

  @Getter @XStreamOmitField @Default private boolean or = false; // NOSONAR

  @Getter @Default @XStreamOmitField private List<String> patterns = new ArrayList<>();

  // @JmcProperty("Assertion.test_strings")
  @JmcMethodAlias(JmeterProperty.COLLECTION_PROP)
  protected CollectionProperty getPatternProperties() {
    return new CollectionProperty("Asserion.test_strings", patterns);
  }

  @JmcProperty("Assertion.test_type")
  protected Integer testType() {
    int result = matchingRule.value;
    if (isNot()) {
      result = result | NOT;
      if (isOr()) {
        result = result | OR;
      } else {
        result = result & ~OR;
      }
    } else {
      result = result & ~NOT;
      if (isOr()) {
        result = result | OR;
      } else {
        result = result & ~OR;
      }
    }
    return result;
  }

  @Override
  public Class<?> getGuiClass() {
    return AssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ResponseAssertion.class;
  }

  /** Builder. */
  public abstract static class ResponseAssertionWrapperBuilder<
          C extends ResponseAssertionWrapper, B extends ResponseAssertionWrapperBuilder<C, B>>
      extends AbstractScopedAssertionWrapper.AbstractScopedAssertionWrapperBuilder<
          ResponseAssertion, AssertionGui, C, B> {

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void withPatterns(List<String> patterns) {} // NOSONAR

    /** Add list of patterns. */
    public B addPatterns(List<String> patterns) {
      if (!this.patterns$set) {
        this.patterns$value = new ArrayList<>();
        this.patterns$set = true;
      }
      this.patterns$value.addAll(patterns);
      return self();
    }

    /** Add pattern to list of patterns. */
    public B addPattern(String pattern) {
      return addPatterns(Arrays.asList(pattern));
    }
  }
}
