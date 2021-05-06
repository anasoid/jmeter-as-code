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
 * Date :   05-May-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmeter.reporters;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.config.JmcConfig;
import org.anasoid.jmeter.as.code.core.util.converters.SampleSaveConfigurationConverter;
import org.anasoid.jmeter.as.code.core.wrapper.jmc.validator.Validator;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.JmeterConstants.JmeterProperty;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.config.ConfigElementWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.SampleListenerWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers.SampleSaveConfigurationWrapper;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcProperty;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcSkipDefault;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionIllegalStateException;
import org.apache.jmeter.gui.AbstractJMeterGuiComponent;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.samplers.SampleSaveConfiguration;
import org.apache.jmeter.testelement.property.ObjectProperty;

/**
 * Wrapper for ResultCollector.
 *
 * @see ResultCollector
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.AvoidUncheckedExceptionsInSignatures"})
public abstract class ResultCollectorWrapper<
        T extends ResultCollector, G extends AbstractJMeterGuiComponent>
    extends AbstractListenerElementWrapper<T, G> implements SampleListenerWrapper<T>, Validator {

  @XStreamOmitField @Default @Getter
  SampleSaveConfigurationWrapper saveConfiguration =
      SampleSaveConfigurationWrapper.builder().build();

  @XStreamOmitField @Getter @Setter String filename;

  @JmcProperty("filename")
  protected String getFilePath() {

    if (filename == null) {
      return "";
    }
    return JmcConfig.getResultRootFolder() + filename;
  }

  @JmcProperty("ResultCollector.error_logging")
  @Getter
  @Setter
  @Default
  boolean logError = false;

  @JmcProperty("useGroupName")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  boolean useGroupName = false;

  @JmcProperty("saveHeaders")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.TRUE)
  boolean saveHeaders = true;

  @JmcProperty("ResultCollector.success_only_logging")
  @Getter
  @Setter
  @Default
  @JmcSkipDefault(ConfigElementWrapper.FALSE)
  boolean logSuccess = false;

  @JmcMethodAlias(JmeterProperty.OBJECT_PROP)
  protected ObjectProperty getSaveConfig() {
    SampleSaveConfigurationConverter converter = new SampleSaveConfigurationConverter();
    SampleSaveConfiguration saveConfig = converter.convert(saveConfiguration);
    return new ObjectProperty("saveConfig", saveConfig);
  }

  @Override
  public void validate() throws ConversionIllegalStateException {
    if (logError && logSuccess) {

      throw new ConversionIllegalStateException(
          "logError && logSuccess can't be used at the same times");
    }
  }
}
