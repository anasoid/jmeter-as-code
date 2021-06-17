package org.anasoid.jmc.test.log;

import org.anasoid.jmc.test.AbstractJmcTest;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Date :   17-Jun-2021
 */

class LogMonitorTest extends AbstractJmcTest {
  private static final Logger LOG = LoggerFactory.getLogger(LogMonitorTest.class);


  @Test
  void testWithError() {
    Throwable ex = new NullPointerException();
    LOG.error("mymessage.err", ex);
    Assertions.assertEquals(1, LogMonitor.getErrors().size());
    Assertions.assertEquals(1, LogMonitor.getLogs().size());
    MonitoredLogLine line = LogMonitor.getLogs().get(0);
    Assertions.assertEquals("mymessage.err", line.getMessage());
    Assertions.assertEquals(ex, line.getThrown());
    Assertions.assertEquals(this.getClass().getName(), line.getLoggerName());
    Assertions.assertEquals(Level.ERROR, line.getLevel());
  }

  @Test
  void testWithoutError() {
    LOG.info("mymessage.info");
    Assertions.assertEquals(0, LogMonitor.getErrors().size());
    Assertions.assertEquals(1, LogMonitor.getLogs().size());
    MonitoredLogLine line = LogMonitor.getLogs().get(0);
    Assertions.assertEquals("mymessage.info", line.getMessage());
    Assertions.assertNull(line.getThrown());
    Assertions.assertEquals(this.getClass().getName(), line.getLoggerName());
    Assertions.assertEquals(Level.INFO, line.getLevel());
  }
}
