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

package org.anasoid.jmeter.as.code.core.test.utils.xmlunit;

import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.diff.Diff;

/** Util to compare XML. */
public final class JmcXmlComparator {

  private JmcXmlComparator() {}

  /**
   * Compare Xml content.
   *
   * @return diff.
   */
  public static Diff compare(String expected, String toTest) {

    Diff myDiff =
        DiffBuilder.compare(expected)
            .withTest(toTest)
            .withNodeMatcher(new JmcNodeMatcher())
            .withAttributeFilter(new JmcAttributeFilter())
            .withNodeFilter(new JmcNodeFilter())
            .checkForSimilar()
            .build();
    return myDiff;
  }
}
