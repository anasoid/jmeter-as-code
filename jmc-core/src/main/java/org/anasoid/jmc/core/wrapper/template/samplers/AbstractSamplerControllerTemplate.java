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
 * Date :   17-May-2021
 */

package org.anasoid.jmc.core.wrapper.template.samplers;

import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.RecordingControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementTreeNodeWrapper;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;

/** Abstract controller template that include simper in controller. */
public abstract class AbstractSamplerControllerTemplate
    extends AbstractJmcTemplate<RecordingControllerWrapper> {

  @Override
  protected void prepareBuilder(JmcWrapperBuilder<RecordingControllerWrapper> builder) {
    super.prepareBuilder(builder);
  }

  @Override
  protected void prepareWrapper(RecordingControllerWrapper wrapper) {
    super.prepareWrapper(wrapper);
    if (wrapper.getName() == null) {
      wrapper.setName(
          wrapper.getChilds().stream()
                  .filter(c -> c instanceof TestElementTreeNodeWrapper)
                  .findFirst()
                  .map(c -> ((TestElementTreeNodeWrapper) c).getName())
                  .get()
              + " CTRL");
    }
  }

  @Override
  protected JmcWrapperBuilder<RecordingControllerWrapper> init() {
    RecordingControllerWrapper.RecordingControllerWrapperBuilder builder =
        RecordingControllerWrapper.builder();

    beforeMainSampler(builder);
    initMainSampler(builder);
    afterMainSampler(builder);

    return builder;
  }

  /** Init Sampler, and customize it. */
  protected abstract void initMainSampler(
      RecordingControllerWrapper.RecordingControllerWrapperBuilder controller);

  /**
   * Before Add Main sampler.
   *
   * @param controller main controller.
   */
  protected void beforeMainSampler(
      RecordingControllerWrapper.RecordingControllerWrapperBuilder controller) {}

  /**
   * After Add Main sampler.
   *
   * @param controller main controller.
   */
  protected void afterMainSampler(
      RecordingControllerWrapper.RecordingControllerWrapperBuilder controller) {}
}
