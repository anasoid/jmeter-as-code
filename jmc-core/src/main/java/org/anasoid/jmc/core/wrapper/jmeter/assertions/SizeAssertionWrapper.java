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

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedAssertionWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.assertions.ResponseAssertion;
import org.apache.jmeter.assertions.SizeAssertion;
import org.apache.jmeter.assertions.gui.SizeAssertionGui;

/**
 * Wrapper for ResponseAssertion.
 *
 * @see ResponseAssertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Size Assertion")
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class SizeAssertionWrapper
    extends AbstractScopedAssertionWrapper<SizeAssertion, SizeAssertionGui> {

  /**
   * The number of bytes to use in testing the size of the response (or value of the JMeter
   * variable)..
   */
  @JmcProperty("SizeAssertion.size")
  @Getter
  @Setter
  @JmcMandatory
  private String size;

  /**
   * Whether to test that the response is equal to, greater than, less than, or not equal to, the
   * number of bytes specified.
   */
  @JmcProperty("SizeAssertion.operator")
  @Getter
  @Setter
  @Default
  private Operator comparisonType = Operator.EQUAL;

  /** Field to test. */
  @JmcProperty("Assertion.test_field")
  @Getter
  @Setter
  @Default
  private FieldtoTest fieldToTest = FieldtoTest.FULL_RESPONSE;

  @Override
  public Class<?> getGuiClass() {
    return SizeAssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return SizeAssertion.class;
  }

  /** enum for Operator comparison type. */
  public enum Operator {
    EQUAL(1),
    /** = . */
    NOT_EQUAL(2),
    /** &gt; . */
    GREATER_THAN(3),
    /** &lt; . */
    LESS_THAN(4),
    /** &gt;= . */
    GREATER_THAN_EQUAL(5),
    /** &lt;= . */
    LESS_THAN_EQUAL(6);

    private final Integer value;

    Operator(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
  }

  /** enum for Field To Test. */
  public enum FieldtoTest {
    FULL_RESPONSE("SizeAssertion.response_network_size"),
    RESPONSE_HEADERS("SizeAssertion.response_headers"),
    RESPONSE_BODY("SizeAssertion.response_data"),
    RESPONSE_CODE("SizeAssertion.response_code"),
    RESPONSE_MESSAGE("SizeAssertion.response_message");

    private final String value;

    FieldtoTest(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  /** Builder. */
  public abstract static class SizeAssertionWrapperBuilder<
          C extends SizeAssertionWrapper, B extends SizeAssertionWrapperBuilder<C, B>>
      extends AbstractScopedAssertionWrapper.AbstractScopedAssertionWrapperBuilder<
          SizeAssertion, SizeAssertionGui, C, B> {

    public B withSize(String size) {
      this.size = size;
      return self();
    }

    public B withSize(Integer size) {
      return withSize(String.valueOf(size));
    }

    public B withSize(Variable size) {
      return withSize(String.valueOf(size.getValue()));
    }
  }
}
