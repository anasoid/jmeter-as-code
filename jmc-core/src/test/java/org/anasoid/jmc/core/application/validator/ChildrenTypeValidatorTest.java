package org.anasoid.jmc.core.application.validator;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmc.core.application.ApplicationTest;
import org.anasoid.jmc.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmc.core.wrapper.test.SubChildTestingElementWrapperTesting;
import org.anasoid.jmc.core.xstream.exceptions.ConversionIllegalStateException;
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
 * Date :   27-Mar-2021
 */

class ChildrenTypeValidatorTest {
  @Test
  void testChild() throws IOException {

    // First sub
    SubChildTestingElementWrapperTesting subChildTestElementWrapperTesting =
        SubChildTestingElementWrapperTesting.builder().withBb1(true).withName("sub1").build();

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withField("field")
            .addArgument(ArgumentWrapper.builder().withName("param").withValue("value").build())
            .addChild(subChildTestElementWrapperTesting)
            .build();

    ApplicationTest applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);

    StringWriter wr = new StringWriter(); // NOPMD

    applicationTest.toJmx(wr);
    parentTestElementWrapperTesting.getChilds().add(ThreadGroupWrapper.builder().build());
    wr = new StringWriter(); // NOPMD
    applicationTest =
        ApplicationTestUtilsForTesting.getApplicationTest(parentTestElementWrapperTesting);
    try {
      applicationTest.toJmx(wr);
      Assertions.fail();
    } catch (ConversionIllegalStateException e) {
      Assertions.assertTrue(e.getMessage().contains("with type"));
    }
  }
}
