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

import java.util.Optional;
import org.anasoid.jmc.core.wrapper.JmcWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.GenericControllerWrapper.GenericControllerWrapperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.control.RecordingControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementTreeNodeWrapper;
import org.anasoid.jmc.core.wrapper.template.AbstractJmcTemplate;

/** Abstract controller template that include simper in controller. */
public abstract class AbstractSamplerControllerTemplate
    extends AbstractJmcTemplate<
        GenericControllerWrapper<?, ?>, GenericControllerWrapper.GenericControllerWrapperBuilder> {

  @Override
  protected void prepareWrapper(GenericControllerWrapper wrapper) {
    super.prepareWrapper(wrapper);
    if (wrapper.getName() == null) {

      Optional<String> firstSampleName =
          wrapper.getChilds().stream()
              .filter(TestElementTreeNodeWrapper.class::isInstance)
              .findFirst()
              .map(c -> ((TestElementTreeNodeWrapper) c).getName());
      wrapper.setName((firstSampleName.isPresent() ? firstSampleName.get() : "") + " CTRL");
    }
  }

  @Override
  protected JmcWrapperBuilder<GenericControllerWrapperBuilder> init() {
    GenericControllerWrapperBuilder builder = createMainController();

    beforeMainSampler(builder);
    initMainSampler(builder);
    afterMainSampler(builder);

    return builder;
  }

  /**
   * Create Main Controller.
   *
   * @return main controller.
   */
  protected GenericControllerWrapperBuilder createMainController() {

    return RecordingControllerWrapper.builder();
  }

  /** Init Sampler, and customize it. */
  protected abstract void initMainSampler(GenericControllerWrapperBuilder controller);

  /**
   * Before Add Main sampler.
   *
   * @param controller main controller.
   */
  protected void beforeMainSampler(GenericControllerWrapperBuilder controller) {}

  /**
   * After Add Main sampler.
   *
   * @param controller main controller.
   */
  protected void afterMainSampler(GenericControllerWrapperBuilder controller) {}
}
