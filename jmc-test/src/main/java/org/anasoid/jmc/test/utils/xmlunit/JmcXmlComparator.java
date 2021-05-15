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
 * Date :   17-Feb-2021
 */

package org.anasoid.jmc.test.utils.xmlunit;

import java.util.Arrays;
import java.util.List;
import org.anasoid.jmc.test.utils.xmlunit.filter.JmcXmlFilterAttr;
import org.anasoid.jmc.test.utils.xmlunit.filter.JmcXmlFilterNode;
import org.w3c.dom.Node;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Comparison.Detail;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.Difference;

/** Util to compare XML. */
public final class JmcXmlComparator {

  private static final String NAME_KEY = "name";

  private JmcXmlComparator() {}

  /**
   * Compare Xml content.
   *
   * @param expected expected string.
   * @param toTest toTest.
   * @return diff.
   */
  public static Diff compare(String expected, String toTest) {

    return compare(expected, toTest, null, null);
  }

  /**
   * Compare Xml content.
   *
   * @param expected expected string.
   * @param toTest toTest.
   * @param filterAttrs list of attributes filters'
   * @param filterNodes list of nodes filters'
   * @return diff.
   */
  public static Diff compare(
      String expected,
      String toTest,
      List<JmcXmlFilterAttr> filterAttrs,
      List<JmcXmlFilterNode> filterNodes) {

    Diff myDiff =
        DiffBuilder.compare(expected)
            .withTest(toTest)
            .withNodeMatcher(new JmcNodeMatcher())
            .withAttributeFilter(new JmcAttributeFilter(filterAttrs))
            .withNodeFilter(new JmcNodeFilter(filterNodes))
            .normalizeWhitespace()
            .checkForSimilar()
            .build();
    return myDiff;
  }

  /**
   * Check diff and ignore error on property related to conversion as String.
   *
   * @param diff diff object to be checked.
   * @param ignoreProperties properties names to be ignored.
   */
  public static boolean hasDifferences(Diff diff, String... ignoreProperties) {
    boolean found = false;
    List properties = Arrays.asList(ignoreProperties);
    for (Difference difference : diff.getDifferences()) {
      if (ComparisonType.ELEMENT_TAG_NAME.equals(difference.getComparison().getType())) {
        Detail control = difference.getComparison().getControlDetails();
        Detail test = difference.getComparison().getTestDetails();

        Node attrControl = control.getTarget().getAttributes().getNamedItem(NAME_KEY);
        Node attrTest = test.getTarget().getAttributes().getNamedItem(NAME_KEY);
        if (attrControl != null
            && attrTest != null
            && properties.contains(attrControl.getNodeValue())
            && properties.contains(attrTest.getNodeValue())) {
          found = false;
        } else {
          return true;
        }
      } else {
        return true;
      }
    }

    return found;
  }
}
