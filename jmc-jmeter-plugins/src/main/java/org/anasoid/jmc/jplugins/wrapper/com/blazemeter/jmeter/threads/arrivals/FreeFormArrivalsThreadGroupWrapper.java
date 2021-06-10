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
 * Date :   09-Jun-2021
 */

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads.arrivals;

import com.blazemeter.jmeter.threads.arrivals.FreeFormArrivalsThreadGroup;
import com.blazemeter.jmeter.threads.arrivals.FreeFormArrivalsThreadGroupGui;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.testelement.property.AbstractRowData;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcHiddenFields;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.apache.jmeter.testelement.property.CollectionProperty;

/**
 * Wrapper for FreeFormArrivalsThreadGroup.
 *
 * @see FreeFormArrivalsThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("bzm - Free-Form Arrivals Thread Group")
@JmcHiddenFields({"rampUp", "steps", "hold"})
public class FreeFormArrivalsThreadGroupWrapper extends ArrivalsThreadGroupWrapper {

  @Getter @XStreamOmitField List<Line> schedule;

  @JmcMethodAlias("collectionProp")
  protected CollectionProperty schedules() {

    return new CollectionProperty(
        "Schedule",
        schedule == null
            ? new ArrayList<>()
            : schedule.stream().map(Line::asProperty).collect(Collectors.toList()));
  }

  @Override
  public Class<?> getGuiClass() {
    return FreeFormArrivalsThreadGroupGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return FreeFormArrivalsThreadGroup.class;
  }

  /** Line in table. */
  protected static class Line extends AbstractRowData {
    private final String start;
    private final String end;
    private final String duration;

    /** Line in table. */
    public Line(String start, String end, String duration) {
      this.start = start;
      this.end = end;
      this.duration = duration;
    }

    @Override
    public List<String> asList() {
      List<String> results = new ArrayList<>();
      results.add(start);
      results.add(end);
      results.add(duration);
      return results;
    }
  }

  /** Builder. */
  public abstract static class FreeFormArrivalsThreadGroupWrapperBuilder<
          C extends FreeFormArrivalsThreadGroupWrapper,
          B extends FreeFormArrivalsThreadGroupWrapperBuilder<C, B>>
      extends ArrivalsThreadGroupWrapper.ArrivalsThreadGroupWrapperBuilder<C, B> {
    private List<FreeFormArrivalsThreadGroupWrapper.Line> schedule;

    protected B withSchedule(List<FreeFormArrivalsThreadGroupWrapper.Line> schedule) {
      this.schedule = schedule;
      return self();
    }

    /**
     * Add Schedule line.
     *
     * @param start Start value.
     * @param end End value.
     * @param duration Duration value.
     */
    public B addSchedule(int start, int end, int duration) {
      return addSchedule(String.valueOf(start), String.valueOf(end), String.valueOf(duration));
    }

    /**
     * Add Schedule line.
     *
     * @param start Start value.
     * @param end End value.
     * @param duration Duration value.
     */
    public B addSchedule(String start, String end, String duration) {
      if (this.schedule == null) {
        withSchedule(new ArrayList<>());
      }
      this.schedule.add(new Line(start, end, duration));
      return self();
    }
  }
}
