package org.anasoid.jmc.core.xstream.annotations;

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
 * Date :   14-Jan-2021
 */

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Defines that a field should be serialized as an collection. &#x3C;collectionProp .../&#x3E; */
@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface JmcCollection {

  /** attribute 'name' value. */
  String value();

  /** add Parent tag elementProp. */
  boolean withElementProp() default false;

  /** name tag on elementProp. only when withElementProp is true. */
  String name() default "";

  /** name tag on elementProp. only when withElementProp is true. */
  String testname() default "";

  /** elementType tag on elementProp. only when withElementProp is true. */
  Class<?> elementType() default Void.class;

  /** guiclass tag on elementProp. only when withElementProp is true. */
  Class<?> guiclass() default Void.class;

  /** testclass tag on elementProp. only when withElementProp is true. */
  Class<?> testclass() default Void.class;

  /** enabled tag on elementProp. only when withElementProp is true. */
  boolean enabled() default true;
}
