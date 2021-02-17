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

import org.junit.platform.commons.util.StringUtils;
import org.w3c.dom.Attr;
import org.xmlunit.util.Predicate;

/** Default attributes filter, to filter attributes to not be tested. */
public class JmcAttributeFilter implements Predicate<Attr> {

  @Override
  public boolean test(Attr toTest) {
    if (isJmeterOnTestPlan(toTest)) {
      return false;
    }
    return true;
  }

  /**
   * Ignore jmeter attribute on testplan to not be impacted by changing jmeter version.
   *
   * @param toTest attribute to Test.
   * @return true to ignore.
   */
  private boolean isJmeterOnTestPlan(Attr toTest) {
    if ("jmeter".equals(toTest.getName())
        && StringUtils.isNotBlank(toTest.getValue())
        && toTest.getOwnerElement() != null
        && "jmeterTestPlan".equals(toTest.getOwnerElement().getLocalName())) {
      return true;
    }
    return false;
  }
}
