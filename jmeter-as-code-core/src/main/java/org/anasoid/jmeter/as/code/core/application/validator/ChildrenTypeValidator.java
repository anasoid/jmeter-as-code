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

package org.anasoid.jmeter.as.code.core.application.validator;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.anasoid.jmeter.as.code.core.application.validator.annotations.JmcChildrenTypes;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;

/** Validate type of children, When access directly to child list, children can be not valid. */
public class ChildrenTypeValidator implements NodeValidator {

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate(TestElementWrapper testElementWrapper)
      throws ConversionIllegalStateException {
    List<TestElementWrapper<?>> children = testElementWrapper.getChilds();
    Set<Class<?>> types = getTypes(testElementWrapper);
    if (children != null && !types.isEmpty()) {

      for (TestElementWrapper<?> child : children) {

        if (types.stream().noneMatch(t -> t.isAssignableFrom(child.getClass()))) {

          throw new ConversionIllegalStateException(
              MessageFormat.format(
                  "Child {0} with type {1} not valid on parent {2} with type {3}",
                  child, child.getClass(), testElementWrapper, testElementWrapper.getClass()));
        }
      }
    }
  }

  /** is field/method will be converted as property. */
  @SuppressWarnings({"PMD.EmptyCatchBlock", "PMD.AvoidDeeplyNestedIfStmts"})
  public static Set<Class<?>> getTypes(TestElementWrapper<?> testElementWrapper) {
    Set<Class<?>> result = new HashSet<>();
    Class<?> item = testElementWrapper.getClass();
    while (item != Object.class) {
      JmcChildrenTypes[] jmcChildrenTypes =
          item.getAnnotationsByType(JmcChildrenTypes.class);
      for (JmcChildrenTypes j : jmcChildrenTypes) {
        result.addAll(Arrays.asList(j.type()));
      }
      item = item.getSuperclass();
    }
    return result;
  }
}
