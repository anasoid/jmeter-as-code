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
 * Date :   01-Feb-2021
 */

package org.anasoid.jmeter.as.code.core.xstream.converters;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.asserts.JmcAsserts;
import org.anasoid.jmeter.as.code.core.data.DataTesting;
import org.junit.jupiter.api.Test;

/**
 * Main Test TestElementConverter.
 *
 * @see TestElementConverter
 */
class TestElementConverterTest extends AbstractJmcTest {

  @Test
  void firstTest() throws IOException {

    ApplicationTest applicationTest = DataTesting.getDefautTest();
    StringWriter wr = new StringWriter(); // NOPMD
    applicationTest.toJmx(wr);
    String content = wr.toString();
    println("content :" + content);
    JmcAsserts.get().assertXPathPropInt(content, "/parent", "Parent.ii1", "1");
    JmcAsserts.get()
        .assertXPathValue(content, "/parent/boolProp/@name", "TestPlan.functional_mode");
  }
}
