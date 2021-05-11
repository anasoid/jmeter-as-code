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
 * Date :   12-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.config;

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
import org.anasoid.jmc.core.wrapper.jmc.config.ShareMode;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.core.xstream.types.BooleanManager;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;

/**
 * Wrapper for CSVDataSet.
 *
 * @see CSVDataSet
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.TooManyMethods")
public class CSVDataSetWrapper extends ConfigTestElementWrapper<CSVDataSet, TestBeanGUI>
    implements Validator {

  @XStreamOmitField private static final long serialVersionUID = -1283066246657871689L;

  @XStreamOmitField @Getter @Setter private String filename;

  @XStreamOmitField @Getter @Setter private String resourceFile;

  @JmcProperty("fileEncoding")
  @Getter
  @Setter
  @JmcNullAllowed
  private String fileEncoding;

  @XStreamOmitField @Getter @Default private List<Variable> variables = new ArrayList<>();
  /**
   * Ignore first line of CSV file, it will only be used if Variable Names is not empty, if Variable
   * Names is empty the first line must contain the headers.
   */
  @JmcProperty(value = "ignoreFirstLine", type = BooleanManager.class)
  @Getter
  @Setter
  @Default
  private String ignoreFirstLine = FALSE;
  @JmcProperty("delimiter")
  @Getter
  @Setter
  @Default
  @NonNull
  private String delimiter = ",";
  @JmcProperty(value = "recycle", type = BooleanManager.class)
  @Getter
  @Setter
  @Default
  private String recycle = TRUE;
  @JmcProperty(value = "stopThread", type = BooleanManager.class)
  @Getter
  @Setter
  @Default
  private String stopThread = FALSE;
  @JmcProperty(value = "quotedData", type = BooleanManager.class)
  @Getter
  @Setter
  @Default
  private String quotedData = FALSE;
  @JmcProperty("shareMode")
  @Getter
  @Setter
  @Default
  private String shareMode = ShareMode.SHARE_ALL.value();

  @JmcProperty("filename")
  protected String getFilePath() {
    if (resourceFile != null) {
      URL url = Thread.currentThread().getContextClassLoader().getResource(resourceFile);
      if (url == null) {
        throw new ConversionException("ResourceFile not found : " + resourceFile);
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
  public Class<?> getGuiClass() {
    return TestBeanGUI.class;
  }

  @Override
  public Class<?> getTestClass() {
    return CSVDataSet.class;
  }

  @Override
  public void validate() throws ConversionIllegalStateException { // NOPMD
    if (getFilePath() == null) {
      throw new ConversionIllegalStateException("Filename not provided");
    }

    if (getVariables().isEmpty()) {
      throw new ConversionIllegalStateException("Variables is mandatory");
    }
  }

  /** Builder. */
  @SuppressWarnings("PMD.AvoidThrowingNullPointerException")
  public abstract static class CSVDataSetWrapperBuilder<
          C extends CSVDataSetWrapper, B extends CSVDataSetWrapperBuilder<C, B>>
      extends ConfigTestElementWrapper.ConfigTestElementWrapperBuilder<
          CSVDataSet, TestBeanGUI, C, B> {

    /**
     * Ignore first line of CSV file, it will only be used if Variable Names is not empty, if
     * Variable Names is empty the first line must contain the headers.
     */
    public B withIgnoreFirstLine(Boolean ignoreFirstLine) {
      return withIgnoreFirstLine(String.valueOf(ignoreFirstLine));
    }

    /**
     * Ignore first line of CSV file, it will only be used if Variable Names is not empty, if
     * Variable Names is empty the first line must contain the headers.
     */
    public B withIgnoreFirstLine(Variable ignoreFirstLine) {
      return withIgnoreFirstLine(ignoreFirstLine.getValue());
    }

    /**
     * Ignore first line of CSV file, it will only be used if Variable Names is not empty, if
     * Variable Names is empty the first line must contain the headers.
     */
    public B withIgnoreFirstLine(String ignoreFirstLine) {
      this.ignoreFirstLine$value = ignoreFirstLine;
      this.ignoreFirstLine$set = true;
      return self();
    }

    /** Should the file be re-read from the beginning on reaching EOF? (default is true). */
    public B withRecycle(Boolean recycle) {
      return withRecycle(String.valueOf(recycle));
    }

    /** Should the file be re-read from the beginning on reaching EOF? (default is true). */
    public B withRecycle(Variable recycle) {
      return withRecycle(recycle.getValue());
    }

    /** Should the file be re-read from the beginning on reaching EOF? (default is true). */
    public B withRecycle(String recycle) {
      this.recycle$value = recycle;
      this.recycle$set = true;
      return self();
    }

    /** Should the thread be stopped on EOF, if Recycle is false? (default is false). */
    public B withStopThread(Boolean stopThread) {
      return withStopThread(String.valueOf(stopThread));
    }

    /** Should the thread be stopped on EOF, if Recycle is false? (default is false). */
    public B withStopThread(Variable stopThread) {
      return withStopThread(stopThread.getValue());
    }

    /** Should the thread be stopped on EOF, if Recycle is false? (default is false). */
    public B withStopThread(String stopThread) {
      this.stopThread$value = stopThread;
      this.stopThread$set = true;
      return self();
    }

    /**
     * Should the CSV file allow values to be quoted? If enabled, then values can be enclosed in " -
     * double-quote - allowing values to contain a delimiter. .
     */
    public B withQuotedData(Boolean quotedData) {
      return withQuotedData(String.valueOf(quotedData));
    }

    /**
     * Should the CSV file allow values to be quoted? If enabled, then values can be enclosed in " -
     * double-quote - allowing values to contain a delimiter. .
     */
    public B withQuotedData(Variable quotedData) {
      return withQuotedData(quotedData.getValue());
    }

    /**
     * Should the CSV file allow values to be quoted? If enabled, then values can be enclosed in " -
     * double-quote - allowing values to contain a delimiter. .
     */
    public B withQuotedData(String quotedData) {
      this.quotedData$value = quotedData;
      this.quotedData$set = true;
      return self();
    }

    /**
     * All threads - (the default) the file is shared between all the threads. Current thread group
     * - each file is opened once for each thread group in which the element appears Current thread
     * - each file is opened separately for each thread Identifier - all threads sharing the same
     * identifier share the same file. So for example if you have 4 thread groups, you could use a
     * common id for two or more of the groups to share the file between them. Or you could use the
     * thread number to share the file between the same thread numbers in different thread groups.
     */
    public B withShareMode(ShareMode shareMode) {
      return withShareMode(shareMode.toString());
    }

    /**
     * All threads - (the default) the file is shared between all the threads. Current thread group
     * - each file is opened once for each thread group in which the element appears Current thread
     * - each file is opened separately for each thread Identifier - all threads sharing the same
     * identifier share the same file. So for example if you have 4 thread groups, you could use a
     * common id for two or more of the groups to share the file between them. Or you could use the
     * thread number to share the file between the same thread numbers in different thread groups.
     */
    public B withShareMode(Variable shareMode) {
      return withShareMode(shareMode.getValue());
    }

    /**
     * All threads - (the default) the file is shared between all the threads. Current thread group
     * - each file is opened once for each thread group in which the element appears Current thread
     * - each file is opened separately for each thread Identifier - all threads sharing the same
     * identifier share the same file. So for example if you have 4 thread groups, you could use a
     * common id for two or more of the groups to share the file between them. Or you could use the
     * thread number to share the file between the same thread numbers in different thread groups.
     */
    public B withShareMode(String shareMode) {
      this.shareMode$value = shareMode;
      this.shareMode$set = true;
      return self();
    }

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
