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

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.util.HTTPFileArg;

/**
 * Wrapper for HTTPFileArg.
 *
 * @see HTTPFileArg
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class HTTPFileArgWrapper extends AbstractBasicTestElementWrapper<HTTPFileArg> {

  /** File path. */
  @XStreamOmitField @Getter @Setter private String path;
  /** Parameter name. */
  @JmcProperty("File.paramname")
  @Getter
  @Setter
  private String name;
  /** MIME Type?. */
  @JmcProperty("File.mimetype")
  @Getter
  @Setter
  private String mimeType;

  @JmcAsAttribute
  @JmcMethodAlias("name")
  protected String getPathAsAttribute() {
    return path;
  }

  @JmcProperty("File.path")
  protected String getPathProperty() {
    return path;
  }

  @Override
  public Class<?> getTestClass() {
    return HTTPFileArg.class;
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }
}
