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

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.control.Cookie;

/**
 * Wrapper for Cookie.
 *
 * @see Cookie
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class CookieWrapper extends AbstractBasicTestElementWrapper<Cookie> {

  @XStreamOmitField private static final long serialVersionUID = -4068882490947271216L;

  @XStreamAsAttribute @Getter @Setter @JmcMandatory private String name;

  @JmcProperty("Cookie.value")
  @Getter
  @Setter
  @JmcMandatory
  private String value;

  @JmcProperty("Cookie.domain")
  @Getter
  @Setter
  @Default
  private String domain = "";

  @JmcProperty("Cookie.path")
  @Getter
  @Setter
  @Default
  private String path = "";

  @JmcProperty("Cookie.secure")
  @Getter
  @Setter
  @Default
  private Boolean secure = false;

  @JmcProperty(value = "Cookie.expires", type = Long.class)
  @Getter
  @Setter
  @Default
  private Integer expires = 0;

  /** Always present and true. */
  @JmcProperty("Cookie.path_specified")
  protected Boolean getPathSpecified() {
    return true;
  }

  /** Always present and true. */
  @JmcProperty("Cookie.domain_specified")
  protected Boolean getDomainSpecified() {
    return true;
  }

  @Override
  public Class<?> getTestClass() {
    return Cookie.class;
  }

  /** testname attribuet, same as name. */
  @JmcAsAttribute
  @JmcMethodAlias("testname")
  public String attributeTestName() {
    return name;
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }
}
