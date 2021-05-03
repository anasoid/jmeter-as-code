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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.assertions.XPath2Assertion;
import org.apache.jmeter.assertions.gui.XPath2AssertionGui;

/**
 * Wrapper for XPath2Assertion.
 *
 * @see XPath2Assertion
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class XPath2AssertionWrapper
    extends AbstractScopedTestElementWrapper<XPath2Assertion, XPath2AssertionGui>
    implements AssertionWrapper<XPath2Assertion> {

  /** XPath to match in the document. */
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

  /** List of namespace aliases prefix=full namespace. */
  @JmcProperty("XPath.namespaces")
  @XStreamOmitField
  @Getter
  @Setter
  private List<String> namespaces;

  @JmcProperty("XPath.namespaces")
  protected String namespacesProp() {

    if (CollectionUtils.isEmpty(namespaces)) {
      return "";
    }
    return String.join("\n", namespaces);
  }

  /** Builder. */
  public abstract static class XPath2AssertionWrapperBuilder<
          C extends XPath2AssertionWrapper, B extends XPath2AssertionWrapperBuilder<C, B>>
      extends AbstractScopedTestElementWrapper.AbstractScopedTestElementWrapperBuilder<
          XPath2Assertion, XPath2AssertionGui, C, B> {

    protected B withNamespaces(List<String> namespaces) {
      this.namespaces = namespaces;
      return self();
    }

    /** Add namespace . */
    public B addNamespaces(List<String> namespaces) {
      if (this.namespaces == null) {
        withNamespaces(new ArrayList<>());
      }
      this.namespaces.addAll(namespaces);
      return self();
    }

    /** Add namespace . */
    public B addNamespace(String value) {
      return addNamespaces(Arrays.asList(value));
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return XPath2AssertionGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return XPath2Assertion.class;
  }
}
