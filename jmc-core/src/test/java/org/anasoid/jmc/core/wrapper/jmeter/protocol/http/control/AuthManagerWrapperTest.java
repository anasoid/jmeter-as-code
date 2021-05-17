package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
import org.anasoid.jmc.test.utils.SetterTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
 * Date :   11-Apr-2021
 */

class AuthManagerWrapperTest {

  @Test
  void testSetter()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    SetterTestUtils.testSetter(AuthManagerWrapper.builder());
  }

  @Test
  void testControlledByThread() throws IOException {

    try {
      AuthManagerWrapper.builder()
          .withControlledByThread(true)
          .withClearEachIteration(false)
          .build();

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testControlledByThreadInverse() {

    try {
      AuthManagerWrapper.builder()
          .withClearEachIteration(false)
          .withControlledByThread(true)
          .build();

      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      // Nothing
    }
  }

  @Test
  void testControlledByThreadSuccess() {
    try {
      AuthManagerWrapper.builder()
          .withClearEachIteration(true)
          .withControlledByThread(false)
          .build();

      AuthManagerWrapper.builder()
          .withControlledByThread(false)
          .withClearEachIteration(true)
          .build();

    } catch (ConversionIllegalStateException e) {
      Assertions.fail();
    }
  }
}
