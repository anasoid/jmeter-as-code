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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;

/**
 * Wrapper for HeaderManager.
 *
 * @see HeaderManager
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class HeaderManagerWrapper extends ConfigTestElementWrapper<HeaderManager, HeaderPanel> {

  @XStreamOmitField private static final long serialVersionUID = 8482403319301067878L;

  @Override
  public Class<HeaderPanel> getGuiClass() {
    return HeaderPanel.class;
  }

  @Override
  public Class<HeaderManager> getTestClass() {
    return HeaderManager.class;
  }

  @JmcCollection(value = "HeaderManager.headers")
  @Getter
  @JmcEmptyAllowed
  @Default
  private final List<HeaderWrapper> headers = new ArrayList<>();

  /** Builder. */
  public abstract static class HeaderManagerWrapperBuilder<
          C extends HeaderManagerWrapper, B extends HeaderManagerWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          HeaderManager, HeaderPanel, C, B> {

    protected B withHeaders(List<HeaderWrapper> headers) {
      this.headers$value = headers;
      this.headers$set = true;
      return self();
    }

    /** Add header List. */
    public B addHeaders(List<HeaderWrapper> headers) {
      this.headers$set = true;
      if (this.headers$value == null) {
        this.headers$value = new ArrayList<>();
      }
      this.headers$value.addAll(headers);
      return self();
    }

    /** Add header. */
    public B addHeader(HeaderWrapper headers) {
      return this.addHeaders(Arrays.asList(headers));
    }

    /**
     * Add header .
     *
     * @param name name.
     * @param value value.
     */
    public B addHeader(String name, String value) {
      return this.addHeader(HeaderWrapper.builder().withName(name).withValue(value).build());
    }
  }
}
