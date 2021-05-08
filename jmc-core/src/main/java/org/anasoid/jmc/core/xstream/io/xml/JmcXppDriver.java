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
 * Date :   04-Apr-2021
 */

package org.anasoid.jmc.core.xstream.io.xml;

import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.Writer;

/** Xstream Jmc Driver. */
public class JmcXppDriver extends XppDriver {

  @Override
  public HierarchicalStreamWriter createWriter(Writer out) {
    return new JmcXstreamWriter(out, this.getNameCoder());
  }
}
