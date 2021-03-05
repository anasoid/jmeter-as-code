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
import org.anasoid.jmeter.as.code.core.wrapper.jmc.http.client.config.CookiePolicy;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcCollection;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.gui.CookiePanel;

/**
 * Wrapper for CookieManager.
 *
 * @see CookieManager
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class CookieManagerWrapper extends ConfigTestElementWrapper<CookieManager, CookiePanel> {

  @XStreamOmitField private static final long serialVersionUID = 8482403319301067878L;

  @JmcProperty("CookieManager.clearEachIteration")
  @Getter
  @Default
  private Boolean clearEachIteration = false;

  @JmcProperty("CookieManager.controlledByThreadGroup")
  @Getter
  @Default
  private Boolean controlledByThreadGroup = false;

  @JmcProperty("CookieManager.policy")
  @Getter
  @Default
  @JmcSkipDefault("standard")
  private CookiePolicy policy = CookiePolicy.STANDARD;

  @Override
  public Class<CookiePanel> getGuiClass() {
    return CookiePanel.class;
  }

  @Override
  public Class<CookieManager> getTestClass() {
    return CookieManager.class;
  }

  @JmcCollection(value = "CookieManager.cookies")
  @Getter
  @JmcEmptyAllowed
  @Default
  private final List<CookieWrapper> cookies = new ArrayList<>();

  /** Builder. */
  public abstract static class CookieManagerWrapperBuilder<
          C extends CookieManagerWrapper, B extends CookieManagerWrapperBuilder<C, B>>
      extends ConfigTestElementWrapperBuilder<CookieManager, CookiePanel, C, B> {

    protected B withCookies(List<CookieWrapper> cookies) {
      this.cookies$value = cookies;
      this.cookies$set = true;
      return self();
    }

    /** Add cookie List. */
    public B addCookies(List<CookieWrapper> cookies) {
      this.cookies$set = true;
      if (this.cookies$value == null) {
        this.cookies$value = new ArrayList<>();
      }
      this.cookies$value.addAll(cookies);
      return self();
    }

    /** Add cookie. */
    public B addCookie(CookieWrapper cookies) {
      return this.addCookies(Arrays.asList(cookies));
    }

    /**
     * Add cookie .
     *
     * @param name name.
     * @param value value.
     */
    public B addCookie(String name, String value) {
      return this.addCookie(CookieWrapper.builder().withName(name).withValue(value).build());
    }
  }
}
