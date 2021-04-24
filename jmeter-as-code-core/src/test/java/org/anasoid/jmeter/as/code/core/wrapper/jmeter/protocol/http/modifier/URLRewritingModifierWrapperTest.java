package org.anasoid.jmeter.as.code.core.wrapper.jmeter.protocol.http.modifier;

import java.io.IOException;
import java.io.StringWriter;
import org.anasoid.jmeter.as.code.core.AbstractJmcTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTest;
import org.anasoid.jmeter.as.code.core.application.ApplicationTestUtilsForTesting;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionMandatoryException;
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
 * Date :   24-Apr-2021
 */

class URLRewritingModifierWrapperTest  extends AbstractJmcTest {


  @Test
  void testFailMandatoryArgument() throws IOException {

    try {
      URLRewritingModifierWrapper wrapper =
          URLRewritingModifierWrapper.builder()
              .build();
      ApplicationTest applicationTest = ApplicationTestUtilsForTesting.getApplicationTest(wrapper);
      StringWriter wr = new StringWriter(); // NOPMD
      applicationTest.toJmx(wr);

      Assertions.fail();
    } catch (ConversionMandatoryException e) {
      // Nothing
    }
  }

}