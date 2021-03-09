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
 * Date :   09-Mar-2021
 */

package org.anasoid.jmeter.as.code.core.dsl.threads;

import org.anasoid.jmeter.as.code.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThreadGroupDslTest {

  @Test
  void testArgument() {

    ThreadGroupWrapper threadGroup = ThreadGroupDsl.threadGroupBuilder("name", 10, 20, 30).build();

    Assertions.assertEquals("name", threadGroup.getName());
    Assertions.assertEquals(10, threadGroup.getNumThreads());
    Assertions.assertEquals(20, threadGroup.getRampUp());
    Assertions.assertEquals(30, threadGroup.getLoops());
  }
}
