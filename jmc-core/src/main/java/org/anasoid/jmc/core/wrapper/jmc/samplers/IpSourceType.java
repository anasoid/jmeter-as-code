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
 * Date :   22-Jan-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.samplers;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.enums.EnumToStringConverter;

/**
 * Enumeretaion for IpSource.
 *
 * @see org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase.SourceType
 */
@XStreamConverter(value = EnumToStringConverter.class)
public enum IpSourceType {
  Hostname(0), // NOSONAR
  DEVICE(1), // NOSONAR
  DEVICE_IPV4(2), // NOSONAR
  DEVICE_IPV6(3); // NOSONAR

  public final Integer value;

  IpSourceType(Integer value) {
    this.value = value;
  }

  public Integer value() {
    return value;
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
