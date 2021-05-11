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
 * Date :   25-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.extractor.json.jsonpath;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcNullAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.core.xstream.exceptions.ConversionMandatoryException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.extractor.json.jsonpath.JSONPostProcessor;
import org.apache.jmeter.extractor.json.jsonpath.gui.JSONPostProcessorGui;

/**
 * Wrapper for JSONPostProcessor.
 *
 * @see JSONPostProcessor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
@JmcDefaultName("JSON Extractor")
public class JSONPostProcessorWrapper
    extends AbstractScopedTestElementWrapper<JSONPostProcessor, JSONPostProcessorGui>
    implements PostProcessorWrapper<JSONPostProcessor>, Validator {

  /** JSON-PATH expressions (must match number of variables). */
  @XStreamOmitField @Getter @Setter private List<String> jsonPathExprs;
  /**
   * Semi-colon separated names of variables that will contain the results of JSON-PATH expressions
   * (must match number of JSON-PATH expressions).
   */
  @XStreamOmitField @Getter @Setter private List<Variable> referenceNames;
  /**
   * If the JSON Path query leads to many results, you can choose which one(s) to extract as
   * Variables:
   *
   * <p>0: means random (Default Value) -1 means extract all results, they will be named as
   * &#x3C;variable name&#x3E;_N (where N goes from 1 to Number of results) X: means extract the Xth
   * result. If this Xth is greater than number of matches, then nothing is returned. Default value
   * will be used
   */
  @JmcProperty("JSONPostProcessor.match_numbers")
  @Getter
  @Setter
  @JmcNullAllowed
  private String matchNumber;
  /**
   * Semi-colon separated default values if JSON-PATH expressions do not return any result(must
   * match number of variables).
   */
  @XStreamOmitField @Getter @Setter private List<String> defaultValues;
  /**
   * Default value returned when no match found. It is also returned if the node has no value and
   * the fragment option is not selected.
   */
  @JmcProperty("JSONPostProcessor.compute_concat")
  @JmcSkipDefault("false")
  @Getter
  @Setter
  @Default
  private boolean computeConcat = false;

  @JmcProperty("JSONPostProcessor.jsonPathExprs")
  @JmcMandatory
  protected String jsonPathExprsProp() {

    if (CollectionUtils.isEmpty(jsonPathExprs)) {
      return null;
    }
    return String.join(";", jsonPathExprs);
  }

  @JmcProperty("JSONPostProcessor.referenceNames")
  @JmcMandatory
  protected String referenceNamesProp() {

    if (CollectionUtils.isEmpty(referenceNames)) {
      return null;
    }
    return String.join(
        ";", referenceNames.stream().map(Variable::getName).collect(Collectors.toList()));
  }

  @JmcProperty("JSONPostProcessor.defaultValues")
  protected String defaultValuesProp() {

    if (CollectionUtils.isEmpty(defaultValues)) {
      return null;
    }
    return String.join(";", defaultValues);
  }

  @Override
  public Class<?> getGuiClass() {
    return JSONPostProcessorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return JSONPostProcessor.class;
  }

  @Override
  @SuppressWarnings("PMD.AvoidUncheckedExceptionsInSignatures")
  public void validate() throws ConversionIllegalStateException {
    if (CollectionUtils.isEmpty(referenceNames)) {
      throw new ConversionMandatoryException(this, "referenceNames");
    }
    if (CollectionUtils.isEmpty(jsonPathExprs)) {
      throw new ConversionMandatoryException(this, "referenceNames");
    }
    if (jsonPathExprs.size() != referenceNames.size()) {
      throw new ConversionIllegalStateException(
          "size of jsonPathExprs ["
              + jsonPathExprs
              + "] and referenceNames ["
              + referenceNames
              + "] not equal");
    }

    if (defaultValues != null && defaultValues.size() != referenceNames.size()) {

      throw new ConversionIllegalStateException(
          "size of defaultValues ["
              + defaultValues
              + "] and referenceNames ["
              + referenceNames
              + "] not equal");
    }
  }

  /** builder. */
  public abstract static class JSONPostProcessorWrapperBuilder<
          C extends JSONPostProcessorWrapper, B extends JSONPostProcessorWrapperBuilder<C, B>>
      extends AbstractScopedTestElementWrapper.AbstractScopedTestElementWrapperBuilder<
          JSONPostProcessor, JSONPostProcessorGui, C, B> {

    protected B withReferenceNames(List<Variable> referenceNames) {
      this.referenceNames = referenceNames;
      return self();
    }

    /** Add ReferenceName to List of Variable Name. */
    public B addReferenceNames(List<Variable> variables) {
      if (this.referenceNames == null) {
        withReferenceNames(new ArrayList<>());
      }
      this.referenceNames.addAll(variables);
      return self();
    }

    /** Add referenceNames to List of Variable Name. */
    public B addReferenceName(Variable variable) {
      return addReferenceNames(Arrays.asList(variable));
    }

    protected B withDefaultValues(List<String> defaultValues) {
      this.defaultValues = defaultValues;
      return self();
    }

    /** Add default value to List of default value. */
    public B addDefaultValues(List<String> defaults) {
      if (this.defaultValues == null) {
        withDefaultValues(new ArrayList<>());
      }
      this.defaultValues.addAll(defaults);
      return self();
    }

    /** Add default value. */
    public B addDefaultValue(String value) {
      return addDefaultValues(Arrays.asList(value));
    }

    protected B withJsonPathExprs(List<String> jsonPathExprs) {
      this.jsonPathExprs = jsonPathExprs;
      return self();
    }

    /** Add list of path expr . */
    public B addJsonPathExprs(List<String> defaults) {
      if (this.jsonPathExprs == null) {
        withJsonPathExprs(new ArrayList<>());
      }
      this.jsonPathExprs.addAll(defaults);
      return self();
    }

    /** Add path expr . */
    public B addJsonPathExpr(String value) {
      return addJsonPathExprs(Arrays.asList(value));
    }
  }
}
