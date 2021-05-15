package org.anasoid.jmc.core.util;

import org.anasoid.jmc.core.wrapper.jmc.Variable;

/**
 * Function utils, for Jmeter function use {@link
 * org.anasoid.jmc.core.wrapper.jmc.functions.JmeterFunctions}.
 */
public final class FunctionsUtils {

  private FunctionsUtils() {}

  /**
   * Return function under jmeter format.
   *
   * @param functionName functionName.
   * @param args list of arguments.
   */
  public static String function(String functionName, Object... args) {

    StringBuilder result = new StringBuilder("${__");
    result.append(functionName);
    if (args.length > 0) {
      result.append('(');
    }

    for (int i = 1; i <= args.length; i++) {
      Object object = args[i - 1];
      if (object != null) {
        if (object instanceof Variable) {
          result.append(convert(((Variable) object).getName()));
        } else {
          result.append(convert(object.toString()));
        }
      }
      if (i != args.length) {
        result.append(',');
      }
    }
    if (args.length > 0) {
      result.append(')');
    }
    result.append('}');
    return result.toString();
  }

  private static String convert(String input) {

    if (input == null) {
      return "";
    }
    return input.replace(",", "\\,");
  }
}
