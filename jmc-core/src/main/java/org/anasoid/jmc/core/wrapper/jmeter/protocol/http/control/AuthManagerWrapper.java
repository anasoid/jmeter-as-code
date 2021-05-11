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
 * Date :   11-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.protocol.http.control.AuthManager;
import org.apache.jmeter.protocol.http.gui.AuthPanel;

/**
 * Wrapper for AuthManager.
 *
 * @see AuthManager
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class AuthManagerWrapper extends ConfigTestElementWrapper<AuthManager, AuthPanel> {

  @XStreamOmitField private static final long serialVersionUID = 4261318150151324005L;
  /** If selected, then the cache is cleared at the start of the thread. */
  @JmcProperty("AuthManager.clearEachIteration")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault("false")
  private boolean clearEachIteration = false;

  @JmcProperty("AuthManager.controlledByThreadGroup")
  @Getter
  @Setter
  @Default
  private boolean controlledByThread = false;

  @JmcCollection(value = "AuthManager.auth_list")
  @Builder.Default
  @Getter
  @JmcEmptyAllowed
  private final List<AuthorizationWrapper> authorizations = new ArrayList<>();

  @Override
  public Class<?> getGuiClass() {
    return AuthPanel.class;
  }

  @Override
  public Class<?> getTestClass() {
    return AuthManager.class;
  }

  /** builder. */
  public abstract static class AuthManagerWrapperBuilder<
          C extends AuthManagerWrapper, B extends AuthManagerWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          AuthManager, AuthPanel, C, B> {

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

    protected B withAuthorizations(List<AuthorizationWrapper> authorizations) {
      this.authorizations$value = authorizations;
      this.authorizations$set = true;
      return self();
    }

    /** Add authorization List. */
    public B addAuthorizations(List<AuthorizationWrapper> authorizations) {
      this.authorizations$set = true;
      if (this.authorizations$value == null) {
        this.authorizations$value = new ArrayList<>();
      }
      this.authorizations$value.addAll(authorizations);
      return self();
    }

    /** Add authorization. */
    public B addAuthorization(AuthorizationWrapper authorization) {
      return this.addAuthorizations(Arrays.asList(authorization));
    }
  }
}
