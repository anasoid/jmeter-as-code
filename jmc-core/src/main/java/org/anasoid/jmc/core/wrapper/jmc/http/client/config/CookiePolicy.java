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
 * Date :   04-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.http.client.config;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.enums.EnumToStringConverter;

/** Cookies policies. */
@XStreamConverter(value = EnumToStringConverter.class)
public enum CookiePolicy {

  /** The Netscape cookie draft compliant policy. */
  NETSCAPE("netscape"),

  /** The RFC 6265 compliant policy (interoprability profile). */
  STANDARD("standard"),

  /** The RFC 6265 compliant policy (strict profile). */
  STANDARD_STRICT("standard-strict"),

  /**
   * The default policy. This policy provides a higher degree of compatibility with common cookie
   * management of popular HTTP agents for non-standard (Netscape style) cookies.
   */
  DEFAULT("default"),
  /** The policy that ignores cookies. */
  IGNORE_COOKIES("ignoreCookies"),

  /** RFC2109. */
  RFC2109("rfc2109"),

  /** RFC2965. */
  RFC2965("rfc2965");

  public final String value;

  CookiePolicy(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
