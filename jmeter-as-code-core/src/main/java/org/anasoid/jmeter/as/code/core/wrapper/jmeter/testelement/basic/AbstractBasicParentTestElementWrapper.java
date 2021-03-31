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
 * Date :   27-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.template.JmcTemplate;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Class represent a node with Children. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcChildrenTypes(type = {AssertionWrapper.class, ConfigTestElementWrapper.class})
public abstract class AbstractBasicParentTestElementWrapper<T extends AbstractTestElement>
    extends AbstractBasicTestElementWrapper<T> {

  @Builder.Default @XStreamOmitField private List<TestElementWrapper<?>> childs = new ArrayList<>();

  @Override
  public List<TestElementWrapper<?>> getChilds() {
    return childs;
  }

  /** Builder. */
  public abstract static class AbstractBasicParentTestElementWrapperBuilder<
          T extends AbstractTestElement,
          C extends AbstractBasicParentTestElementWrapper<T>,
          B extends AbstractBasicParentTestElementWrapperBuilder<T, C, B>>
      extends AbstractBasicTestElementWrapper.AbstractBasicTestElementWrapperBuilder<T, C, B> {

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private void withChilds(Collection<TestElementWrapper<?>> childs) {} // NOSONAR

    protected B withChild(TestElementWrapper<?> child) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.add(child);
      this.childs$set = true;

      return self();
    }

    /**
     * Add Assertion.
     *
     * @param assertion assertion.
     */
    public B addAssertion(AssertionWrapper<?> assertion) {
      return addAssertions(Arrays.asList(assertion));
    }

    /**
     * Add Assertion.
     *
     * @param assertion template assertion.
     */
    public <E extends AssertionWrapper<?>> B addAssertion(JmcTemplate<E> assertion) {
      return addAssertion(assertion.generate());
    }

    /** Add Assertions as child in tree. */
    public B addAssertions(List<AssertionWrapper<?>> assertions) {
      for (AssertionWrapper<?> assertion : assertions) {
        withChild(assertion);
      }
      return self();
    }

    /** Add configElement as child in tree. */
    public B addConfig(ConfigTestElementWrapper<?, ?> config) { // NOSONAR
      return addConfigs(Arrays.asList(config));
    }

    /**
     * Add configElement as child in tree.
     *
     * @param config template config.
     */
    public <E extends ConfigTestElementWrapper<?, ?>> B addConfig(JmcTemplate<E> config) {
      return addConfig(config.generate());
    }

    /** Add configElements as child in tree. */
    public B addConfigs(List<ConfigTestElementWrapper<?, ?>> childs) { // NOSONAR
      for (ConfigTestElementWrapper<?, ?> config : childs) {
        withChild(config);
      }
      return self();
    }
  }
}
