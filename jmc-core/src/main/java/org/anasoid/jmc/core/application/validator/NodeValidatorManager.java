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
 * Date :   27-Mar-2021
 */

package org.anasoid.jmc.core.application.validator;

import java.util.Arrays;
import java.util.List;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;

/** Group all validator to validate testElement. */
public final class NodeValidatorManager {

  private static final List<NodeValidator> VALIDATORS = Arrays.asList(new ChildrenTypeValidator());

  private NodeValidatorManager() {}

  /**
   * Validate node, Check using all nodate validators.
   *
   * @throws ConversionIllegalStateException if not valid.
   */
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public static void validate(TestElementWrapper<?> testElementWrapper)
      throws ConversionIllegalStateException {

    for (NodeValidator validator : VALIDATORS) {
      validator.validate(testElementWrapper);
    }
  }
}
