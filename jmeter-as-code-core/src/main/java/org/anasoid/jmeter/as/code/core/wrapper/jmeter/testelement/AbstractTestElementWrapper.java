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
import org.anasoid.jmeter.as.code.core.wrapper.converter.ConvertUtils;
import org.anasoid.jmeter.as.code.core.wrapper.converter.Converter;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.AutoConvert;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcAsAttribute;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.apache.jmeter.testelement.AbstractTestElement;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jorphan.collections.HashTree;

@SuperBuilder(setterPrefix = "with")
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractTestElementWrapper<T extends AbstractTestElement>
    implements Converter<T> {

  @XStreamAsAttribute
  @XStreamAlias("testname")
  @Getter
  private String name;

  @JmcProperty(TestPlan.COMMENTS)
  @Getter
  private String comment;

  @XStreamAsAttribute @Builder.Default @Getter private boolean enabled = true;

  @Getter
  @AutoConvert(false)
  @Builder.Default
  @XStreamAlias("hashTree")
  @XStreamOmitField
  private List<AbstractTestElementWrapper> childs = new ArrayList<>();

  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  @JmcMethodAlias("testclass")
  @JmcAsAttribute
  public String getTestClassAsString() {
    return getTestClass().getSimpleName();
  }
  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  @JmcMethodAlias("guiclass")
  @JmcAsAttribute
  public String getGuiClassAsString() {
    if (this instanceof JMeterGUIWrapper) {
      JMeterGUIWrapper gui = (JMeterGUIWrapper) this;
      return gui.getGuiClass().getSimpleName();
    }
    return null;
  }
  /** Test Class used by Jmeter TestElement.TEST_CLASS @See TestElement */
  public abstract Class<T> getTestClass();

  public void init() {}

  @Override
  public T convert() {
    beforeconvert();
    T dest = internalConvert();
    afterConvert(dest);
    return dest;
  }

  public HashTree convertAll() {
    HashTree me = new HashTree();
    HashTree childTree = me.add(convert());
    for (AbstractTestElementWrapper testElement : childs) {
      testElement.convertAll(childTree);
    }
    return me;
  }

  protected void convertAll(HashTree parent) {
    HashTree childTree = parent.add(convert());
    for (AbstractTestElementWrapper testElement : childs) {
      testElement.convertAll(childTree);
    }
  }

  protected T internalConvert() {
    T destination = newTarget();
    destination.setProperty(TestElement.TEST_CLASS, getTestClass().getName());
    if (this instanceof JMeterGUIWrapper) {
      destination.setProperty(
          TestElement.GUI_CLASS, ((JMeterGUIWrapper) this).getGuiClass().getName());
    }
    try {
      ConvertUtils.copyProperties(destination, this);
    } catch (IllegalAccessException
        | InvocationTargetException
        | NoSuchMethodException
        | NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    return destination;
  }

  protected void beforeconvert() {}

  protected void afterConvert(T dest) {}

  public abstract static class AbstractTestElementWrapperBuilder<
      T extends AbstractTestElement,
      C extends AbstractTestElementWrapper<T>,
      B extends AbstractTestElementWrapperBuilder<T, C, B>> {

    protected B withChilds(Collection<AbstractTestElementWrapper> childs) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.addAll(childs);
      this.childs$set = true;

      return self();
    }

    protected B withChild(AbstractTestElementWrapper child) {
      if (!this.childs$set) {
        this.childs$value = new ArrayList<>();
      }
      this.childs$value.add(child);
      this.childs$set = true;

      return self();
    }

    public B addChilds(List<AbstractTestElementWrapper> childs) {
      for (AbstractTestElementWrapper testElement : childs) {
        try {
          Method method = this.getClass().getMethod("addChild", AbstractTestElementWrapper.class);
          method.invoke(this, testElement);

        } catch (NoSuchMethodException e) {
          throw new IllegalArgumentException("Illegal argument Type of  : " + testElement);
        } catch (IllegalAccessException e) {
          // Should Not Happen
          throw new RuntimeException(e);

        } catch (InvocationTargetException e) {
          // Should Not Happen
          throw new RuntimeException(e);
        }
      }
      return self();
    }

    public B addChild(AbstractTestElementWrapper child) {
      return this.withChild(child);
    }
  }
}
