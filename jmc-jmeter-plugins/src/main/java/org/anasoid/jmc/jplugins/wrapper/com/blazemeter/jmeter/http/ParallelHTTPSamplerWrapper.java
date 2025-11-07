package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.http;

import com.blazemeter.jmeter.http.ParallelHTTPSampler;
import com.blazemeter.jmeter.http.ParallelHTTPSamplerGui;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmeter.protocol.http.util.HTTPArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.samplers.AbstractSamplerWrapper;
import org.anasoid.jmc.core.xstream.annotations.JmcCollection;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcEmptyAllowed;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.anasoid.jmc.core.xstream.annotations.JmcProperty;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerBase;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.StringProperty;

/**
 * Wrapper for ParallelHTTPSampler.
 *
 * @see ParallelHTTPSampler
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("jp@gc - Parallel HTTP Requests")
public class ParallelHTTPSamplerWrapper
    extends AbstractSamplerWrapper<ParallelHTTPSampler, ParallelHTTPSamplerGui> {

  @Getter @XStreamOmitField private List<String> urls;

  @JmcMethodAlias("collectionProp")
  protected CollectionProperty urlsProperties() {

    CollectionProperty rows =
        new CollectionProperty(ParallelHTTPSampler.DATA_PROPERTY, new ArrayList<>());

    if (urls != null) {
      urls.forEach(
          s -> {
            List<StringProperty> row = new ArrayList<>();
            row.add(new StringProperty(Integer.toString(s.hashCode()), s));
            rows.addItem(row);
          });
    }
    return rows;
  }

  /** read only imageParser. */
  @JmcProperty(HTTPSamplerBase.IMAGE_PARSER)
  @Getter
  @Default
  @SuppressWarnings({"PMD.ImmutableField"})
  private Boolean imageParser = true;

  /** read only concurrentDwn. */
  @JmcProperty(HTTPSamplerBase.CONCURRENT_DWN)
  @Getter
  @Default
  @SuppressWarnings({"PMD.ImmutableField"})
  private Boolean concurrentDwn = true;

  /** read only arguments. */
  @JmcCollection(
      value = Arguments.ARGUMENTS,
      withElementProp = true,
      name = "HTTPsampler.Arguments",
      elementType = Arguments.class,
      enabled = false)
  @JmcMethodAlias("arguments")
  @JmcEmptyAllowed
  protected final List<HTTPArgumentWrapper> getArgumentsUser() {
    return new ArrayList<>(); // NOSONAR
  }

  @Override
  public Class<?> getGuiClass() {
    return ParallelHTTPSamplerGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return ParallelHTTPSampler.class;
  }

  /** builder. */
  public abstract static class ParallelHTTPSamplerWrapperBuilder<
          C extends ParallelHTTPSamplerWrapper, B extends ParallelHTTPSamplerWrapperBuilder<C, B>>
      extends AbstractSamplerWrapper.AbstractSamplerWrapperBuilder<
          ParallelHTTPSampler, ParallelHTTPSamplerGui, C, B> {

    /**
     * Add Schedule line.
     *
     * @param url url.
     */
    public B addUrl(String url) {
      if (this.urls == null) {
        withUrls(new ArrayList<>());
      }
      this.urls.add(url);
      return self();
    }

    protected B withUrls(List<String> urls) {
      this.urls = urls;
      return self();
    }
  }
}
