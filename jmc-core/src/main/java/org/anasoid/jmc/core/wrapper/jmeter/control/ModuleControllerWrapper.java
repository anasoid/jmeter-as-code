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
 * Date :   07-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.control;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.JmeterConstants.JmeterProperty;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.jmeter.control.ModuleController;
import org.apache.jmeter.control.gui.ModuleControllerGui;
import org.apache.jmeter.testelement.property.CollectionProperty;

/** Wrapper for Module Controller. */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("Module Controller")
public class ModuleControllerWrapper extends AbstractTestElementWrapper<ModuleController>
    implements JMeterGUIWrapper<ModuleControllerGui>, ControllerWrapper<ModuleController> {

  @XStreamOmitField @Getter @Setter private ControllerWrapper<?> module;
  @XStreamOmitField @Getter @Setter private List<String> path;
  @XStreamOmitField @Getter @Setter private String rootParent;

  @XStreamOmitField @Setter private List<String> nodePath;

  @Override
  public Class<?> getGuiClass() {
    return ModuleControllerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ModuleController.class;
  }

  @JmcMethodAlias(JmeterProperty.COLLECTION_PROP)
  protected CollectionProperty getNodePath() {
    return new CollectionProperty("ModuleController.node_path", nodePath);
  }

  @Override
  @SuppressWarnings("PMD.AvoidDeeplyNestedIfStmts")
  protected void internalInit() {
    super.internalInit();
    String nameTarget = CollectionUtils.isNotEmpty(path) ? path.get(path.size() - 1) : null;
    if (getModule() instanceof AbstractTestElementWrapper) {
      AbstractTestElementWrapper currentModule = (AbstractTestElementWrapper) getModule();
      nameTarget = currentModule.getName();
    }
    if (nameTarget != null) {
      if (getName() == null) {
        setName("MD-> " + nameTarget);
      }
      if (getComment() == null) {
        setComment("MD-> " + nameTarget);
      }
    }
  }

  /** Builder. */
  public abstract static class ModuleControllerWrapperBuilder<
          C extends ModuleControllerWrapper, B extends ModuleControllerWrapperBuilder<C, B>>
      extends AbstractTestElementWrapper.AbstractTestElementWrapperBuilder<ModuleController, C, B> {

    private B withPath(List<String> path) {
      this.path = path;
      return self();
    }

    private void withNodePath(List<String> nodePath) {} // NOPMD - Hide Lombock generated method.

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param path node names to target node.
     */
    public B withModule(String rootParent, List<String> path) {
      withPath(path);
      withRootParent(rootParent);
      return self();
    }

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param path node names to target node.
     */
    public B withModule(TestFragmentWrapper rootParent, List<String> path) {
      String name = rootParent.getName();
      if (name == null) {
        name = TestFragmentWrapper.DEFAULT_NAME;
      }
      return withModule(name, path);
    }

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param path node names to target node.
     */
    public B withModule(String rootParent, String firstNode, String... path) {
      return withModule(
          rootParent, (List<String>) ListUtils.sum(Arrays.asList(firstNode), Arrays.asList(path)));
    }

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param path node names to target node.
     */
    public B withModule(TestFragmentWrapper rootParent, String firstNode, String... path) {
      String name = rootParent.getName();
      if (name == null) {
        name = TestFragmentWrapper.DEFAULT_NAME;
      }
      return withModule(
          name, (List<String>) ListUtils.sum(Arrays.asList(firstNode), Arrays.asList(path)));
    }

    /**
     * set module.
     *
     * @param module target module.
     */
    public B withModule(ControllerWrapper<?> module) {
      this.module = module;
      return self();
    }

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param module module.
     */
    public B withModule(TestFragmentWrapper rootParent, ControllerWrapper<?> module) {
      String name = rootParent.getName();
      if (name == null) {
        name = TestFragmentWrapper.DEFAULT_NAME;
      }
      return withModule(name, module);
    }

    /**
     * set module with root parent.
     *
     * @param rootParent rootParent root parent of module, can be not direct parent.
     * @param module module.
     */
    public B withModule(String rootParent, ControllerWrapper<?> module) {
      withModule(module);
      withRootParent(rootParent);
      return self();
    }

    protected B withRootParent(String rootParent) {
      this.rootParent = rootParent;
      return self();
    }
  }
}
