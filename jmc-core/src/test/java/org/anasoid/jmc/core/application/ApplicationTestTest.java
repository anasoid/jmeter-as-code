package org.anasoid.jmc.core.application;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import org.anasoid.jmc.core.application.interceptors.PrepareInterceptor;
import org.anasoid.jmc.core.wrapper.jmeter.config.ArgumentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.test.ChildTestElementWrapperTesting;
import org.anasoid.jmc.core.wrapper.test.ParentTestElementWrapperTesting;
import org.anasoid.jmc.core.wrapper.test.SubChildTestingElementWrapperTesting;
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
 * Date :   23-Mar-2021
 */

class ApplicationTestTest {

  @Test
  void testChild() throws IOException {

    ApplicationTest applicationTest =
        new ApplicationTest(getTestElement(), Arrays.asList(new ParentPrepareInterceptor()));

    StringWriter wr = new StringWriter(); // NOPMD

    TestElementWrapper result = applicationTest.toJmx(wr);

    Assertions.assertEquals(4, result.getChilds().size());
    ChildTestElementWrapperTesting child1 =
        (ChildTestElementWrapperTesting) result.getChilds().get(0);
    ChildTestElementWrapperTesting child2 =
        (ChildTestElementWrapperTesting) result.getChilds().get(1);
    ChildTestElementWrapperTesting child3 =
        (ChildTestElementWrapperTesting) result.getChilds().get(2);
    ChildTestElementWrapperTesting child4 =
        (ChildTestElementWrapperTesting) result.getChilds().get(3);

    Assertions.assertEquals("newnode", child1.getName());
    Assertions.assertEquals("sub1", child2.getName());
    Assertions.assertEquals("sub2prepared", child3.getName());
    Assertions.assertEquals("sub2prepared", child4.getName());
  }

  @Test
  void testToJmx() throws IOException {

    ApplicationTest applicationTest = new ApplicationTest(getTestElement());

    String filepath = System.getProperties().getProperty("user.dir") + "/build/jmx/" + "toJmx.jmx";
    applicationTest.toJmx(new File(filepath));
    Assertions.assertTrue(new File(filepath).exists());
  }

  private ParentTestElementWrapperTesting getTestElement() {

    // First sub
    SubChildTestingElementWrapperTesting subChildTestElementWrapperTesting =
        SubChildTestingElementWrapperTesting.builder()
            .withBb1(true)
            .withField("f")
            .withName("sub1")
            .build();

    // second sub
    SubChildTestingElementWrapperTesting subChildTestElementWrapperTesting2 =
        SubChildTestingElementWrapperTesting.builder()
            .withBb1(true)
            .withField("f")
            .addTags("toprepare")
            .withName("sub2")
            .build();

    // Thread Group
    ParentTestElementWrapperTesting parentTestElementWrapperTesting =
        ParentTestElementWrapperTesting.builder()
            .withName("Parent")
            .withBb1(false)
            .withIi1(1)
            .withLl1(2L)
            .withField("field")
            .addArgument(ArgumentWrapper.builder().withName("param").withValue("value").build())
            .addChild(subChildTestElementWrapperTesting)
            .addChild(subChildTestElementWrapperTesting2)
            .addChild(subChildTestElementWrapperTesting2)
            .build();

    return parentTestElementWrapperTesting;
  }

  class ParentPrepareInterceptor implements PrepareInterceptor {

    @Override
    public boolean support(TestElementWrapper<?> testElementWrapper) {
      return testElementWrapper instanceof ParentTestElementWrapperTesting
          || testElementWrapper instanceof ChildTestElementWrapperTesting;
    }

    @Override
    public void prepare(TestElementWrapper<?> testPlan, TestElementWrapper<?> testElementWrapper) {

      if (testElementWrapper instanceof ParentTestElementWrapperTesting) {
        ParentTestElementWrapperTesting parent =
            (ParentTestElementWrapperTesting) testElementWrapper;
        parent
            .getChilds()
            .add(0, SubChildTestingElementWrapperTesting.builder().withName("newnode").build());
      } else if (testElementWrapper instanceof ChildTestElementWrapperTesting) {
        ChildTestElementWrapperTesting child = (ChildTestElementWrapperTesting) testElementWrapper;
        if (child.getTags().contains("toprepare")) {
          child.setName(child.getName() + "prepared");
        }
      }
    }
  }
}
