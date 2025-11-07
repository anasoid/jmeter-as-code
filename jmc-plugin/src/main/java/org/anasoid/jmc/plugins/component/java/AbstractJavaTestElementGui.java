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
 * Date :   18-Jun-2021
 */

package org.anasoid.jmc.plugins.component.java;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.anasoid.jmc.plugins.config.gui.ReadOnlyArgumentsPanel;
import org.anasoid.jmc.plugins.utils.ExecutorUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.gui.util.HorizontalPanel;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.reflect.ClassFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** base gui class for JavaTestElement. */
@SuppressWarnings({"PMD.TooManyMethods"})
public abstract class AbstractJavaTestElementGui<T extends AbstractJavaTestElement>
    extends AbstractJMeterGuiComponent implements ActionListener {

  protected abstract Class<T> getTestClass();

  protected AbstractJavaTestElement getTestElementInstance() {
    try {
      return getTestClass().getDeclaredConstructor().newInstance();
    } catch (InstantiationException
        | NoSuchMethodException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  @Override
  public TestElement createTestElement() {
    AbstractJavaTestElement config = getTestElementInstance();
    modifyTestElement(config);
    return config;
  }

  private static final long serialVersionUID = 1L;

  /** Logging. */
  private static final Logger log = LoggerFactory.getLogger(AbstractJavaTestElementGui.class);

  /** A combo box allowing the user to choose a Executor class. */
  private JComboBox<String> classnameCombo;

  /** A panel allowing the user to set arguments for this test. */
  private ArgumentsPanel argsPanel;

  /** A attributePanel allowing the user to see attribute. */
  private ArgumentsPanel attributePanel;

  /** The current className of the Executor. * */
  private String className; // NOPMD

  /** Create a new AbstractJavaTestElementGui as a standalone component. */
  @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
  protected AbstractJavaTestElementGui() {
    super();
    initialize();
  }

  @Override
  public String getLabelResource() {
    return getClass().getCanonicalName();
  }

  @Override
  public String getStaticLabel() {
    return "@JMC " + getTestClass().getSimpleName();
  }

  /** get Executor class type. */
  protected abstract <T extends JavaTestElementExecutor> Class<T> getExecutorClass();

  /** Initialize the GUI components and layout. */
  private void initialize() { // called from ctor, so must not be overridable
    setLayout(new BorderLayout(0, 5));

    setBorder(makeBorder());
    add(makeTitlePanel(), BorderLayout.NORTH);

    JPanel classnameRequestPanel = new JPanel(new BorderLayout(0, 5));
    add(classnameRequestPanel, BorderLayout.CENTER);

    classnameRequestPanel.add(createClassnamePanel(), BorderLayout.NORTH);

    JPanel paramAttributePanel = new JPanel(new GridLayout(2, 1));
    classnameRequestPanel.add(paramAttributePanel, BorderLayout.CENTER);
    paramAttributePanel.add(createParameterPanel());
    paramAttributePanel.add(createAttributePanel());

    if (classnameCombo.getSelectedItem() != null) {
      className = ((String) classnameCombo.getSelectedItem()).trim();
    } else {
      className = "";
    }
  }

  @Override
  public void modifyTestElement(TestElement config) {
    configureTestElement(config);
    AbstractJavaTestElement<?> javaTestElement = (AbstractJavaTestElement) config;
    javaTestElement.setArguments((Arguments) argsPanel.createTestElement());
    javaTestElement.setExecutorClass(String.valueOf(classnameCombo.getSelectedItem()));
  }

  /**
   * Create a panel with GUI components allowing the user to select a test class.
   *
   * @return a panel containing the relevant components
   */
  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  private JPanel createClassnamePanel() {
    List<String> possibleClasses = new ArrayList<>();

    try {
      // Find all the classes which implement the Executor
      // interface.
      possibleClasses =
          ClassFinder.findClassesThatExtend(
              JMeterUtils.getSearchPaths(), new Class[] {getExecutorClass()});

    } catch (Exception e) {
      log.debug("Exception getting interfaces.", e);
    }
    if (possibleClasses.isEmpty()) {
      log.info("No executor Found for : {}", getExecutorClass());
    }

    final JLabel label = new JLabel("Executor ");

    classnameCombo = new JComboBox<>(possibleClasses.toArray(ArrayUtils.EMPTY_STRING_ARRAY));
    classnameCombo.addActionListener(this);
    classnameCombo.setEditable(false);
    label.setLabelFor(classnameCombo);

    HorizontalPanel classNamePanel = new HorizontalPanel();
    classNamePanel.add(label);
    classNamePanel.add(classnameCombo);

    JPanel panel = new JPanel(new BorderLayout(0, 5));
    panel.add(classNamePanel, BorderLayout.NORTH);
    return panel;
  }

  /**
   * Handle action events for this component. This method currently handles events for the classname
   * combo box.
   *
   * @param event the ActionEvent to be handled
   */
  @Override
  public void actionPerformed(ActionEvent event) {
    if (event.getSource() == classnameCombo) { // NOPMD

      String newClassName = ((String) classnameCombo.getSelectedItem()).trim();
      changeExecutor(newClassName);
    }
  }

  @SuppressWarnings("PMD.AvoidCatchingGenericException")
  private void changeExecutor(String executorClass) {

    try {

      Arguments currArgs = new Arguments();
      argsPanel.modifyTestElement(currArgs);
      Map<String, String> currArgsMap = currArgs.getArgumentsAsMap();
      Arguments defaultArgs = extractDefaultArguments(currArgsMap, executorClass);

      className = executorClass;
      argsPanel.configure(defaultArgs);
    } catch (Exception e) {
      log.error("Error getting argument list for {}", executorClass, e);
    }
  }

  private Arguments extractDefaultArguments(Map<String, String> userArgMap, String executorClass) {
    Arguments defaultArgs = new Arguments();
    JavaTestElementExecutor executor = ExecutorUtils.getExecutor(executorClass, new HashMap<>());
    List<String> parametersKey = executor.getParametersKey();
    if (parametersKey == null) {
      return defaultArgs;
    }

    for (String key : parametersKey) {
      defaultArgs.addArgument(key, userArgMap.get(key));
    }

    return defaultArgs;
  }

  /**
   * Create a panel containing components allowing the user to provide arguments to be passed to the
   * test class instance.
   *
   * @return a panel containing the relevant components
   */
  private JPanel createParameterPanel() {
    argsPanel = new ArgumentsPanel("Parameters");

    return argsPanel;
  }

  /**
   * Create a panel containing components allowing the user to provide arguments to be passed to the
   * test class instance.
   *
   * @return a panel containing the relevant components
   */
  private JPanel createAttributePanel() {
    attributePanel =
        new ReadOnlyArgumentsPanel(
            "Attributes", JMeterUtils.getPropDefault("jmc.java.gui.attribute.disabled", true));

    return attributePanel;
  }

  @Override
  public void configure(TestElement config) {
    super.configure(config);

    argsPanel.configure(
        (Arguments) config.getProperty(AbstractJavaTestElement.PARAMETERS).getObjectValue());

    attributePanel.configure(
        ExecutorUtils.extractAttributeArguments((AbstractJavaTestElement<?>) config));
    className = config.getPropertyAsString(AbstractJavaTestElement.EXECUTOR_CLASS);
    if (StringUtils.isNotBlank(className)) {
      if (checkContainsClassName(classnameCombo.getModel(), className)) {
        classnameCombo.setSelectedItem(className);
        changeExecutor(className);
      } else {
        log.error(
            "Error setting class: '{}' in  JavaTestElement: {}, check for a missing jar in"
                + " your jmeter 'search_paths' and 'plugin_dependency_paths' properties",
            className,
            getName());
      }
    } else {
      log.error("Error setting class: null in  JavaTestElement: {}", getName());
    }
  }

  /**
   * Check combo contains className.
   *
   * @param model ComboBoxModel
   * @param className String class name
   * @return boolean true if model contains className
   */
  private static boolean checkContainsClassName(ComboBoxModel<?> model, String className) {
    int size = model.getSize();
    Set<String> set = new HashSet<>(size);
    for (int i = 0; i < size; i++) {
      set.add((String) model.getElementAt(i));
    }
    return set.contains(className);
  }

  @Override
  public void clearGui() {
    super.clearGui();
    argsPanel.clearGui();
    attributePanel.clearGui();
    if (classnameCombo.getItemCount() > 0) {
      classnameCombo.setSelectedIndex(0);
    }
  }
}
