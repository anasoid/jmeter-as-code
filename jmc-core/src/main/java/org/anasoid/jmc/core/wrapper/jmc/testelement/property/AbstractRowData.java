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
 * Date :   09-Jun-2021
 */

package org.anasoid.jmc.core.wrapper.jmc.testelement.property;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.jmeter.testelement.property.CollectionProperty;
import org.apache.jmeter.testelement.property.StringProperty;

/**
 * Represent line on table , to be converted to collectionProperty {@link
 * org.apache.jmeter.testelement.property.CollectionProperty}.
 */
public abstract class AbstractRowData implements RowData {

  @Override
  public CollectionProperty asProperty() {
    List<String> list = asList();
    if (list == null) {
      return null;
    }
    List<StringProperty> listProp = new ArrayList<>();
    listProp.addAll(
        list.stream()
            .map(c -> new StringProperty(Integer.toString(c.hashCode()), c))
            .collect(Collectors.toList()));

    return new CollectionProperty(Integer.toString(listProp.hashCode()), listProp);
  }

  @Override
  public List<String> asList() {
    return null;
  }
}
