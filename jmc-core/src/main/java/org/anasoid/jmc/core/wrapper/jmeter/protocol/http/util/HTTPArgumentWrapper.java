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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.AbstractArgumentWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.protocol.http.util.HTTPArgument;

/**
 * Wrapper for HTTPArgument.
 *
 * @see HTTPArgument
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class HTTPArgumentWrapper extends AbstractArgumentWrapper<HTTPArgument> {

  /** URL Encode. */
  @JmcProperty("HTTPArgument.always_encode")
  @Getter
  @Setter
  @Default
  private Boolean alwaysEncoded = Boolean.FALSE;
  /** Include equals. */
  @JmcProperty("HTTPArgument.use_equals")
  @Getter
  @Setter
  @Default
  private Boolean useEquals = true;
  /** Content-Type. */
  @JmcProperty("HTTPArgument.content_type")
  @Getter
  @Setter
  private String contentType;

  @Override
  public Class<?> getTestClass() {
    return HTTPArgument.class;
  }

  @Override
  public void internalInit() {
    super.internalInit();
    if (useEquals != null && StringUtils.isNotEmpty(getValue())) {
      useEquals = true;
    }
  }
}
