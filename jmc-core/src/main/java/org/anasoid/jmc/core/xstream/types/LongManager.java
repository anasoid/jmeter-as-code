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
 * Date :   13-Apr-2021
 */

package org.anasoid.jmc.core.xstream.types;

/** Manage Integer type. */
public class LongManager extends TypeManager {

  @Override
  public Class getType(String value) {
    if (value == null) {
      return String.class;
    }
    if (value.trim().startsWith("$")) {
      return String.class;
    }

    return Long.class;
  }
}
