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
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.anasoid.jmeter.as.code.core.util.FileUtils;
import org.anasoid.jmeter.as.code.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmeter.as.code.core.xstream.ConverterBeanUtils;
import org.anasoid.jmeter.as.code.core.xstream.annotations.JmcParam;
import org.anasoid.jmeter.as.code.core.xstream.converters.TestElementConverter;
import org.anasoid.jmeter.as.code.core.xstream.exceptions.ConversionException;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

/** Abstract JmX Include element. */
@XStreamConverter(value = TestElementConverter.class)
public abstract class AbstractJmxIncludeWrapper<T> implements TestElementWrapper<T> {

  private static final long serialVersionUID = 2424752951957768877L;

  private static final Pattern REGEX =
      Pattern.compile(
          "<jmeterTestPlan\\s+(:?[a-zA-Z]+=\".{3,5}\"\\s*){3}>[\\s]*<hashTree>"
              + "[\\s]*(?<node>\\<.+?\\>)[\\s]*"
              + "</hashTree>[\\s]*</jmeterTestPlan>",
          Pattern.DOTALL);

  @SuppressWarnings("PMD.UnusedAssignment")
  @XStreamOmitField
  private Set<String> tags = new HashSet<>();

  private boolean isInitialized;

  @SuppressWarnings("PMD.UnusedAssignment")
  private String path = getDefaultPath();

  private Map<String, String> params = new HashMap<>(); // NOPMD

  protected String getDefaultPath() {
    return null;
  }

  /** Convert object to Xml. */
  public String toXml() throws IOException {
    if (getDefaultPath() != null && !getDefaultPath().equals(path)) {
      throw new ConversionException(
          "Path [" + path + "]+ is provided and  getDefaultPath  for :" + this);
    }
    String raw = FileUtils.readResource(path);
    return replaceParam(cleanup(raw));
  }

  @Override
  public Set<String> getTags() {
    return tags;
  }

  /**
   * replace parameters.
   *
   * @param raw raw content.
   */
  protected String replaceParam(String raw) {

    Map<String, String> allParam = new HashMap<>(params);
    allParam.putAll(getFieldParam());
    if (MapUtils.isEmpty(allParam)) {
      return raw;
    }
    String result = raw;
    for (Entry<String, String> entry : allParam.entrySet()) {
      result = StringUtils.replace(result, getParam(entry.getKey()), entry.getValue());
    }
    return result;
  }

  /**
   * get Param format.
   *
   * @param key key of parameter.
   */
  protected String getParam(String key) {

    return "${jmc." + key + "}";
  }

  /**
   * cleanup raw content.
   *
   * @param raw raw content.
   */
  protected String cleanup(String raw) {

    Matcher regexMatcher = REGEX.matcher(raw);
    String result;
    if (regexMatcher.find()) {
      result = regexMatcher.group("node");
      if (result == null) {
        throw new ConversionException("Format incorrect for node : " + raw);
      }

    } else {
      throw new ConversionException("Format incorrect for node : " + raw);
    }
    return result;
  }

  /**
   * get All Param field on object with value.
   *
   * @return list all field.
   */
  @SneakyThrows
  private Map<String, String> getFieldParam() {
    Map<String, String> result = new HashMap<>(); // NOPMD
    Class<?> clazz = this.getClass();
    for (Class<?> sclazz : ConverterBeanUtils.getSuperClasses(clazz)) {
      for (Field field : sclazz.getDeclaredFields()) {
        if (!result.containsKey(field.getName())) {
          JmcParam jmcParam = field.getAnnotation(JmcParam.class);
          if (jmcParam != null) {
            String key = field.getName();
            if (!"".equals(jmcParam.value())) { // NOPMD
              key = jmcParam.value();
            }
            field.setAccessible(true);
            Object value = field.get(this);
            if (value == null) { // NOPMD
              throw new ConversionException("Null value for " + field.getName() + "on " + this);
            }
            result.put(key, value.toString());
          }
        }
      }
    }
    return result;
  }

  @Override
  public final void init() {
    if (!isInitialized) {
      internalInit();
      isInitialized = true;
    }
  }

  protected void internalInit() {}

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

  public String getPath() {
    return this.path;
  }

  /** get list of Parameters. */
  public Map<String, String> getParams() {
    return this.params;
  }

  /** Builder. */
  @SuppressWarnings({"PMD.TooManyMethods"})
  public abstract static class AbstractJmxIncludeWrapperBuilder<
      T,
      C extends AbstractJmxIncludeWrapper<T>,
      B extends AbstractJmxIncludeWrapperBuilder<T, C, B>> {

    private String pathValue;
    private boolean pathSet;
    private Set<String> tagsValue;
    private boolean tagsSet;

    private Map<String, String> params = new HashMap<>();

    @SuppressWarnings("PMD.AccessorMethodGeneration")
    private static <T> void $fillValuesFromInstanceIntoBuilder(
        AbstractJmxIncludeWrapper<T> instance, AbstractJmxIncludeWrapperBuilder<T, ?, ?> b) {
      b.withPath(instance.path);
      b.withParams(instance.params);
      b.withTags(instance.tags);
    }

    /** hide method , generated by Lombok. */
    protected B withTags(Set<String> tags) {
      this.tagsValue = tags;
      this.tagsSet = true;
      return self();
    }

    /** Add tags. */
    public B addTags(String... tags) {
      if (this.tagsValue == null) {
        this.tagsValue = new HashSet<>();
        this.tagsSet = true;
      }
      for (String tag : tags) {
        this.tagsValue.add(tag);
      }
      return self();
    }

    /** add Path of template file. */
    public B withPath(String path) {
      this.pathValue = path;
      this.pathSet = true;
      return self();
    }

    /** hide method , generated by Lombok. */
    @SuppressWarnings("PMD.UnusedFormalParameter")
    protected B withParams(Map<String, String> params) {
      this.params = params;
      return self();
    }

    /** Add parameters maps. */
    public B addParams(Map<String, String> params) {
      if (this.params == null) {
        this.params = new HashMap<>();
      }
      this.params.putAll(params);
      return self();
    }

    /**
     * Add one Parameter.
     *
     * @param key key.
     * @param value value.
     */
    public B addParam(String key, String value) {
      return addParams(Map.of(key, value));
    }

    protected B $fillValuesFrom(C instance) { // NOSONAR
      AbstractJmxIncludeWrapperBuilder.$fillValuesFromInstanceIntoBuilder(instance, this);
      return self();
    }

    protected abstract B self();

    public abstract C build();

    /** to String. */
    @Override
    public String toString() {
      return "AbstractJmxIncludeWrapper.AbstractJmxIncludeWrapperBuilder(path$value="
          + this.pathValue
          + ", params="
          + this.params
          + ")";
    }
  }

  @SuppressWarnings("PMD.AccessorMethodGeneration")
  protected AbstractJmxIncludeWrapper(AbstractJmxIncludeWrapperBuilder<T, ?, ?> b) {
    if (b.pathSet) {
      this.path = b.pathValue;
    } else {
      this.path = getDefaultPath(); // NOPMD
    }
    if (b.tagsSet) {
      this.tags = b.tagsValue;
    } else {
      this.tags = new HashSet<>();
    }
    this.params = b.params;
  }
}
