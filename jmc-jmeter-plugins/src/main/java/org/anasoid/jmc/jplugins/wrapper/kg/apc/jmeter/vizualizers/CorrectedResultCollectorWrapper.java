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
 * Date :   06-Jul-2021
 */

package org.anasoid.jmc.jplugins.wrapper.kg.apc.jmeter.vizualizers;

import kg.apc.jmeter.vizualizers.CorrectedResultCollector;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmeter.reporters.ResultCollectorWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.visualizers.gui.AbstractListenerGui;

/**
 * Wrapper for CorrectedResultCollector.
 *
 * @see CorrectedResultCollector
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public abstract class CorrectedResultCollectorWrapper<G extends AbstractListenerGui>
    extends ResultCollectorWrapper<CorrectedResultCollector, G> {

  /** include Labels. */
  @JmcProperty(value = CorrectedResultCollector.INCLUDE_SAMPLE_LABELS)
  @Getter
  @Setter
  @JmcNullAllowed
  private String includeLabels;

  /** exclude Labels. */
  @JmcProperty(value = CorrectedResultCollector.EXCLUDE_SAMPLE_LABELS)
  @Getter
  @Setter
  @JmcNullAllowed
  private String excludeLabels;

  /** Use exclude labels as regex. */
  @JmcProperty(value = CorrectedResultCollector.EXCLUDE_REGEX_CHECKBOX_STATE)
  @Getter
  @Setter
  @Default
  private boolean excludeRegex = false;

  /** Use include labels as regex. */
  @JmcProperty(value = CorrectedResultCollector.INCLUDE_REGEX_CHECKBOX_STATE)
  @Getter
  @Setter
  @Default
  private boolean includeRegex = false;

  /** Start offset (sec). */
  @JmcProperty(value = CorrectedResultCollector.START_OFFSET)
  @Getter
  @Setter
  @JmcNullAllowed
  private String startOffset;

  /** End offset (sec). */
  @JmcProperty(value = CorrectedResultCollector.END_OFFSET)
  @Getter
  @Setter
  @JmcNullAllowed
  private String endOffset;

  @Override
  public Class<?> getTestClass() {
    return CorrectedResultCollector.class;
  }

  /** builder. */
  public abstract static class CorrectedResultCollectorWrapperBuilder<
          G extends AbstractListenerGui,
          C extends CorrectedResultCollectorWrapper<G>,
          B extends CorrectedResultCollectorWrapperBuilder<G, C, B>>
      extends ResultCollectorWrapper.ResultCollectorWrapperBuilder<
          CorrectedResultCollector, G, C, B> {

    /** Start offset (sec). */
    public B withStartOffset(String startOffset) {
      this.startOffset = startOffset;
      return self();
    }

    /** Start offset (sec). */
    public B withStartOffset(Integer startOffset) {
      return withStartOffset(String.valueOf(startOffset));
    }

    /** Start offset (sec). */
    public B withStartOffset(Variable startOffset) {
      return withStartOffset(String.valueOf(startOffset.getValue()));
    }

    /** End offset (sec). */
    public B withEndOffset(String endOffset) {
      this.endOffset = endOffset;
      return self();
    }

    /** End offset (sec). */
    public B withEndOffset(Integer endOffset) {
      return withEndOffset(String.valueOf(endOffset));
    }

    /** End offset (sec). */
    public B withEndOffset(Variable endOffset) {
      return withEndOffset(String.valueOf(endOffset.getValue()));
    }
  }
}
