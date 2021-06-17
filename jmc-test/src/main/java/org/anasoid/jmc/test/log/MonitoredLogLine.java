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

import org.apache.logging.log4j.Level;

/** Monitored log line container. */
public class MonitoredLogLine {

  private final String loggerName;
  private final String message;
  private final Level level;
  private final Throwable throwable;

  protected MonitoredLogLine(String loggerName, String message, Level level, Throwable throwable) {
    this.loggerName = loggerName;
    this.message = message;
    this.level = level;
    this.throwable = throwable;
  }

  public String getLoggerName() {
    return loggerName;
  }

  public String getMessage() {
    return message;
  }

  public Level getLevel() {
    return level;
  }

  public Throwable getThrowable() {
    return throwable;
  }

  @Override
  public String toString() {
    return "MonitoredLogLine{"
        + "loggerName='"
        + loggerName
        + '\''
        + ", message='"
        + message
        + '\''
        + ", level="
        + level
        + ", throwable="
        + throwable
        + '}';
  }
}
