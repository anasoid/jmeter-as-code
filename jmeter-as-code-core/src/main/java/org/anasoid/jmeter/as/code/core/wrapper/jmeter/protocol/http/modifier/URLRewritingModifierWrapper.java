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
 * Date :   24-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.modifier;

import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMandatory;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.protocol.http.modifier.URLRewritingModifier;
import org.apache.jmeter.protocol.http.modifier.gui.URLRewritingModifierGui;

/**
 * Wrapper for URLRewritingModifier. HTML Link Parser.
 *
 * @see URLRewritingModifier
 */
@SuppressWarnings("PMD.RedundantFieldInitializer")
@SuperBuilder(setterPrefix = "with", toBuilder = true)
public class URLRewritingModifierWrapper
    extends AbstractBasicChildTestElementWrapper<URLRewritingModifier>
    implements JMeterGUIWrapper<URLRewritingModifierGui>,
        PreProcessorWrapper<URLRewritingModifier> {

  /**
   * The name of the parameter to grab from previous response. This modifier will find the parameter
   * anywhere it exists on the page, and grab the value assigned to it, whether it's in an HREF or a
   * form.
   */
  @JmcProperty("argument_name")
  @Getter
  @Setter
  @JmcMandatory
  private String argumentName;

  /**
   * Some web apps rewrite URLs by appending a semi-colon plus the session id parameter. Check this
   * box if that is so.
   */
  @JmcProperty("path_extension")
  @Getter
  @Setter
  @Default
  private boolean pathExtension = false;

  /**
   * Some web apps rewrite URLs without using an "=" sign between the parameter name and value (such
   * as Intershop Enfinity).
   */
  @JmcProperty("path_extension_no_equals")
  @Getter
  @Setter
  @Default
  private boolean pathExtensionNoEquals = false;

  /** Prevents the query string to end up in the path extension (such as Intershop Enfinity). */
  @JmcProperty("path_extension_no_questionmark")
  @Getter
  @Setter
  @Default
  private boolean pathExtensionNoQuestionmark = false;

  /**
   * Should the value of the session Id be saved for later use when the session Id is not present.
   */
  @JmcProperty("cache_value")
  @Getter
  @Setter
  @Default
  private boolean cache = false;

  /** URL Encode value when writing parameter. */
  @JmcProperty("encode")
  @Getter
  @Setter
  @Default
  private boolean encode = false;

  @Override
  public Class<URLRewritingModifierGui> getGuiClass() {
    return URLRewritingModifierGui.class;
  }

  @Override
  public Class<URLRewritingModifier> getTestClass() {
    return URLRewritingModifier.class;
  }
}
