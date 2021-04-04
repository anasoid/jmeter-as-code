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
 * Date :   03-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmc.generic;

import com.thoughtworks.xstream.annotations.XStreamConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.NonNull;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.apache.commons.io.FileUtils;
import org.apache.jmeter.testelement.TestElement;

/** Abstract JmX Include element. */
@XStreamConverter(value = TestElementConverter.class)
public class AbstractJmxIncludeWrapper<T extends TestElement> implements TestElementWrapper<T> {

  private static Pattern regex =
      Pattern.compile(
          "<jmeterTestPlan +[a-zA-Z]*=\".{3,5}\" "
              + "+[a-zA-Z]*=\".{3,5}\" +[a-zA-Z]*=\".{3,5}\" *>"
              + "[\r|\n| ]*<hashTree>[\r|\n| ]*(.*)[\r|\n| ]*"
              + "</hashTree>[\r|\n| ]*</jmeterTestPlan>$",
          Pattern.DOTALL);

  private final String path;
  private Map<String, String> params;

  public AbstractJmxIncludeWrapper(@NonNull String path) {
    this.path = path;
  }

  public AbstractJmxIncludeWrapper(@NonNull String path, Map<String, String> params) {
    this.path = path;
    this.params = params;
  }

  public String getPath() {
    return path;
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String toXml() throws IOException {
    String raw = readFile(path);
    return cleanup(raw);
  }

  /**
   * cleanup raw content.
   *
   * @param raw raw content.
   */
  protected String cleanup(String raw) throws IOException {

    Matcher regexMatcher = regex.matcher(raw);
    String result = null;
    if (regexMatcher.find()) {
      result = regexMatcher.group(1);
    }
    return result;
  }

  /**
   * read file from resource as string.
   *
   * @param resource resource path.
   * @return file content as string.
   */
  protected String readFile(String resource) throws IOException {
    URL url = this.getClass().getClassLoader().getResource(resource); // NOPMD
    if (url == null) {
      throw new ConversionException("Resource not fournd : " + resource);
    }
    return FileUtils.readFileToString(new File(url.getFile()), StandardCharsets.UTF_8);
  }

  @Override
  public void init() {}

  @Override
  public Class<T> getTestClass() {
    return null;
  }

  @Override
  public String getTestClassAsString() {
    return null;
  }

  @Override
  public List<TestElementWrapper<?>> getChilds() {
    return null;
  }

  @Override
  public Set<String> getTags() {
    return null;
  }
}
