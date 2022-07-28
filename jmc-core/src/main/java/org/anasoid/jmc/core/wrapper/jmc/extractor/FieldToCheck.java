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
 * Date :   28-Apr-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.extractor;

/** enum for field check. */
public enum FieldToCheck {

  /** Response Header. may not be present for non-HTTP samples. */
  HEADERS("true"),
  /** Request Header. may not be present for non-HTTP samples. */
  REQUEST_HDRS("request_headers"),
  /** the body of the response, e.g. the content of a web-page (excluding headers). */
  BODY("false"),

  /**
   * Body as a Document - the extract text from various type of documents via Apache Tika (see View
   * Results Tree Document view section).
   */
  BODY_AS_DOCUMENT("as_document"),
  /**
   * the body of the response, with all Html escape codes replaced. Note that Html escapes are
   * processed without regard to context, so some incorrect substitutions may be made. (Note that
   * this option highly impacts performances, so use it only when absolutely necessary and be aware
   * of its impacts).
   */
  BODY_UNESCAPED("unescaped"),
  /** Url. */
  URL("URL"),
  /** Response code : ex 200. */
  RESPONSE_CODE("code"),
  /** Response message : ex ok. */
  MESSAGE("message");

  private final String value;

  FieldToCheck(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }
}
