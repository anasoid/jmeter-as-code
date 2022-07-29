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
 * Date :   08-Mar-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.assertions;

/** available Field to be checked in assertion. */
public enum AssertionField {

  /**
   * Test Response : the response text from the server, i.e. the body, excluding any HTTP headers.
   */
  RESPONSE_DATA("Assertion.response_data"),

  /** Response Code - e.g. 200. */
  RESPONSE_CODE("Assertion.response_code"),

  /** Response message.- e.g. OK. */
  RESPONSE_MESSAGE("Assertion.response_message"),

  /** Response Headers, including Set-Cookie headers (if any). */
  RESPONSE_HEADERS("Assertion.response_headers"),

  /** Request Headers. */
  REQUEST_HEADERS("Assertion.request_headers"),

  /**
   * Request data - the request text sent to the server, i.e. the body, excluding any HTTP headers.
   */
  REQUEST_DATA("Assertion.request_data"),

  /** URL sampled. */
  SAMPLE_URL("Assertion.sample_label"),

  /** Document (text) - the extract text from various type of documents via Apache Tika. */
  RESPONSE_DATA_AS_DOCUMENT("response_data_as_document");

  private final String value;

  AssertionField(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
