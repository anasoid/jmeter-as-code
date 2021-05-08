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
 * Date :   15-Feb-2021
 */

package org.anasoid.jmc.core.test.utils.xmlunit;

import com.google.common.collect.FluentIterable;
import java.util.Comparator;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xmlunit.diff.DefaultNodeMatcher;

/** xmlunit Node Matcher for comparison. Order node to ignore node order for comparison. */
public class JmcNodeMatcher extends DefaultNodeMatcher {
  @Override
  public Iterable<Map.Entry<Node, Node>> match(
      Iterable<Node> controlNodes, Iterable<Node> testNodes) {

    return super.match(sort(controlNodes), sort(testNodes));
  }

  private static Iterable<Node> sort(Iterable<Node> nodes) {
    return FluentIterable.from(nodes).toSortedList(COMPARATOR_NODE);
  }

  private static final Comparator<Node> COMPARATOR_NODE =
      (node1, node2) -> {
        int nameResult = StringUtils.compare(node1.getLocalName(), node2.getLocalName());

        if (nameResult != 0) {
          return nameResult;
        }
        NamedNodeMap namedNodeMap1 = node1.getAttributes();
        NamedNodeMap namedNodeMap2 = node2.getAttributes();
        if (namedNodeMap1 == null && namedNodeMap2 == null) {
          return 0;
        } else if (namedNodeMap1 == null) {
          return 1;
        } else if (namedNodeMap2 == null) {
          return -1;
        }

        int size1 = namedNodeMap1.getLength();
        int size2 = namedNodeMap2.getLength();
        if (size1 != size2) {
          return Integer.valueOf(size1).compareTo(Integer.valueOf(size2));
        }
        for (int i = 0; i < size1; i++) {
          Node subNode1 = namedNodeMap1.item(i);
          Node subNode2 = namedNodeMap2.item(i);
          int subResult = StringUtils.compare(subNode1.getNodeValue(), subNode2.getNodeValue());
          if (subResult != 0) {
            return subResult;
          }
        }
        return 0;
      };
}
