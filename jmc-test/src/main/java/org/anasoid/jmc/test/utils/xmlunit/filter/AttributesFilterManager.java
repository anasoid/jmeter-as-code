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
 * Date :   20-Feb-2021
 */

package org.anasoid.jmc.test.utils.xmlunit.filter;

import java.util.HashMap;
import java.util.Map;
import org.anasoid.jmc.test.utils.xmlunit.filter.node.NodeByAttributesFilter;

/** Manage attributes filter helper. */
public final class AttributesFilterManager {

  private AttributesFilterManager() {}

  /** get filter to gnore comments. */
  public static NodeByAttributesFilter getCommentFilter() {

    Map<String, String> map = new HashMap<>();
    map.put("name", "TestPlan.comments");
    NodeByAttributesFilter filter = new NodeByAttributesFilter("stringProp", "TestPlan", map);
    return filter;
  }
}
