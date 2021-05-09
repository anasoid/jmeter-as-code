package org.anasoid.jmc.core.wrapper.jmeter.protocol.http.control;

import java.io.IOException;
import org.anasoid.jmc.core.AbstractJmcTest;
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
 * Date :   09-May-2021
 */

class HeaderManagerWrapperTest extends AbstractJmcTest {

  @Test
  void testDslMethod() throws IOException {
    // ADD DIRECT
    HeaderManagerWrapper headerManager =
        HeaderManagerWrapper.builder()
            .acceptEncodingHeader("encoding")
            .authorizationHeader("authorize")
            .contentTypeHeader("content")
            .acceptLanguageHeader("FR")
            .acceptCharsetHeader("charset")
            .acceptHeader("accept")
            .userAgentHeader("agent")
            .build();

    assertHeader(headerManager, "Accept-Encoding", "encoding");
    assertHeader(headerManager, "Authorization", "authorize");
    assertHeader(headerManager, "Content-Type", "content");
    assertHeader(headerManager, "Accept-Language", "FR");
    assertHeader(headerManager, "Accept-Charset", "charset");
    assertHeader(headerManager, "Accept", "accept");
    assertHeader(headerManager, "User-Agent", "agent");
  }

  private void assertHeader(HeaderManagerWrapper headerManager, String name, String value) {
    Assertions.assertEquals(
        headerManager.getHeaders().stream()
            .filter(h -> h.getName().equals(name))
            .findAny()
            .get()
            .getValue(),
        value);
  }
}
