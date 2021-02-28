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
 * Date :   27-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Class represent a node with Children. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public abstract class AbstractBasicParentTestElementWrapper<T extends AbstractTestElement>
    extends AbstractBasicTestElementWrapper<T> {

  @Builder.Default @XStreamOmitField private List<TestElementWrapper<?>> childs = new ArrayList<>();

  @Override
  public List<TestElementWrapper<?>> getChilds() {
    return childs;
  }

  /** Builder. */
  public abstract static class AbstractBasicParentTestElementWrapperBuilder<
          T extends AbstractTestElement,
          C extends AbstractBasicParentTestElementWrapper<T>,
          B extends AbstractBasicParentTestElementWrapperBuilder<T, C, B>>
      extends AbstractBasicTestElementWrapper.AbstractBasicTestElementWrapperBuilder<T, C, B> {

    protected B withChilds(Collection<TestElementWrapper<?>> childs) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.addAll(childs);
      this.childs$set = true;

      return self();
    }

    protected B withChild(TestElementWrapper<?> child) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.add(child);
      this.childs$set = true;

      return self();
    }

    /**
     * Add Test element tree children.
     *
     * @param childs list of child.
     */
    public B addChilds(List<TestElementWrapper<?>> childs) {
      for (TestElementWrapper<?> testElement : childs) {
        boolean found = false;
        try { // NOSONAR
          Method method;
          Class<?> clazz = testElement.getClass();
          while (clazz != Object.class) { // NOSONAR
            try { // NOSONAR
              method = this.getClass().getMethod("addChild", clazz);
            } catch (NoSuchMethodException e) {
              clazz = clazz.getSuperclass();
              continue;
            }
            method.invoke(this, testElement);
            found = true;
            break; // NOPMD
          }

        } catch (IllegalAccessException | InvocationTargetException e) {
          // Should Not Happen
          throw new ConversionException(e);
        }
        if (!found) {
          throw new IllegalArgumentException("Illegal argument Type of  : " + testElement);
        }
      }
      return self();
    }

    /**
     * Add testElement as child in tree.
     *
     * @param child child.
     */
    protected B addChild(TestElementWrapper<?> child) {
      return this.withChild(child);
    }
  }
}
