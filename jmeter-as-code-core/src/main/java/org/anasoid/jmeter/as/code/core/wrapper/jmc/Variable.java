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
 * Date :   09-Apr-2021
 */

package org.anasoid.jmeter.as.code.core.wrapper.jmc;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 * Variable to manage variable in Jmeter. this class will manage transformation from varible name to
 * ${name}.
 */
public class Variable implements Serializable {

  @XStreamOmitField private static final long serialVersionUID = -2353696454050745099L;
  private final String name;

  /**
   * name of the variable.
   *
   * @param name name of the variable.
   */
  public Variable(@NonNull String name) {
    this.name = name;
  }

  /**
   * get list of variable from list of names.
   *
   * @param variables list of names.
   * @return List of variables.
   */
  public static List<Variable> asVariables(@NonNull List<String> variables) {
    return variables.stream().map(Variable::new).collect(Collectors.toList());
  }

  /**
   * get list of variable from arrays of names.
   *
   * @param variables arrays of names.
   * @return List of variables.
   */
  public static List<Variable> asVariables(@NonNull String... variables) {
    return asVariables(Arrays.asList(variables));
  }

  /** get name of variables. */
  public String getName() {
    return name;
  }

  /**
   * get value. the value is name decorated with ${...}.
   *
   * @return value.
   */
  public String getValue() {
    return "${" + name + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Variable variable = (Variable) o;
    return name.equals(variable.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
