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
 * Date :   14-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java;

import com.thoughtworks.xstream.converters.ConversionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Base class for Java TestElement. */
public abstract class JavaTestElement<T extends JavaTestElementExecutor>
    extends AbstractTestElement {

  private static final long serialVersionUID = -3117849471442160201L;

  private String getExecutorClass() {
    return getPropertyAsString("executorClass");
  }

  protected T getExecutor() {

    try {
      Class<?> wrapper = Class.forName(getExecutorClass());
      Method builderMethod = wrapper.getDeclaredMethod("builder");
      builderMethod.setAccessible(true); // NOSONAR
      Object builder = builderMethod.invoke(null);

      Method buildMethod = builder.getClass().getDeclaredMethod("build");
      buildMethod.setAccessible(true); // NOSONAR
      return (T) buildMethod.invoke(builder);
    } catch (ClassNotFoundException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new ConversionException("Loading executor ", e);
    }
  }
}
