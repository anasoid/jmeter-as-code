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

package org.anasoid.jmc.jplugins.wrapper.com.blazemeter.jmeter.threads;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import kg.apc.jmeter.threads.UltimateThreadGroup;
import kg.apc.jmeter.threads.UltimateThreadGroupGui;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.anasoid.jmc.core.wrapper.jmc.testelement.property.AbstractRowData;
import org.anasoid.jmc.core.xstream.annotations.JmcDefaultName;
import org.anasoid.jmc.core.xstream.annotations.JmcHiddenFields;
import org.anasoid.jmc.core.xstream.annotations.JmcMethodAlias;
import org.apache.jmeter.testelement.property.CollectionProperty;

/**
 * Wrapper for UltimateThreadGroup.
 *
 * @see UltimateThreadGroup
 */
@SuperBuilder(setterPrefix = "with", toBuilder = true)
@JmcDefaultName("jp@gc - Ultimate Thread Group")
@JmcHiddenFields({"numThreads", "isSameUserOnNextIteration"})
public class UltimateThreadGroupWrapper
    extends AbstractSimpleThreadGroupWrapper<UltimateThreadGroup, UltimateThreadGroupGui> {

  @Getter @XStreamOmitField private List<Line> threadsSchedule;

  @JmcMethodAlias("collectionProp")
  protected CollectionProperty schedules() {
    return new CollectionProperty(
        "ultimatethreadgroupdata",
        threadsSchedule == null
            ? new ArrayList<>()
            : threadsSchedule.stream().map(Line::asProperty).collect(Collectors.toList()));
  }

  @Override
  public void internalInit() {
    super.internalInit();
    // Hide numThreads
    setNumThreads(null);
    setIsSameUserOnNextIteration(null);
  }

  @Override
  public Class<?> getGuiClass() {
    return UltimateThreadGroupGui.class;
  }

  @Override
  public Class<?> getTestClass() {
    return UltimateThreadGroup.class;
  }

  /** Line in table. */
  protected static class Line extends AbstractRowData {
    private final String startThreadCount;
    private final String initialDelay;
    private final String startupTime;
    private final String holdLoadFor;
    private final String shutDownTime;

    public Line(
        String startThreadCount,
        String initialDelay,
        String startupTime,
        String holdLoadFor,
        String shutDownTime) {
      this.startThreadCount = startThreadCount;
      this.initialDelay = initialDelay;
      this.startupTime = startupTime;
      this.holdLoadFor = holdLoadFor;
      this.shutDownTime = shutDownTime;
    }

    @Override
    public List<String> asList() {
      List<String> results = new ArrayList<>();
      results.add(startThreadCount);
      results.add(initialDelay);
      results.add(startupTime);
      results.add(holdLoadFor);
      results.add(shutDownTime);
      return results;
    }
  }

  /** Builder. */
  @SuppressWarnings("PMD.UseObjectForClearerAPI")
  public abstract static class UltimateThreadGroupWrapperBuilder<
          C extends UltimateThreadGroupWrapper, B extends UltimateThreadGroupWrapperBuilder<C, B>>
      extends AbstractSimpleThreadGroupWrapper.AbstractSimpleThreadGroupWrapperBuilder<
          UltimateThreadGroup, UltimateThreadGroupGui, C, B> {

    protected B withThreadsSchedule(List<Line> threadsSchedule) {
      this.threadsSchedule = threadsSchedule;
      return self();
    }

    /**
     * Add Threads Schedule line.
     *
     * @param startThreadCount Start Thread Count,
     * @param initialDelay Initial Delay.
     * @param startupTime Startup Time.
     * @param holdLoadFor Hold LoadF or
     * @param shutDownTime ShutDownT ime.
     */
    public B addThreadsSchedule(
        int startThreadCount,
        int initialDelay,
        int startupTime,
        int holdLoadFor,
        int shutDownTime) {
      return addThreadsSchedule(
          String.valueOf(startThreadCount),
          String.valueOf(initialDelay),
          String.valueOf(startupTime),
          String.valueOf(holdLoadFor),
          String.valueOf(shutDownTime));
    }

    /**
     * Add Threads Schedule line.
     *
     * @param startThreadCount Start Thread Count,
     * @param initialDelay Initial Delay.
     * @param startupTime Startup Time.
     * @param holdLoadFor Hold LoadF or
     * @param shutDownTime ShutDownT ime.
     */
    public B addThreadsSchedule(
        String startThreadCount,
        String initialDelay,
        String startupTime,
        String holdLoadFor,
        String shutDownTime) {
      if (this.threadsSchedule == null) {
        withThreadsSchedule(new ArrayList<>());
      }
      this.threadsSchedule.add(
          new Line(startThreadCount, initialDelay, startupTime, holdLoadFor, shutDownTime));
      return self();
    }
  }
}
