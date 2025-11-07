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
 * Date :   07-Jul-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.config;

import com.blazemeter.jmeter.RandomCSVDataSetConfig;
import com.blazemeter.jmeter.RandomCSVDataSetConfigGui;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.config.JmcConfig;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.CSVDataSet;

/**
 * Wrapper for CSVDataSet.
 *
 * @see CSVDataSet
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("bzm - Random CSV Data Set Config")
public class RandomCSVDataSetWrapper
    extends ConfigTestElementWrapper<RandomCSVDataSetConfig, RandomCSVDataSetConfigGui>
    implements Validator {

  @XStreamOmitField private static final long serialVersionUID = 5901529684597605018L;
  @XStreamOmitField @Getter @Default private final List<Variable> variables = new ArrayList<>();
  @XStreamOmitField @Getter @Setter private String filename;

  /** Include file name from resource folder, use only when executing from source code. */
  @XStreamOmitField @Getter @Setter @Default boolean asResourceFile = JmcConfig.isDataResource();

  @JmcProperty("fileEncoding")
  @Getter
  @Setter
  @Default
  private String fileEncoding = "UTF-8";

  /**
   * Ignore first line of CSV file, it will only be used if Variable Names is not empty, if Variable
   * Names is empty the first line must contain the headers.
   */
  @JmcProperty(value = "ignoreFirstLine")
  @Getter
  @Setter
  @Default
  private Boolean ignoreFirstLine = false;

  @JmcProperty("delimiter")
  @Getter
  @Setter
  @Default
  @NonNull
  private String delimiter = ",";

  /**
   * Random order - The plugin will get records from the file in random order. This is the part that
   * provides this element’s added value. If you don’t select this option, the element will work
   * like the regular CSV Data Set Config.
   */
  @JmcProperty(value = "randomOrder")
  @Getter
  @Setter
  @Default
  private Boolean randomOrder = true;

  /**
   * Rewind on end of list - if the flag is selected and an iteration loop has reached the end, the
   * new loop will be started.
   */
  @JmcProperty(value = "rewindOnTheEndOfList")
  @Getter
  @Setter
  @Default
  private Boolean recycle = true;

  @JmcProperty("independentListPerThread")
  @Getter
  @Setter
  @Default
  private Boolean independentListPerThread = false;

  @JmcProperty("filename")
  protected String getFilePath() {
    if (filename == null) {
      return null;
    }
    if (asResourceFile) {
      URL url = Thread.currentThread().getContextClassLoader().getResource(filename);
      if (url == null) {
        throw new ConversionException("ResourceFile not found : " + filename);
      }
      return url.getFile();
    }

    return JmcConfig.getDataRootFolder() + filename;
  }

  @JmcProperty("variableNames")
  protected String getVariableNames() {
    if (variables.isEmpty()) {
      return "";
    }
    return String.join(",", variables.stream().map(Variable::getName).collect(Collectors.toList()));
  }

  @Override
  public void validate() throws ConversionIllegalStateException { // NOPMD
    if (StringUtils.isBlank(getFilePath())) {
      throw new ConversionIllegalStateException("Filename not provided");
    }

    if (getVariables().isEmpty()) {
      throw new ConversionIllegalStateException("Variables is mandatory");
    }
  }

  @Override
  public Class<?> getGuiClass() {
    return RandomCSVDataSetConfigGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return RandomCSVDataSetConfig.class;
  }

  /** Builder. */
  public abstract static class RandomCSVDataSetWrapperBuilder<
          C extends RandomCSVDataSetWrapper,
          B extends RandomCSVDataSetWrapper.RandomCSVDataSetWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          RandomCSVDataSetConfig, RandomCSVDataSetConfigGui, C, B> {

    /** hide method , generated by Lombok. */
    protected B withVariables(List<Variable> variables) {
      this.variables$value = variables;
      this.variables$set = true;
      return self();
    }

    /** Add variable to List of Variable Name. */
    public B addvariables(List<Variable> variables) {
      if (!this.variables$set) {
        withVariables(new ArrayList<>());
      }
      this.variables$value.addAll(variables);
      return self();
    }

    /** Add variable to List of Variable Name. */
    public B addvariable(Variable variable) {
      return addvariables(Arrays.asList(variable));
    }
  }
}
