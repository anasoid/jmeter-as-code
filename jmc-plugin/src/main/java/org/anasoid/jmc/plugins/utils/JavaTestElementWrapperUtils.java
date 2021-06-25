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
 * Date :   22-Jun-2021
 */

package org.anasoid.jmc.plugins.utils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;

/**
 * AbstractJavaParentTestElementWrapper and utils.
 *
 * @see org.anasoid.jmc.plugins.wrapper.java.AbstractJavaParentTestElementWrapper
 * @see org.anasoid.jmc.plugins.wrapper.java.AbstractJavaTestElementWrapper
 */
public final class JavaTestElementWrapperUtils {

  private JavaTestElementWrapperUtils() {}

  /**
   * transform map to ArgumentWrapper List.
   *
   * @param parameters map input.
   * @return list ArgumentWrapper
   */
  public static List<ArgumentWrapper> mapToArgumentList(@NonNull Map<String, String> parameters) {

    return parameters.entrySet().stream()
        .map(c -> ArgumentWrapper.builder().withName(c.getKey()).withValue(c.getValue()).build())
        .collect(Collectors.toList());
  }

  /**
   * valate parametres and parameters key.
   *
   * @param parametersKey parametersKey.
   * @param parameters parameters.
   * @throws ConversionIllegalStateException if not valid.
   */
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public static void validate(List<String> parametersKey, @NonNull Map<String, String> parameters)
      throws ConversionIllegalStateException {
    if (parametersKey != null) {
      List<String> notValidKeys =
          parameters.keySet().stream()
              .filter(c -> !parametersKey.contains(c))
              .collect(Collectors.toList());
      if (!notValidKeys.isEmpty()) {
        throw new ConversionIllegalStateException("Not Valid Parameters  : " + notValidKeys);
      }
    }
  }
}
