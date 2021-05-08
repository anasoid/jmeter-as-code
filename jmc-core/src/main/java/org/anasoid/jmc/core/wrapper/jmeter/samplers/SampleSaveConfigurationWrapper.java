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

package org.anasoid.jmc.core.wrapper.jmeter.samplers;

import java.io.Serializable;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Wrapper for SampleSaveConfiguration.
 *
 * @see org.apache.jmeter.samplers.SampleSaveConfiguration
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.TooManyFields"})
public class SampleSaveConfigurationWrapper implements Serializable {

  @Getter @Setter @Default private boolean time = true;
  @Getter @Setter @Default private boolean latency = true;
  @Getter @Setter @Default private boolean timestamp = true;
  @Getter @Setter @Default private boolean success = true;
  @Getter @Setter @Default private boolean label = true;
  @Getter @Setter @Default private boolean code = true;
  @Getter @Setter @Default private boolean message = true;
  @Getter @Setter @Default private boolean threadName = true;
  @Getter @Setter @Default private boolean dataType = true;
  @Getter @Setter @Default private boolean encoding = false;
  @Getter @Setter @Default private boolean assertions = true;
  @Getter @Setter @Default private boolean subresults = true;
  @Getter @Setter @Default private boolean responseData = false;
  @Getter @Setter @Default private boolean samplerData = false;
  @Getter @Setter @Default private boolean xml = false;
  @Getter @Setter @Default private boolean fieldNames = true;
  @Getter @Setter @Default private boolean responseHeaders = false;
  @Getter @Setter @Default private boolean requestHeaders = false;
  @Getter @Setter @Default private boolean assertionResultsFailureMessage = true;
  @Getter @Setter @Default private boolean bytes = true;
  @Getter @Setter @Default private boolean sentBytes = true;
  @Getter @Setter @Default private boolean url = true;
  @Getter @Setter @Default private boolean threadCounts = true;
  @Getter @Setter @Default private boolean idleTime = true;
  @Getter @Setter @Default private boolean connectTime = true;

  @Getter @Setter @Default private boolean fileName = false;
  @Getter @Setter @Default private boolean hostname = false;
  @Getter @Setter @Default private boolean sampleCount = false;
}
