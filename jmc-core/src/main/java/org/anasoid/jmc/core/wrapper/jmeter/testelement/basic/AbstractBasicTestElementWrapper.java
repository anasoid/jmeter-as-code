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

package org.anasoid.jmc.core.wrapper.jmeter.testelement.basic;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmc.core.xstream.annotations.JmcInherited;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.testelement.AbstractTestElement;

/** Abstract Class for all test element. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractBasicTestElementWrapper<T extends AbstractTestElement>
    implements TestElementWrapper<T> {

  @XStreamOmitField private static final long serialVersionUID = 5001920204233593046L;
  @Default @Getter @XStreamOmitField private final Set<String> tags = new HashSet<>();
  @XStreamOmitField private boolean isInitialized;

  @Override
  public final void init() {
    if (!isInitialized) {
      internalInit();
      isInitialized = true;
    }
  }

  protected void internalInit() {}

  @JmcInherited
  @Override
  public String getTestClassAsString() {
    if (getTestClass() != null) {
      boolean full = false;
      String result;
      if (isFullClassName() == null) {
        if (!getTestClass().getName().startsWith("org.apache.jmeter")) {
          full = true;
        }
      } else {
        full = isFullClassName();
      }
      if (full) {
        result = getTestClass().getName();
      } else {
        result = getTestClass().getSimpleName();
      }

      return result;
    }
    return null;
  }

  /**
   * use full class name or simple class name. null : auto, full if not start with
   * 'org.apache.jmeter'.
   *
   * @return default null.
   */
  protected Boolean isFullClassName() {
    return null;
  }

  @Override
  @SuppressWarnings("PMD.ReturnEmptyCollectionRatherThanNull")
  public List<TestElementWrapper<?>> getChildren() {
    return null;
  }

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement. */
  @JmcMethodAlias("guiclass")
  @JmcAsAttribute
  public String getGuiClassAsString() {
    String result = null;

    if (this instanceof JMeterGUIWrapper) {
      JMeterGUIWrapper<?> gui = (JMeterGUIWrapper) this;
      if (gui.getGuiClass() == null) {
        return null;
      }
      boolean full = false;
      if (isFullClassName() == null) {
        if (!gui.getGuiClass().getName().startsWith("org.apache.jmeter")) {
          full = true;
        }
      } else {
        full = isFullClassName();
      }

      if (full) {
        result = gui.getGuiClass().getName();
      } else {
        result = gui.getGuiClass().getSimpleName();
      }
    }
    return result;
  }

  /** Builder. */
  @SuppressWarnings("PMD.AbstractClassWithoutAbstractMethod")
  public abstract static class AbstractBasicTestElementWrapperBuilder<
          T extends AbstractTestElement,
          C extends AbstractBasicTestElementWrapper<T>,
          B extends AbstractBasicTestElementWrapperBuilder<T, C, B>>
      implements JmcWrapperBuilder<C> {

    /** hide method , generated by Lombok. */
    protected B withIsInitialized(boolean isInitialized) { // NOSONAR
      return self();
    }

    /** hide method , generated by Lombok. */
    protected B withTags(Set<String> tags) {
      this.tags$value = tags;
      this.tags$set = true;
      return self();
    }

    /** Add tags. */
    public B addTags(String... tags) {
      if (this.tags$value == null) {
        this.tags$value = new HashSet<>();
        this.tags$set = true;
      }
      for (String tag : tags) {
        this.tags$value.add(tag);
      }
      return self();
    }
  }
}
