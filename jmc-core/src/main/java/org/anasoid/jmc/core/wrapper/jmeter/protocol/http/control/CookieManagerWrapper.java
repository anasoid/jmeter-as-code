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

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmc.core.wrapper.jmc.http.client.config.CookiePolicy;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.protocol.http.control.CookieManager;
import org.apache.jmeter.protocol.http.gui.CookiePanel;

/**
 * Wrapper for CookieManager.
 *
 * @see CookieManager
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcChildrenTypes(type = {CookieWrapper.class})
@JmcDefaultName("HTTP Cookie Manager")
public class CookieManagerWrapper extends ConfigTestElementWrapper<CookieManager, CookiePanel> {

  @XStreamOmitField private static final long serialVersionUID = 8482403319301067878L;

  @JmcCollection(value = "CookieManager.cookies")
  @Getter
  @JmcEmptyAllowed
  @Default
  private final List<CookieWrapper> cookies = new ArrayList<>();

  @JmcProperty("CookieManager.clearEachIteration")
  @Getter
  @Setter
  @Default
  private Boolean clearEachIteration = false;

  @JmcProperty("CookieManager.controlledByThreadGroup")
  @Getter
  @Setter
  @Default
  private Boolean controlledByThreadGroup = false;

  @JmcProperty("CookieManager.policy")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("standard")
  private CookiePolicy policy = CookiePolicy.STANDARD;

  @Override
  public Class<?> getGuiClass() {
    return CookiePanel.class;
  }

  @Override
  public Class<?> getTestClass() {
    return CookieManager.class;
  }

  /** Builder. */
  public abstract static class CookieManagerWrapperBuilder<
          C extends CookieManagerWrapper, B extends CookieManagerWrapperBuilder<C, B>>
      extends ConfigTestElementWrapperBuilder<CookieManager, CookiePanel, C, B> {

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    private B withCookies(List<CookieWrapper> cookies) { // NOSONAR

      this.cookies$value = cookies;
      this.cookies$set = true;
      return self();
    }

    /** Add cookie List. */
    public B addCookies(List<CookieWrapper> cookies) {
      if (!this.cookies$set) {
        withCookies(new ArrayList<>());
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
