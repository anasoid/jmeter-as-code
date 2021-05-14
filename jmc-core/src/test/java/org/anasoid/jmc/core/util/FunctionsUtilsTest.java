package org.anasoid.jmc.core.util;

import org.anasoid.jmc.core.wrapper.jmc.Variable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FunctionsUtilsTest {

  @Test
  void basicFunction() {

    String result = FunctionsUtils.function("fct", "input", new Variable("var"));
    Assertions.assertEquals("${__fct(input,var)}", result);
  }

  @Test
  void escapeCharactereFunction() {

    String result = FunctionsUtils.function("base64Decode", "in,put", new Variable("var"));
    Assertions.assertEquals("${__base64Decode(in\\,put,var)}", result);
  }

  @Test
  void escapeNullFunction() {

    String result = FunctionsUtils.function("fct", "input", new Variable("var"), null);
    Assertions.assertEquals("${__fct(input,var,)}", result);
  }

  @Test
  void withouArgFunction() {

    String result = FunctionsUtils.function("fct");
    Assertions.assertEquals("${__fct}", result);
  }

  @Test
  void booleanArgFunction() {

    String result = FunctionsUtils.function("fct", Boolean.TRUE);
    Assertions.assertEquals("${__fct(true)}", result);
  }
}
