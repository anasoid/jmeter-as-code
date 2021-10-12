package org.anasoid.jmc.core.wrapper.jmc.functions;

import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.anasoid.jmc.core.wrapper.jmc.functions.JmeterFunctions.DigestAlgorithm;
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
  void digest() {
    Assertions.assertEquals(
        "${__digest(MD5,me,salt,true,var)}",
        JmeterFunctions.digest("me", DigestAlgorithm.MD5, "salt", true, new Variable("var")));
    Assertions.assertEquals(
        "${__digest(MD5,me,salt,,)}", JmeterFunctions.digest("me", DigestAlgorithm.MD5, "salt"));
    Assertions.assertEquals(
        "${__digest(MD5,me,,true,)}", JmeterFunctions.digest("me", DigestAlgorithm.MD5, true));
    Assertions.assertEquals(
        "${__digest(MD5,me,salt,,var)}",
        JmeterFunctions.digest("me", DigestAlgorithm.MD5, "salt", new Variable("var")));
    Assertions.assertEquals(
        "${__digest(MD5,me,,,var)}",
        JmeterFunctions.digest("me", DigestAlgorithm.MD5, new Variable("var")));
  }

  @Test
  void escapeHtml() {
    Assertions.assertEquals("${__escapeHtml(me)}", JmeterFunctions.escapeHtml("me"));
  }

  @Test
  void escapeOroRegexpChars() {
    Assertions.assertEquals(
        "${__escapeOroRegexpChars(me,)}", JmeterFunctions.escapeOroRegexpChars("me"));
    Assertions.assertEquals(
        "${__escapeOroRegexpChars(me,var)}",
        JmeterFunctions.escapeOroRegexpChars("me", new Variable("var")));
  }

  @Test
  void escapeXml() {
    Assertions.assertEquals("${__escapeXml(me)}", JmeterFunctions.escapeXml("me"));
  }

  @Test
  void eval() {
    Assertions.assertEquals("${__eval(me)}", JmeterFunctions.eval("me"));
  }

  @Test
  void evalVar() {
    Assertions.assertEquals("${__evalVar(var)}", JmeterFunctions.evalVar(new Variable("var")));
  }

  @Test
  void fileToString() {
    Assertions.assertEquals(
        "${__FileToString(file,utf-8,var)}",
        JmeterFunctions.fileToString("file", "utf-8", new Variable("var")));

    Assertions.assertEquals(
        "${__FileToString(file,,var)}", JmeterFunctions.fileToString("file", new Variable("var")));

    Assertions.assertEquals(
        "${__FileToString(file,utf-8,)}", JmeterFunctions.fileToString("file", "utf-8"));
  }

  @Test
  void groovy() {
    Assertions.assertEquals("${__groovy(me,)}", JmeterFunctions.groovy("me"));
    Assertions.assertEquals(
        "${__groovy(me,var)}", JmeterFunctions.groovy("me", new Variable("var")));
  }

  @Test
  void intSum() {
    Assertions.assertEquals("${__intSum(5,7,)}", JmeterFunctions.intSum(5, 7));

    Assertions.assertEquals(
        "${__intSum(5,7,var)}", JmeterFunctions.intSum(new Variable("var"), 5, 7));
  }

  @Test
  void isPropDefined() {
    Assertions.assertEquals("${__isPropDefined(me)}", JmeterFunctions.isPropDefined("me"));
  }

  @Test
  void isVarDefined() {
    Assertions.assertEquals(
        "${__isVarDefined(var)}", JmeterFunctions.isVarDefined(new Variable("var")));
  }

  @Test
  void javaScript() {
    Assertions.assertEquals("${__javaScript(me,)}", JmeterFunctions.javaScript("me"));
    Assertions.assertEquals(
        "${__javaScript(me,var)}", JmeterFunctions.javaScript("me", new Variable("var")));
  }

  @Test
  void jexl3() {
    Assertions.assertEquals("${__jexl3(me,)}", JmeterFunctions.jexl3("me"));
    Assertions.assertEquals("${__jexl3(me,var)}", JmeterFunctions.jexl3("me", new Variable("var")));
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
  void logn() {
    Assertions.assertEquals("${__logn(me,,)}", JmeterFunctions.logn("me"));
    Assertions.assertEquals(
        "${__logn(me,ERR,throwable)}", JmeterFunctions.logn("me", LogLevel.ERR, "throwable"));
    Assertions.assertEquals("${__logn(me,ERR,)}", JmeterFunctions.logn("me", LogLevel.ERR));
  }

  @Test
  void machineIP() {
    Assertions.assertEquals("${__machineIP()}", JmeterFunctions.machineIP());
    Assertions.assertEquals("${__machineIP(var)}", JmeterFunctions.machineIP(new Variable("var")));
  }

  @Test
  void machineName() {
    Assertions.assertEquals("${__machineName()}", JmeterFunctions.machineName());
    Assertions.assertEquals(
        "${__machineName(var)}", JmeterFunctions.machineName(new Variable("var")));
  }

  @Test
  void property() {
    Assertions.assertEquals("${__P(me,)}", JmeterFunctions.property("me"));
    Assertions.assertEquals(
        "${__P(me,default)}", JmeterFunctions.property("me", "default"));
    Assertions.assertEquals(
        "${__property(me,var,default)}",
        JmeterFunctions.property("me", new Variable("var"), "default"));
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
  void samplerName() {
    Assertions.assertEquals("${__samplerName()}", JmeterFunctions.samplerName());
    Assertions.assertEquals(
        "${__samplerName(var)}", JmeterFunctions.samplerName(new Variable("var")));
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
