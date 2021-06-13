package org.anasoid.jmc.core.wrapper.jmc.functions;

import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.functions.JmeterFunctions.LogLevel;
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
 * Date :   13-Jun-2021
 */

class JmeterFunctionsTest {

  @Test
  void dateTimeConvert() {
    Assertions.assertEquals(
        "${__dateTimeConvert(11/11/2011,dd/mm/yyyy,yyyy/mm/dd,var)}",
        JmeterFunctions.dateTimeConvert(
            "11/11/2011", "dd/mm/yyyy", "yyyy/mm/dd", new Variable("var")));

    Assertions.assertEquals(
        "${__dateTimeConvert(11/11/2011,,yyyy/mm/dd,)}",
        JmeterFunctions.dateTimeConvert("11/11/2011", "yyyy/mm/dd"));

    Assertions.assertEquals(
        "${__dateTimeConvert(11/11/2011,,yyyy/mm/dd,var)}",
        JmeterFunctions.dateTimeConvert("11/11/2011", "yyyy/mm/dd", new Variable("var")));
  }

  @Test
  void fileToString() {
    Assertions.assertEquals(
        "${__FileToString(file,utf-8,var)}",
        JmeterFunctions.fileToString("file", "utf-8", new Variable("var")));

    Assertions.assertEquals(
        "${__FileToString(file,,var)}", JmeterFunctions.fileToString("file", new Variable("var")));
  }

  @Test
  void intSum() {
    Assertions.assertEquals("${__intSum(5,7,)}", JmeterFunctions.intSum(5, 7));

    Assertions.assertEquals(
        "${__intSum(5,7,var)}", JmeterFunctions.intSum(new Variable("var"), 5, 7));
  }

  @Test
  void longSum() {
    Assertions.assertEquals("${__longSum(5,7,)}", JmeterFunctions.longSum(5L, 7L));

    Assertions.assertEquals(
        "${__longSum(5,7,var)}", JmeterFunctions.longSum(new Variable("var"), 5L, 7L));
  }

  @Test
  void log() {
    Assertions.assertEquals("${__log(message,,,)}", JmeterFunctions.log("message"));

    Assertions.assertEquals(
        "${__log(message,ERR,exception,comment)}",
        JmeterFunctions.log("message", LogLevel.ERR, "exception", "comment"));
  }

  @Test
  void random() {
    Assertions.assertEquals("${__Random(1,10,)}", JmeterFunctions.random(1, 10));
    Assertions.assertEquals(
        "${__Random(1,10,var)}", JmeterFunctions.random(1, 10, new Variable("var")));
  }

  @Test
  void randomAlpha() {
    Assertions.assertEquals(
        "${__RandomString(10,abcdefghijklmnopqrstuvwxyz,)}", JmeterFunctions.randomAlpha(10));
    Assertions.assertEquals(
        "${__RandomString(10,abcdefghijklmnopqrstuvwxyz,var)}",
        JmeterFunctions.randomAlpha(10, new Variable("var")));
  }

  @Test
  void randomAlphaNumeric() {
    Assertions.assertEquals(
        "${__RandomString(10,abcdefghijklmnopqrstuvwxyz0123456789,)}",
        JmeterFunctions.randomAlphaNumeric(10));
    Assertions.assertEquals(
        "${__RandomString(10,abcdefghijklmnopqrstuvwxyz0123456789,var)}",
        JmeterFunctions.randomAlphaNumeric(10, new Variable("var")));
  }

  @Test
  void split() {
    Assertions.assertEquals(
        "${__split(first\\,second,var,)}", JmeterFunctions.split("first,second", "var"));

    Assertions.assertEquals(
        "${__split(first\\,second,var,|)}", JmeterFunctions.split("first,second", "var", "|"));
  }

  @Test
  void stringToFile() {
    Assertions.assertEquals(
        "${__StringToFile(file,input,,)}", JmeterFunctions.stringToFile("file", "input"));

    Assertions.assertEquals(
        "${__StringToFile(file,input,false,)}",
        JmeterFunctions.stringToFile("file", "input", false));
  }

  @Test
  void stringFromFile() {
    Assertions.assertEquals(
        "${__StringFromFile(file,var,,)}",
        JmeterFunctions.stringFromFile("file", new Variable("var")));

    Assertions.assertEquals("${__StringFromFile(file,,,)}", JmeterFunctions.stringFromFile("file"));
  }
}
