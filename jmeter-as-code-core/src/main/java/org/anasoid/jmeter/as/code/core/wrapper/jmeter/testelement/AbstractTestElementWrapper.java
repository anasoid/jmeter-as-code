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
 * Date :   04-Jan-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;

/**
 * Wrapper for AbstractTestElement.
 *
 * @see AbstractTestElement
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractTestElementWrapper<T extends AbstractTestElement> {

  /** Name. */
  @XStreamAsAttribute
  @XStreamAlias("testname")
  @Getter
  private String name;

  /** Comments. */
  @JmcProperty(TestElement.COMMENTS)
  @Getter
  @Builder.Default
  @JmcSkipDefault("")
  private String comment = "";

  /** enabled. */
  @XStreamAsAttribute @Builder.Default @Getter private boolean enabled = true;

  @Getter
  @Builder.Default
  @XStreamAlias("hashTree")
  @XStreamOmitField
  private List<AbstractTestElementWrapper<?>> childs = new ArrayList<>();

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  @JmcMethodAlias("testclass")
  @JmcAsAttribute
  public String getTestClassAsString() {
    if (getTestClass() != null) {
      return getTestClass().getSimpleName();
    }
    return null;
  }

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  @JmcMethodAlias("guiclass")
  @JmcAsAttribute
  public String getGuiClassAsString() {
    if (this instanceof JMeterGUIWrapper) {
      JMeterGUIWrapper<?> gui = (JMeterGUIWrapper) this;
      return gui.getGuiClass().getSimpleName();
    }
    return null;
  }

  /** Test Class used by Jmeter TestElement.TEST_CLASS. @See TestElement. */
  public abstract Class<T> getTestClass();

  public void init() {}

  /** Builder. */
  @SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
  public abstract static class AbstractTestElementWrapperBuilder<
      T extends AbstractTestElement,
      C extends AbstractTestElementWrapper<T>,
      B extends AbstractTestElementWrapperBuilder<T, C, B>> {

    protected B withChilds(Collection<AbstractTestElementWrapper<?>> childs) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.addAll(childs);
      this.childs$set = true;

      return self();
    }

    protected B withChild(AbstractTestElementWrapper<?> child) {
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
    public B addChilds(List<AbstractTestElementWrapper<?>> childs) {
      for (AbstractTestElementWrapper<?> testElement : childs) {
        boolean found = false;
        try { // NOSONAR
          Method method;
          Class<?> clazz = testElement.getClass();
          while (clazz != AbstractTestElementWrapper.class) { // NOSONAR
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
    protected B addChild(AbstractTestElementWrapper<?> child) {
      return this.withChild(child);
    }
  }
}
