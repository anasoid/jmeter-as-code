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
 * Date :   03-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.assertions;

import java.util.Map;
import lombok.NonNull;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.generic.AbstractJmxIncludeWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AssertionWrapper;
import org.apache.jmeter.assertions.Assertion;

/** Assertion Include Jmx. */
public class AssertionJmxIncludeWrapper extends AbstractJmxIncludeWrapper<Assertion>
    implements AssertionWrapper<Assertion> {

  private static final long serialVersionUID = -8017246349372467904L;

  public AssertionJmxIncludeWrapper(@NonNull String path) {
    super(path);
  }

  public AssertionJmxIncludeWrapper(String path, Map<String, String> params) {
    super(path, params);
  }
}
