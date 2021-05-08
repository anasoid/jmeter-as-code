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

package org.anasoid.jmc.core.wrapper.jmeter.extractor;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PostProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractScopedTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.anasoid.jmc.core.xstream.annotations.JmcSkipDefault;
import org.apache.commons.collections.CollectionUtils;
import org.apache.jmeter.extractor.XPath2Extractor;
import org.apache.jmeter.extractor.gui.XPath2ExtractorGui;

/**
 * Wrapper for XPath2Extractor.
 *
 * @see XPath2Extractor
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings("PMD.RedundantFieldInitializer")
public class XPath2ExtractorWrapper
    extends AbstractScopedTestElementWrapper<XPath2Extractor, XPath2ExtractorGui>
    implements PostProcessorWrapper<XPath2Extractor> {

  @JmcProperty("XPathExtractor2.refname")
  @Getter
  @Setter
  @JmcMandatory
  private String refName;

  @JmcProperty("XPathExtractor2.xpathQuery")
  @Getter
  @Setter
  @JmcMandatory
  private String xpathQuery;

  /**
   * If the XPath Path query leads to many results, you can choose which one(s) to extract as
   * Variables:
   *
   * <p>0: means random (default value) -1 means extract all results, they will be named as
   * &#x3C;variable name&#x3E;_N (where N goes from 1 to Number of results) X: means extract the Xth
   * result. If this Xth is greater than number of matches, then nothing is returned. Default value
   * will be used
   */
  @JmcProperty("XPathExtractor2.matchNumber")
  @Getter
  @Setter
  @JmcMandatory
  @Default
  private String matchNumber = "0";

  /**
   * Default value returned when no match found. It is also returned if the node has no value and
   * the fragment option is not selected.
   */
  @JmcProperty("XPathExtractor2.default")
  @Getter
  @Setter
  @Default
  private String defaultValue = "";

  /**
   * If selected, the fragment will be returned rather than the text content. For example //title
   * would return "<title>Apache JMeter</title>" rather than "Apache JMeter". In this case,
   * //title/text() would return "Apache JMeter".
   */
  @JmcProperty("XPathExtractor2.fragment")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  private boolean fragment = false;

  /**
   * List of namespaces aliases you want to use to parse the document, one line per declaration. You
   * must specify them as follow: prefix=namespace. This implementation makes it easier to use
   * namespaces than with the old XPathExtractor version.
   */
  @XStreamOmitField @Getter @Setter private List<String> namespaces;

  @JmcProperty("XPathExtractor2.namespaces")
  protected String namespacesProp() {

    if (CollectionUtils.isEmpty(namespaces)) {
      return "";
    }
    return String.join("\n", namespaces);
  }

  @Override
  public Class<?> getGuiClass() {
    return XPath2ExtractorGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return XPath2Extractor.class;
  }

  /** Builder. */
  public abstract static class XPath2ExtractorWrapperBuilder<
          C extends XPath2ExtractorWrapper, B extends XPath2ExtractorWrapperBuilder<C, B>>
      extends AbstractScopedTestElementWrapper.AbstractScopedTestElementWrapperBuilder<
          XPath2Extractor, XPath2ExtractorGui, C, B> {

    protected B withNamespaces(List<String> namespaces) {
      this.namespaces = namespaces;
      return self();
    }

    /** Add namespace . */
    public B addNamespaces(List<String> namespaces) {
      if (this.namespaces == null) {
        withNamespaces(new ArrayList<>());
      }
      this.namespaces.addAll(namespaces);
      return self();
    }

    /** Add namespace . */
    public B addNamespace(String value) {
      return addNamespaces(Arrays.asList(value));
    }
  }
}
