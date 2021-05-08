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

package org.anasoid.jmc.core.wrapper.jmc.http.client.config;

/**
 * Wrapper for Mechanism. Type of authentication to perform. JMeter can perform different types of
 * authentications based on used Http Samplers:
 *
 * <p>Java BASIC HttpClient 4 BASIC, DIGEST and Kerberos
 *
 * @see org.apache.jmeter.protocol.http.control.AuthManager.Mechanism
 */
public enum AuthMechanism {
  @Deprecated
  BASIC_DIGEST("BASIC_DIGEST"),
  BASIC("BASIC"),

  DIGEST("DIGEST"),
  KERBEROS("KERBEROS");

  public final String value;

  private AuthMechanism(String value) {
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
