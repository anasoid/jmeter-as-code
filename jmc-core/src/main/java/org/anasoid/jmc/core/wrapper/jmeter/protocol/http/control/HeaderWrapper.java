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
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.control.Header;

/**
 * Wrapper for Header.
 *
 * @see Header
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class HeaderWrapper extends AbstractBasicTestElementWrapper<Header> {

  @XStreamOmitField private static final long serialVersionUID = -4068882490947271216L;

  @JmcProperty("Header.name")
  @Getter
  @Setter
  @Default
  private String name = "";

  @JmcProperty("Header.value")
  @Default
  private @Getter @Setter String value = "";

  @Override
  public Class<?> getTestClass() {
    return Header.class;
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
