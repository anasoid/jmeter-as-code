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
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.control.Header;

/**
 * Wrapper for Header.
 *
 * @see Header
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class HeaderWrapper extends AbstractTestElementWrapper<Header> {

  @XStreamOmitField private static final long serialVersionUID = -4068882490947271216L;

  @JmcProperty("Header.name")
  @Getter
  @Default
  private String name = "";

  @XStreamOmitField @Builder.Default @Getter private boolean enabled = true;

  /** Comments. */
  @XStreamOmitField @Getter private String comment;

  @JmcProperty("Header.value")
  @Default
  private @Getter String value = "";

  @Override
  public Class<Header> getTestClass() {
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