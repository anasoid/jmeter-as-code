package org.anasoid.jmeter.as.code.core.wrapper.jmeter.samplers;

import lombok.experimental.SuperBuilder;
import org.anasoid.jmeter.as.code.core.wrapper.converter.annotation.Mandatory;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.gui.JMeterGUIWrapper;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;

@SuperBuilder(setterPrefix = "with")
@Mandatory(fields = {"name"})
public abstract class AbstractSamplerWrapper<
        T extends AbstractSampler, G extends AbstractSamplerGui>
    extends AbstractTestElementWrapper<T> implements JMeterGUIWrapper<G> {}
