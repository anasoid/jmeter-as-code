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
 * Date :   14-Feb-2021
 */

package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CacheManagerWrapperCoreTest extends AbstractJmcCoreTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(CacheManagerWrapper.builder());
  }

  @Test
  void testControlledByThread() throws IOException {

    try {
      CacheManagerWrapper.builder()
          .withControlledByThread(true)
          .withClearEachIteration(false)
          .build();

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testControlledByThreadInverse() throws IOException {

    try {
      CacheManagerWrapper.builder()
          .withClearEachIteration(false)
          .withControlledByThread(true)
          .build();

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testControlledByThreadSuccess() throws IOException {
    try {
      CacheManagerWrapper.builder()
          .withClearEachIteration(true)
          .withControlledByThread(false)
          .build();

      CacheManagerWrapper.builder()
          .withControlledByThread(false)
          .withClearEachIteration(true)
          .build();
    } catch (ConversionIllegalStateException e) {
      Assertions.fail();
    }
  }
}
