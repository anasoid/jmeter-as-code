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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.protocol.http.control.CacheManager;
import org.apache.jmeter.protocol.http.gui.CacheManagerGui;

/**
 * Wrapper for CacheManager.
 *
 * @see CacheManager
 */
@SuppressWarnings("PMD.RedundantFieldInitializer")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class CacheManagerWrapper extends ConfigTestElementWrapper<CacheManager, CacheManagerGui> {

  @XStreamOmitField private static final long serialVersionUID = -4068882490947271216L;

  @JmcProperty(CacheManager.USE_EXPIRES)
  @Getter
  @Setter
  @Default
  private boolean useExpires = true;

  /** If selected, then the cache is cleared at the start of the thread. */
  @JmcProperty(CacheManager.CLEAR)
  @Getter
  @Setter
  @Default
  private boolean clearEachIteration = false;

  @JmcProperty("CacheManager.controlledByThread")
  @Getter
  @Setter
  @Default
  private boolean controlledByThread = false;

  @JmcProperty(CacheManager.MAX_SIZE)
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("5000")
  private Integer maxSize = 5000;

  @Override
  public Class<?> getTestClass() {
    return CacheManager.class;
  }

  @Override
  public Class<?> getGuiClass() {
    return CacheManagerGui.class;
  }

  /** Builder. */
  public abstract static class CacheManagerWrapperBuilder<
          C extends CacheManagerWrapper, B extends CacheManagerWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          CacheManager, CacheManagerGui, C, B> {

    /** clearEachIteration. */
    public B withClearEachIteration(boolean clearEachIteration) {
      if (this.controlledByThread$value) {
        throw new IllegalStateException(
            "When using controlledByThread, clearEachIteration should not be used");
      }
      this.clearEachIteration$value = clearEachIteration;
      this.clearEachIteration$set = true;
      return self();
    }

    /** controlledByThread. */
    public B withControlledByThread(boolean controlledByThread) {

      if (controlledByThread && this.clearEachIteration$set) {
        throw new IllegalStateException(
            "When using controlledByThread, clearEachIteration should not be used");
      }
      if (controlledByThread) {
        withClearEachIteration(false);
      }
      this.controlledByThread$value = controlledByThread;
      this.controlledByThread$set = true;

      return self();
    }
  }
}
