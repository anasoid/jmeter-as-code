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
 * Date :   19-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.node;

import java.util.Map;
import java.util.Map.Entry;
import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.JmcXmlFilterNode;
import org.w3c.dom.Node;

/** Ignore node by name and attributes keys/values. */
public class NodeByAttributesFilter implements JmcXmlFilterNode {
  private final String parent;
  private final String node;
  private final Map<String, String> attributes;

  /**
   * Ignore node by name and attributes keys/values.
   *
   * @param node node name.
   * @param attributes attribute key/values.
   */
  public NodeByAttributesFilter(String node, Map<String, String> attributes) {
    this.node = node;
    this.attributes = attributes;
    this.parent = null;
  }

  /**
   * Ignore node by name, parent name and attributes keys/values.
   *
   * @param node node name.
   * @param parent node name.
   * @param attributes attribute key/values.
   */
  public NodeByAttributesFilter(String node, String parent, Map<String, String> attributes) {
    this.node = node;
    this.attributes = attributes;
    this.parent = parent;
  }

  @Override
  public boolean filter(Node toTest) {
    if (node.equals(toTest.getLocalName())
        && (parent == null
            || (toTest.getParentNode() != null
                && parent.equals(toTest.getParentNode().getLocalName())))) {
      for (Entry<String, String> entry : attributes.entrySet()) {
        Node attr = toTest.getAttributes().getNamedItem(entry.getKey());
        if (attr == null || !entry.getValue().equals(attr.getNodeValue())) {
          return false;
        }
      }
      return true;
    }
    return false;
  }
}
