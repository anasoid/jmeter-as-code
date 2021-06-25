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
 * Date :   16-Jun-2021
 */

package org.anasoid.jmc.test.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.Level;

/**
 * Class to monitor Errors and message in log. Appender {@link MonitorLogAppender} should be
 * configured in log4j2
 */
public final class LogMonitor {
  private static final List<MonitoredLogLine> LOGS_LINE = new ArrayList<>();

  private LogMonitor() {}

  protected static void add(MonitoredLogLine line) {
    LOGS_LINE.add(line);
  }

  /** reset errors. */
  public static void reset() {
    LOGS_LINE.clear();
  }

  public static List<MonitoredLogLine> getLogs() {
    return Collections.unmodifiableList(LOGS_LINE);
  }

  /** get List of logged Errors. */
  public static List<MonitoredLogLine> getErrors() {
    return LOGS_LINE.stream()
        .filter(c -> Level.ERROR.equals(c.getLevel()))
        .collect(Collectors.toList());
  }
}
