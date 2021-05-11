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

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.modifier;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.processor.PreProcessorWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.basic.AbstractBasicChildTestElementWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.apache.jmeter.protocol.http.modifier.AnchorModifier;
import org.apache.jmeter.protocol.http.modifier.gui.AnchorModifierGui;

/**
 * Wrapper for AnchorModifier. HTML Link Parser.
 *
 * @see AnchorModifier
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("HTML Link Parser")
public class AnchorModifierWrapper extends AbstractBasicChildTestElementWrapper<AnchorModifier>
    implements JMeterGUIWrapper<AnchorModifierGui>, PreProcessorWrapper<AnchorModifier> {

  @Override
  public Class<?> getGuiClass() {
    return AnchorModifierGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return AnchorModifier.class;
  }
}
