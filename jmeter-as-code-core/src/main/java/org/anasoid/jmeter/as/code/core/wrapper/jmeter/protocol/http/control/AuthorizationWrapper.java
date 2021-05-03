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

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.control;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.http.client.config.AuthMechanism;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.apache.jmeter.protocol.http.control.Authorization;

/**
 * Wrapper for Authorization.
 *
 * @see Authorization
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class AuthorizationWrapper extends AbstractBasicTestElementWrapper<Authorization> {

  @JmcProperty("Authorization.url")
  private @Getter @JmcNullAllowed String url;

  @JmcProperty("Authorization.username")
  private @Getter @JmcNullAllowed String username;

  @JmcProperty("Authorization.password")
  private @Getter @JmcNullAllowed String password;

  @JmcProperty("Authorization.domain")
  private @Getter @JmcNullAllowed String domain;

  @JmcProperty("Authorization.realm")
  private @Getter @JmcNullAllowed String realm;

  @JmcProperty("Authorization.mechanism")
  @JmcSkipDefault("BASIC")
  @Default
  private @Getter @JmcNullAllowed AuthMechanism mechanism = AuthMechanism.BASIC;

  @Override
  public Class<?> getTestClass() {
    return Authorization.class;
  }

  /** To have always a empty attribute name. */
  @JmcAsAttribute
  @JmcMethodAlias("name")
  public String emptyAttributeName() {
    return "";
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }
}
