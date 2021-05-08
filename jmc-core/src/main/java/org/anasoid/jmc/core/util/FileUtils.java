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
 * Date :   23-Apr-2021
 */

package org.anasoid.jmc.core.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;

/** File Utils. */
public final class FileUtils {

  private FileUtils() {}

  /**
   * read file from resource as string.
   *
   * @param resource resource path.
   * @return file content as string.
   */
  public static String readResource(String resource) {
    URL url = Thread.currentThread().getContextClassLoader().getResource(resource); // NOPMD
    if (url == null) {
      throw new ConversionException("Resource not found : " + resource);
    }
    try {
      return org.apache.commons.io.FileUtils.readFileToString(
          new File(url.getFile()), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new ConversionException("Resource not found : " + resource, e);
    }
  }
}
