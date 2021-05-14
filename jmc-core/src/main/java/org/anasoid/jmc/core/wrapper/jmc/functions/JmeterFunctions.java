package org.anasoid.jmc.core.wrapper.jmc.functions;

import java.util.Locale;
import lombok.NonNull;
import org.anasoid.jmc.core.util.FunctionsUtils;
import org.anasoid.jmc.core.wrapper.jmc.Variable;

/** Implementation of Native Jmeter functions. */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UseObjectForClearerAPI", "PMD.ExcessivePublicCount"})
public final class JmeterFunctions {

  /** Alpha characters. */
  public static final String ALPHA = "abcdefghijklmnopqrstuvwxyz";
  /** Upper alpha characters. */
  public static final String ALPHA_UPPER = ALPHA.toUpperCase(Locale.ROOT);
  /** Numeric characters. */
  public static final String NUMERIC = "0123456789";
  /** Alpha Numeric characters. */
  public static final String ALPHA_NUMERIC = ALPHA + NUMERIC;

  private JmeterFunctions() {}

  /**
   * The counter generates a new number each time it is called, starting with 1 and incrementing by
   * +1 each time. The counter can be configured to keep each simulated user's values separate, or
   * to use the same counter for all users. If each user's values is incremented separately, that is
   * like counting the number of iterations through the test plan. A global counter is like counting
   * how many times that request was run.
   *
   * @param perThread TRUE if you wish each simulated user's counter to be kept independent and
   *     separate from the other users. FALSE for a global counter.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String counter(@NonNull Boolean perThread, Variable variableName) {
    return FunctionsUtils.function(
        "counter", perThread.toString().toUpperCase(Locale.ROOT), variableName);
  }

  /**
   * The counter generates a new number each time it is called, starting with 1 and incrementing by
   * +1 each time. The counter can be configured to keep each simulated user's values separate, or
   * to use the same counter for all users. If each user's values is incremented separately, that is
   * like counting the number of iterations through the test plan. A global counter is like counting
   * how many times that request was run.
   *
   * @param perThread TRUE if you wish each simulated user's counter to be kept independent and
   *     separate from the other users. FALSE for a global counter.
   */
  public static String counter(@NonNull Boolean perThread) {
    return counter(perThread);
  }

  /**
   * The eval function returns the result of evaluating a string expression.
   *
   * @param val expression to be evaluated.
   */
  public static String eval(@NonNull String val) {
    return FunctionsUtils.function("eval", val);
  }

  /**
   * The evalVar function returns the result of evaluating an expression stored in a variable.
   *
   * @param variable expression to be evaluated.
   */
  public static String evalVar(@NonNull Variable variable) {
    return FunctionsUtils.function("evalVar", variable.getName());
  }

  /**
   * The groovy function evaluates Apache Groovy scripts passed to it, and returns the result. Don't
   * escape commas.
   *
   * @param expression An Apache Groovy script.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String groovy(@NonNull String expression, Variable variableName) {
    return FunctionsUtils.function("groovy", expression, variableName);
  }

  /**
   * The groovy function evaluates Apache Groovy scripts passed to it, and returns the result. Don't
   * escape commas.
   *
   * @param expression An Apache Groovy script.
   */
  public static String groovy(@NonNull String expression) {
    return groovy(expression, null);
  }

  /**
   * The __isPropDefined function returns true if property exists or false if not.
   *
   * @param propertyName The Property Name to be used to check if defined.
   */
  public static String isPropDefined(@NonNull String propertyName) {
    return FunctionsUtils.function("isPropDefined", propertyName);
  }

  /**
   * The isVarDefined function returns true if variable exists or false if not.
   *
   * @param variable The Variable to be used to check if defined
   */
  public static String isVarDefined(@NonNull Variable variable) {
    return FunctionsUtils.function("isVarDefined", variable.getName());
  }

  /**
   * The javaScript function executes a piece of JavaScript (not Java!) code and returns its value.
   *
   * @param expression The expression to be evaluated.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String javaScript(@NonNull String expression, Variable variableName) {
    return FunctionsUtils.function("javaScript", expression, variableName);
  }

  /**
   * The javaScript function executes a piece of JavaScript (not Java!) code and returns its value.
   *
   * @param expression The expression to be evaluated.
   */
  public static String javaScript(@NonNull String expression) {
    return javaScript(expression, null);
  }

  /**
   * The jexl function returns the result of evaluating a Commons JEXL expression. See links below
   * for more information on JEXL expressions.
   *
   * @param expression The expression to be evaluated.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String jexl3(@NonNull String expression, Variable variableName) {
    return FunctionsUtils.function("jexl3", expression, variableName);
  }

  /**
   * The jexl function returns the result of evaluating a Commons JEXL expression. See links below *
   * for more information on JEXL expressions.
   *
   * @param expression The expression to be evaluated.
   */
  public static String jexl3(@NonNull String expression) {
    return jexl3(expression, null);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged.
   * @param throwable If non-empty, creates a Throwable to pass to the logger.
   * @param comment If present, it is displayed in the string. Useful for identifying what is being
   *     logged.
   * @return logged text.
   */
  public static String log(String input, String logLevel, String throwable, String comment) {
    return FunctionsUtils.function("log", input, logLevel, throwable, comment);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged.
   * @param comment If present, it is displayed in the string. Useful for identifying what is being
   *     logged.
   * @return logged text.
   */
  public static String log(String input, String logLevel, String comment) {
    return log(input, logLevel, null, comment);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged. logged.
   * @return logged text.
   */
  public static String log(String input, String logLevel) {
    return log(input, logLevel, null);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @return logged text.
   */
  public static String log(String input) {
    return log(input, null);
  }

  /**
   * The logn function logs a message, and returns the empty string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged.
   * @param throwable If non-empty, creates a Throwable to pass to the logger.
   * @return logged text.
   */
  public static String logn(String input, String logLevel, String throwable) {
    return FunctionsUtils.function("logn", input, logLevel, throwable);
  }

  /**
   * The logn function logs a message, and returns the empty string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged.
   * @return logged text.
   */
  public static String logn(String input, String logLevel) {
    return logn(input, logLevel, null);
  }

  /**
   * The logn function logs a message, and returns the empty string.
   *
   * @param input String to be logged.
   * @return logged text.
   */
  public static String logn(String input) {
    return logn(input, null);
  }

  /**
   * The machineIP function returns the local IP address. This uses the Java method
   * InetAddress.getLocalHost() and passes it to getHostAddress().
   */
  public static String machineIP() {
    return FunctionsUtils.function("machineIP");
  }

  /**
   * The machineName function returns the local host name. This uses the Java method
   * InetAddress.getLocalHost() and passes it to getHostName().
   */
  public static String machineName() {
    return FunctionsUtils.function("machineName");
  }

  /**
   * The property function returns the value of a JMeter property. If the property value cannot be
   * found, and no default has been supplied, it returns the property name.
   *
   * @param propertyName he property name to be retrieved.
   * @param variableName A reference name for reusing the value computed by this function.
   * @param defaultValue The default value for the property.
   */
  public static String property(
      @NonNull String propertyName, Variable variableName, String defaultValue) {
    return FunctionsUtils.function("property", propertyName, variableName, defaultValue);
  }

  /**
   * The property function returns the value of a JMeter property. If the property value cannot be
   * found, and no default has been supplied, it returns the property name.
   *
   * @param propertyName he property name to be retrieved.
   * @param defaultValue The default value for the property.
   */
  public static String property(@NonNull String propertyName, String defaultValue) {
    return property(propertyName, null, defaultValue);
  }

  /**
   * The random function returns a random number that lies between the given min and max values.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   * @param variableName variableName A reference name for reusing the value computed by this
   *     function.
   */
  public static String random(String min, String max, Variable variableName) {
    return FunctionsUtils.function("Random", min, max, variableName);
  }

  /**
   * The random function returns a random number that lies between the given min and max values.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   */
  public static String random(String min, String max) {
    return random(min, max, null);
  }

  /**
   * The random function returns a random number that lies between the given min and max values.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   * @param variableName variableName A reference name for reusing the value computed by this
   *     function.
   */
  public static String random(int min, int max, Variable variableName) {
    return random(String.valueOf(min), String.valueOf(max), variableName);
  }

  /**
   * The random function returns a random number that lies between the given min and max values.
   *
   * @param min Minimum value.
   * @param max Maximum value.
   */
  public static String random(int min, int max) {
    return random(min, max, null);
  }

  /**
   * The randomAlpha function returns a random alpha String of length using characters in chars to
   * use.
   *
   * @param length A number length of generated String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomAlpha(String length, Variable variableName) {
    return randomString(length, ALPHA, variableName);
  }

  /**
   * The randomAlpha function returns a random alpha String of length using characters in chars to
   * use.
   *
   * @param length A number length of generated String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomAlpha(int length, Variable variableName) {
    return randomAlpha(String.valueOf(length), variableName);
  }

  /**
   * The randomAlpha function returns a random alpha String of length using characters in chars to
   * use.
   *
   * @param length A number length of generated String.
   */
  public static String randomAlpha(int length) {
    return randomAlpha(String.valueOf(length), null);
  }

  /**
   * The randomAlpha function returns a random alpha numeric String of length using characters in
   * chars to use.
   *
   * @param length A number length of generated String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomAlphaNumeric(String length, Variable variableName) {
    return randomString(length, ALPHA_NUMERIC, variableName);
  }

  /**
   * The randomAlpha function returns a random alpha numeric String of length using characters in *
   * chars to use.
   *
   * @param length A number length of generated String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomAlphaNumeric(int length, Variable variableName) {
    return randomAlphaNumeric(String.valueOf(length), variableName);
  }

  /**
   * The randomAlpha function returns a random alpha numeric String of length using characters in *
   * chars to use.
   *
   * @param length A number length of generated String.
   */
  public static String randomAlphaNumeric(int length) {
    return randomAlphaNumeric(String.valueOf(length), null);
  }

  /**
   * The RandomDate function returns a random date that lies between the given start date and end
   * date values.
   *
   * @param format Format string for DateTimeFormatter (default yyyy-MM-dd).
   * @param startDate The start date, the default is now.
   * @param endDate The end date.
   * @param locale The string format of a locale. The language code must be lowercase. The country
   *     code must be uppercase. The separator must be an underscore, e.g. en_EN. See
   *     http://www.oracle.com/technetwork/java/javase/javase7locales-334809.html. If omitted, by
   *     default the function uses the Apache JMeter locale one.
   * @param variableName The variable to set.
   */
  public static String randomDate(
      String format,
      String startDate,
      @NonNull String endDate,
      String locale,
      Variable variableName) {
    return FunctionsUtils.function("RandomDate", format, startDate, endDate, locale, variableName);
  }

  /**
   * The RandomDate function returns a random date that lies between the given start date and end
   * date values.
   *
   * @param format Format string for DateTimeFormatter (default yyyy-MM-dd).
   * @param startDate The start date, the default is now.
   * @param endDate The end date.
   * @param locale The string format of a locale. The language code must be lowercase. The country
   *     code must be uppercase. The separator must be an underscore, e.g. en_EN. See
   *     http://www.oracle.com/technetwork/java/javase/javase7locales-334809.html. If omitted, by
   *     default the function uses the Apache JMeter locale one.
   */
  public static String randomDate(
      String format, String startDate, @NonNull String endDate, String locale) {
    return randomDate(format, startDate, endDate, locale);
  }

  /**
   * The RandomDate function returns a random date that lies between the given start date and end
   * date values.
   *
   * @param format Format string for DateTimeFormatter (default yyyy-MM-dd).
   * @param startDate The start date, the default is now.
   * @param endDate The end date.
   */
  public static String randomDate(String format, String startDate, @NonNull String endDate) {
    return randomDate(format, startDate, endDate);
  }

  /**
   * The RandomString function returns a random String of length using characters in chars to use.
   *
   * @param length A number length of generated String.
   * @param chars Chars used to generate String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomString(String length, String chars, Variable variableName) {
    return FunctionsUtils.function("RandomString", length, chars, variableName);
  }

  /**
   * The RandomString function returns a random String of length using characters in chars to use.
   *
   * @param length A number length of generated String.
   * @param chars Chars used to generate String.
   */
  public static String randomString(String length, String chars) {
    return randomString(length, chars, null);
  }

  /**
   * The RandomString function returns a random String of length using characters in chars to use.
   *
   * @param length A number length of generated String.
   * @param chars Chars used to generate String.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String randomString(int length, String chars, Variable variableName) {
    return randomString(String.valueOf(length), chars, variableName);
  }

  /**
   * The RandomString function returns a random String of length using characters in chars to use.
   *
   * @param length A number length of generated String.
   * @param chars Chars used to generate String.
   */
  public static String randomString(int length, String chars) {
    return randomString(length, chars, null);
  }

  /** The samplerName function returns the name (i.e. label) of the current sampler. */
  public static String samplerName() {
    return FunctionsUtils.function("samplerName");
  }

  /**
   * The setProperty function sets the value of a JMeter property. The default return value from the
   * function is the empty string, so the function call can be used anywhere functions are valid.
   *
   * @param propertyName The property name to be set.
   * @param propertyValue The value for the property.
   * @param returnOrigin Should the original value be returned?
   */
  public static String setProperty(
      @NonNull String propertyName, String propertyValue, Boolean returnOrigin) {
    return FunctionsUtils.function("setProperty", propertyName, propertyValue, returnOrigin);
  }

  /**
   * The setProperty function sets the value of a JMeter property. The default return value from the
   * function is the empty string, so the function call can be used anywhere functions are valid.
   *
   * @param propertyName The property name to be set.
   * @param propertyValue The value for the property.
   */
  public static String setProperty(@NonNull String propertyName, String propertyValue) {
    return setProperty(propertyName, propertyValue, null);
  }

  /**
   * The TestPlanName function returns the name of the current test plan (can be used in Including
   * Plans to know the name of the calling test plan).
   */
  public static String testPlanName() {
    return FunctionsUtils.function("TestPlanName");
  }

  /** The thread group name function simply returns the name of the thread group being executed. */
  public static String threadGroupName() {
    return FunctionsUtils.function("threadGroupName");
  }

  /**
   * get thread number.
   *
   * @return returns a number between zero and (max number of running threads minus one) .
   */
  public static String threadNum() {
    return FunctionsUtils.function("threadNum");
  }

  /**
   * The time function returns the current time in various formats.
   *
   * @param format The format to be passed to SimpleDateFormat. The function supports various
   *     shorthand aliases, see below. If omitted, the function returns the current time in
   *     milliseconds since the epoch.
   * @param variableName The name of the variable to set.
   */
  public static String time(String format, Variable variableName) {
    return FunctionsUtils.function("time", format, variableName);
  }

  /**
   * The time function returns the current time in various formats.
   *
   * @param format The format to be passed to SimpleDateFormat. The function supports various
   *     shorthand aliases, see below. If omitted, the function returns the current time in
   *     milliseconds since the epoch.
   */
  public static String time(String format) {
    return time(format, null);
  }

  /**
   * The time function returns the current time in various formats.
   *
   * @return returns the current time in milliseconds since the epoch.
   */
  public static String time() {
    return time(null, null);
  }

  /**
   * The timeShift function returns a date in the given format with the specified amount of seconds,
   * minutes, hours, days or months added.
   *
   * @param format The format to be passed to DateTimeFormatter. See DateTimeFormatter If omitted,
   *     the function uses milliseconds since epoch format.
   * @param dateToShift Indicate the date in the format set by the parameter 'Format' to shift If
   *     omitted, the date is set to now.
   * @param valueToShift Indicate the specified amount of seconds, minutes, hours or days to shift
   *     according to a textual representation of a duration such as PnDTnHnMn.nS. See
   *     https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-
   *     &lt;p&gt;PT20.345S parses as 20.345 seconds PT15M parses as 15 minutes PT10H parses as 10
   *     hours P2D parses as 2 days -P6H3M parses as -6 hours and -3 minutes
   * @param locale The string format of a locale. The language code must be lowercase. The country
   *     code must be uppercase. The separator must be an underscore. ex: en_EN See
   *     http://www.oracle.com/technetwork/java/javase/javase7locales-334809.html If omitted, by
   *     default the function use the ApacheJMeter locale one.
   * @param variableName The name of the variable to set.
   */
  public static String timeShift(
      String format,
      String dateToShift,
      String valueToShift,
      String locale,
      Variable variableName) {
    return FunctionsUtils.function(
        "timeShift", format, dateToShift, valueToShift, locale, variableName);
  }

  /**
   * The timeShift function returns a date in the given format with the specified amount of seconds,
   * minutes, hours, days or months added.
   *
   * @param format The format to be passed to DateTimeFormatter. See DateTimeFormatter If omitted,
   *     the function uses milliseconds since epoch format.
   * @param dateToShift Indicate the date in the format set by the parameter 'Format' to shift If
   *     omitted, the date is set to now.
   * @param valueToShift Indicate the specified amount of seconds, minutes, hours or days to shift
   *     according to a textual representation of a duration such as PnDTnHnMn.nS. See
   *     https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html#parse-java.lang.CharSequence-
   *     &lt;p&gt;PT20.345S parses as 20.345 seconds PT15M parses as 15 minutes PT10H parses as 10
   *     hours P2D parses as 2 days -P6H3M parses as -6 hours and -3 minutes
   * @param locale The string format of a locale. The language code must be lowercase. The country
   *     code must be uppercase. The separator must be an underscore. ex: en_EN See
   *     http://www.oracle.com/technetwork/java/javase/javase7locales-334809.html If omitted, by
   *     default the function use the ApacheJMeter locale one.
   */
  public static String timeShift(
      String format, String dateToShift, String valueToShift, String locale) {
    return timeShift(format, dateToShift, valueToShift, locale, null);
  }

  /** The UUID function returns a pseudo random type 4 Universally Unique IDentifier (UUID).. */
  public static String uuid() {
    return FunctionsUtils.function("UUID");
  }
}
