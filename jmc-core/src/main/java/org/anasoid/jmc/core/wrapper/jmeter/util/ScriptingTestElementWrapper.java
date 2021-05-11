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
 * Date :   21-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.util;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.config.JmcConfig;
import org.anasoid.jmc.core.util.FileUtils;
import org.anasoid.jmc.core.wrapper.jmc.script.ScriptLanguage;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.util.ScriptingTestElement;

/**
 * Wrapper for ScriptingTestElement.
 *
 * @see ScriptingTestElement
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.AbstractClassWithoutAnyMethod", "PMD.AvoidUncheckedExceptionsInSignatures"})
public abstract class ScriptingTestElementWrapper<
        T extends ScriptingTestElement, G extends AbstractJMeterGuiComponent>
    extends AbstractBasicChildTestElementWrapper<T> implements JMeterGUIWrapper<G>, Validator {

  /**
   * Parameters to pass to the BeanShell script. The list is passed to script split on white-space.
   */
  @XStreamOmitField @Getter @Default private final List<String> parameters = new ArrayList<>();

  @JmcProperty(value = "scriptLanguage")
  @Getter
  @Default
  private final String scriptLanguage = ScriptLanguage.GROOVY.value();

  @XStreamOmitField @Getter private final String scriptFile;

  /** Script file resource. the resource file will be included as content in test. */
  @XStreamOmitField @Default @Getter
  private final boolean scriptFileResource = JmcConfig.isScriptResource();

  @Override
  public void validate() throws ConversionIllegalStateException {
    if (scriptFile == null) {
      throw new ConversionIllegalStateException("ScriptFile is mandatory");
    }
  }

  @JmcProperty("filename")
  protected String filenameField() {
    if (isScriptFileResource()) {
      return "";
    }
    return JmcConfig.getScriptRootFolder() + scriptFile;
  }

  @JmcProperty("script")
  protected String scriptField() {
    if (!isScriptFileResource()) {
      return "";
    }
    return FileUtils.readResource(getScriptFile());
  }

  @JmcProperty("parameters")
  protected String parametersField() {
    if (parameters.isEmpty()) {
      return "";
    }
    return String.join(" ", parameters);
  }

  /** Builder. */
  public abstract static class ScriptingTestElementWrapperBuilder<
          T extends ScriptingTestElement,
          G extends AbstractJMeterGuiComponent,
          C extends ScriptingTestElementWrapper<T, G>,
          B extends ScriptingTestElementWrapperBuilder<T, G, C, B>>
      extends AbstractBasicChildTestElementWrapper.AbstractBasicChildTestElementWrapperBuilder<
          T, C, B> {

    /** The JSR223 language to be used. */
    public B withScriptLanguage(String scriptLanguage) {
      this.scriptLanguage$value = scriptLanguage;
      this.scriptLanguage$set = true;
      return self();
    }

    /** The JSR223 language to be used. */
    public B withScriptLanguage(ScriptLanguage scriptLanguage) {
      return this.withScriptLanguage(scriptLanguage.value());
    }

    protected B withParameters(List<String> parameters) {
      this.parameters$value = parameters;
      this.parameters$set = true;
      return self();
    }

    /** Add Paramete to List of Variable Name. */
    public B addParameters(List<String> parameters) {
      if (!this.parameters$set) {
        withParameters(new ArrayList<>());
      }
      this.parameters$value.addAll(parameters);
      return self();
    }

    /** Add Parameter to List of parameters. */
    public B addParameter(String parameter) {
      return addParameters(Arrays.asList(parameter));
    }
  }
}
