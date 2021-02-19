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

package org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.attr;

import org.anasoid.jmeter.as.code.core.test.utils.xmlunit.filter.JmcXmlFilterAttr;
import org.junit.platform.commons.util.StringUtils;
import org.w3c.dom.Attr;

/** Ignore Jmeter property on TesPlan, to be tolerant to changing version. */
public class JmeterOnTestPlanFilter implements JmcXmlFilterAttr {

  @Override
  public boolean filter(Attr attr) {
    if ("jmeter".equals(attr.getName())
        && StringUtils.isNotBlank(attr.getValue())
        && attr.getOwnerElement() != null
        && "jmeterTestPlan".equals(attr.getOwnerElement().getLocalName())) {
      return true;
    }
    return false;
  }
}
