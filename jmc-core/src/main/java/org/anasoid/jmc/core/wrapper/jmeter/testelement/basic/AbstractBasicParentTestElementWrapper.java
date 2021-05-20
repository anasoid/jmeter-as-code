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

package org.anasoid.jmc.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.SampleListenerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.timers.TimerWrapper;
import org.anasoid.jmc.core.wrapper.template.JmcTemplate;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Class represent a node with Children. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcChildrenTypes(
    type = {
      AssertionWrapper.class,
      ConfigElementWrapper.class,
      PreProcessorWrapper.class,
      PostProcessorWrapper.class,
      TimerWrapper.class,
      SampleListenerWrapper.class
    })
public abstract class AbstractBasicParentTestElementWrapper<T extends AbstractTestElement>
    extends AbstractBasicTestElementWrapper<T> {

  @Builder.Default @XStreamOmitField
  private final List<TestElementWrapper<?>> childs = new ArrayList<>();

  @Override
  public List<TestElementWrapper<?>> getChilds() {
    return childs;
  }

  /** Builder. */
  @SuppressWarnings({"PMD.TooManyMethods"})
  public abstract static class AbstractBasicParentTestElementWrapperBuilder<
          T extends AbstractTestElement,
          C extends AbstractBasicParentTestElementWrapper<T>,
          B extends AbstractBasicParentTestElementWrapperBuilder<T, C, B>>
      extends AbstractBasicTestElementWrapper.AbstractBasicTestElementWrapperBuilder<T, C, B> {

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    protected <I extends TestElementWrapper<?>> B withChilds(Collection<I> childs) {
      for (TestElementWrapper<?> child : childs) {
        withChild(child);
      }
      return self();
    }

    protected B withChild(TestElementWrapper<?> child) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.add(child);
      this.childs$set = true;

      return self();
    }

    // ASSERTION .

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
    public B addAssertions(List<AssertionWrapper<?>> childs) {
      return withChilds(childs);
    }

    // Config .

    /** Add configElement as child in tree. */
    public B addConfig(ConfigElementWrapper<?> config) { // NOSONAR
      return addConfigs(Arrays.asList(config));
    }

    /**
     * Add configElement as child in tree.
     *
     * @param config template config.
     */
    public <E extends ConfigElementWrapper<?>> B addConfig(JmcTemplate<E> config) {
      return addConfig(config.generate());
    }

    /** Add configElements as child in tree. */
    public B addConfigs(List<ConfigElementWrapper<?>> childs) { // NOSONAR
      return withChilds(childs);
    }

    // PreProcessor .

    /** Add PreProcessor as child in tree. */
    public B addPreProcessor(PreProcessorWrapper<?> preProcessor) { // NOSONAR
      return addPreProcessors(Arrays.asList(preProcessor));
    }

    /**
     * Add preProcessor as child in tree.
     *
     * @param preProcessor template preProcessor.
     */
    public <E extends PreProcessorWrapper<?>> B addPreProcessor(JmcTemplate<E> preProcessor) {
      return addPreProcessor(preProcessor.generate());
    }

    /** Add preProcessor as child in tree. */
    public B addPreProcessors(List<PreProcessorWrapper<?>> childs) { // NOSONAR
      return withChilds(childs);
    }

    // PostProcessor .

    /** Add postProcessor as child in tree. */
    public B addPostProcessor(PostProcessorWrapper<?> postProcessor) { // NOSONAR
      return addPostProcessors(Arrays.asList(postProcessor));
    }

    /**
     * Add postProcessor as child in tree.
     *
     * @param postProcessor template postProcessor.
     */
    public <E extends PostProcessorWrapper<?>> B addPostProcessor(JmcTemplate<E> postProcessor) {
      return addPostProcessor(postProcessor.generate());
    }

    /** Add postProcessor as child in tree. */
    public B addPostProcessors(List<PostProcessorWrapper<?>> childs) { // NOSONAR
      return withChilds(childs);
    }

    // Timer

    /** Add timer as child in tree. */
    public B addTimer(TimerWrapper<?> timer) { // NOSONAR
      return addTimers(Arrays.asList(timer));
    }

    /**
     * Add timer as child in tree.
     *
     * @param timer template timer.
     */
    public <E extends TimerWrapper<?>> B addTimer(JmcTemplate<E> timer) {
      return addTimer(timer.generate());
    }

    /** Add timer as child in tree. */
    public B addTimers(List<TimerWrapper<?>> childs) { // NOSONAR
      return withChilds(childs);
    }

    // Listener

    /** Add listener as child in tree. */
    public B addListener(SampleListenerWrapper<?> listener) { // NOSONAR
      return addListener(Arrays.asList(listener));
    }

    /**
     * Add listener as child in tree.
     *
     * @param listener template listener.
     */
    public <E extends SampleListenerWrapper<?>> B addListener(JmcTemplate<E> listener) {
      return addListener(listener.generate());
    }

    /** Add listener as child in tree. */
    public B addListener(List<SampleListenerWrapper<?>> childs) { // NOSONAR
      return withChilds(childs);
    }
  }
}
