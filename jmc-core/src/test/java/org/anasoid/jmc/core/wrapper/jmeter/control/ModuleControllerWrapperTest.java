package org.anasoid.jmc.core.wrapper.jmeter.control;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcCoreTest;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.apache.jmeter.control.ModuleController;
import org.apache.jorphan.collections.HashTree;
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
 * Date :   20-Jul-2021
 */

class ModuleControllerWrapperTest extends AbstractJmcCoreTest {

  @Test
  void testDuplicate() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(s1).build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();
    try {
      toApplicationTest(testPlanWrapper, "MD");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("Ambiguous target"));
    }
  }

  @Test
  void testDuplicateByName() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().withName("s").build();
    SimpleControllerWrapper s2 = SimpleControllerWrapper.builder().withName("s").build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().addController(s2).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(s1).build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();
    try {
      toApplicationTest(testPlanWrapper, "MD");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("Ambiguous target"));
    }
  }

  @Test
  void testDuplicateFailNameRootId() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(t1, s1).build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();

    try {
      toApplicationTest(testPlanWrapper, "MD");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("Ambiguous target for "));
    }
  }

  @Test
  void testUseNameAndReferenceIdFail() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(
                        ModuleControllerWrapper.builder()
                            .withModule(t1, s1)
                            .withModule("target")
                            .build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();

    try {
      toApplicationTest(testPlanWrapper, "MD");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(
          e.getMessage().contains("Module target can be set by name or by reference "));
    }
  }

  @Test
  void testDuplicateSuccessRootId() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().withName("t1").addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().withName("t2").addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(t1, s1).build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();

    toApplicationTest(testPlanWrapper, "MD");
  }

  @Test
  void testDuplicateSuccessRootIdByName() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().withName("s").build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().withName("t1").addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().withName("t2").addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(t1, "s").build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();

    toApplicationTest(testPlanWrapper, "MD");
  }

  @Test
  void testValidator() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().withName("t1").addController(s1).build();
    TestFragmentWrapper t2 = TestFragmentWrapper.builder().withName("t2").addController(s1).build();
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().build())
                    .build())
            .addTestFragment(t1)
            .addTestFragment(t2)
            .build();

    try {
      toApplicationTest(testPlanWrapper, "MD");
      Assertions.fail();
    } catch (ConversionException e) {
      Assertions.assertTrue(e.getMessage().contains("Module target can't be empty"));
    }
  }

  @Test
  void testName() throws IOException {

    SimpleControllerWrapper s1 = SimpleControllerWrapper.builder().withName("s1").build();
    TestFragmentWrapper t1 = TestFragmentWrapper.builder().withName("t1").addController(s1).build();
    ;
    TestPlanWrapper testPlanWrapper =
        TestPlanWrapper.builder()
            .addThread(
                ThreadGroupWrapper.builder()
                    .addController(ModuleControllerWrapper.builder().withModule(s1).build())
                    .build())
            .addTestFragment(t1)
            .build();

    HashTree applicationTest = toHashTree(testPlanWrapper, "MD");
    ModuleController moduleController =
        (ModuleController)
            applicationTest
                .get(applicationTest.getArray()[0])
                .get(applicationTest.get(applicationTest.getArray()[0]).getArray()[0])
                .getArray()[0];

    Assertions.assertEquals("MD-> s1", moduleController.getName());
  }
}
