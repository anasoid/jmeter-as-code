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

package org.anasoid.jmc.core.test.utils.xmlunit;

import java.util.Arrays;
import java.util.List;
import org.anasoid.jmc.core.test.utils.xmlunit.filter.JmcXmlFilterAttr;
import org.anasoid.jmc.core.test.utils.xmlunit.filter.attr.JmeterOnTestPlanFilter;
import org.w3c.dom.Attr;
import org.xmlunit.util.Predicate;

/** Default attributes filter, to filter attributes to not be tested. */
public class JmcAttributeFilter implements Predicate<Attr> {

  List<JmcXmlFilterAttr> filters = Arrays.asList(new JmeterOnTestPlanFilter());

  public JmcAttributeFilter() {}

  /**
   * list filters.
   *
   * @param filters list filters.
   */
  public JmcAttributeFilter(List<JmcXmlFilterAttr> filters) {
    if (filters != null) {
      this.filters.addAll(filters);
    }
  }

  @Override
  public boolean test(Attr toTest) {
    for (JmcXmlFilterAttr filter : filters) {
      if (filter.filter(toTest)) {
        return false;
      }
    }
    return true;
  }
}
