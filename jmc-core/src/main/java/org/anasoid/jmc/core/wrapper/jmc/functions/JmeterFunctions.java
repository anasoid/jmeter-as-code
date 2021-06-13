package org.anasoid.jmc.core.wrapper.jmc.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import lombok.NonNull;
import org.anasoid.jmc.core.util.FunctionsUtils;
import org.anasoid.jmc.core.wrapper.jmc.Variable;

/** Implementation of Native Jmeter functions. */
@SuppressWarnings({
  "PMD.TooManyMethods",
  "PMD.UseObjectForClearerAPI",
  "PMD.ExcessivePublicCount",
  "PMD.ExcessiveClassLength",
  "PMD.GodClass"
})
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

  /** Mode for change case method. */
  public enum ChangeCaseMode {
    UPPER,
    LOWER,
    CAPITALIZE
  }

  /**
   * The change case function returns a string value which case has been changed following a
   * specific mode. Result can optionally be saved in a JMeter variable.
   *
   * @param input The String which case will be changed.
   * @param mode The mode to be used to change case: for example for ab-CD eF: UPPER result as AB-CD
   *     EF, LOWER result as ab-cd ed ,CAPITALIZE result as Ab-CD eF s.
   */
  public static String changeCase(@NonNull String input, ChangeCaseMode mode) {
    return FunctionsUtils.function("changeCase", input, mode);
  }

  /**
   * The CSVRead function returns a string from a CSV file (c.f. StringFromFile)
   *
   * <p>NOTE: JMeter supports multiple file names.
   *
   * <p>In most cases, the newer CSV Data Set Config element is easier to use.
   *
   * <p>When a filename is first encountered, the file is opened and read into an internal array. If
   * a blank line is detected, this is treated as end of file - this allows trailing comments to be
   * used.
   *
   * <p>All subsequent references to the same file name use the same internal array. N.B. the
   * filename case is significant to the function, even if the OS doesn't care, so
   * CSVRead(abc.txt,0) and CSVRead(aBc.txt,0) would refer to different internal arrays.
   *
   * <p>The *ALIAS feature allows the same file to be opened more than once, and also allows for
   * shorter file names.
   *
   * <p>Each thread has its own internal pointer to its current row in the file array. When a thread
   * first refers to the file it will be allocated the next free row in the array, so each thread
   * will access a different row from all other threads. [Unless there are more threads than there
   * are rows in the array.]
   *
   * @param file The file (or *ALIAS) to read from.
   * @param columnNum he column number in the file. 0 = first column, 1 = second etc. "next" - go to
   *     next line of file. *ALIAS - open a file and assign it to the alias .
   */
  public static String csvRead(@NonNull String file, @NonNull String columnNum) {
    return FunctionsUtils.function("CSVRead", file, columnNum);
  }

  /**
   * The CSVRead function returns a string from a CSV file (c.f. StringFromFile)
   *
   * <p>NOTE: JMeter supports multiple file names.
   *
   * <p>In most cases, the newer CSV Data Set Config element is easier to use.
   *
   * <p>When a filename is first encountered, the file is opened and read into an internal array. If
   * a blank line is detected, this is treated as end of file - this allows trailing comments to be
   * used.
   *
   * <p>All subsequent references to the same file name use the same internal array. N.B. the
   * filename case is significant to the function, even if the OS doesn't care, so
   * CSVRead(abc.txt,0) and CSVRead(aBc.txt,0) would refer to different internal arrays.
   *
   * <p>The *ALIAS feature allows the same file to be opened more than once, and also allows for
   * shorter file names.
   *
   * <p>Each thread has its own internal pointer to its current row in the file array. When a thread
   * first refers to the file it will be allocated the next free row in the array, so each thread
   * will access a different row from all other threads. [Unless there are more threads than there
   * are rows in the array.]
   *
   * @param file The file (or *ALIAS) to read from.
   * @param columnNum he column number in the file. 0 = first column, 1 = second etc. "next" - go to
   *     next line of file. *ALIAS - open a file and assign it to the alias .
   */
  public static String csvRead(@NonNull String file, @NonNull Integer columnNum) {
    return FunctionsUtils.function("CSVRead", file, columnNum);
  }

  /** Algorithm for digest method. */
  public enum DigestAlgorithm {
    MD2,
    MD5,
    SHA1,
    SHA224,
    SHA256,
    SHA384,
    SHA512,
  }

  /**
   * The digest function returns an encrypted value in the specific hash algorithm with the optional
   * salt, upper case and variable name.
   *
   * @param input The String that will be encrypted
   * @param algorithm The algorithm to be used to encrypt.
   * @param salt Salt to be added to string (after it).
   * @param upper Result will be in lower case by default. Choose true to upper case results.
   */
  public static String digest(
      @NonNull String input, @NonNull DigestAlgorithm algorithm, String salt, Boolean upper) {
    return FunctionsUtils.function("digest", algorithm, input, salt, upper);
  }

  /**
   * The digest function returns an encrypted value in the specific hash algorithm with the optional
   * salt, upper case and variable name.
   *
   * @param input The String that will be encrypted
   * @param algorithm The algorithm to be used to encrypt.
   * @param salt Salt to be added to string (after it).
   */
  public static String digest(
      @NonNull String input, @NonNull DigestAlgorithm algorithm, String salt) {
    return digest(input, algorithm, salt, null);
  }

  /**
   * The digest function returns an encrypted value in the specific hash algorithm with the optional
   * salt, upper case and variable name.
   *
   * @param input The String that will be encrypted
   * @param algorithm The algorithm to be used to encrypt.
   * @param upper Result will be in lower case by default. Choose true to upper case results.
   */
  public static String digest(
      @NonNull String input, @NonNull DigestAlgorithm algorithm, Boolean upper) {
    return digest(input, algorithm, null, upper);
  }

  /**
   * The __dateTimeConvert function converts a date that is in source format to a target format
   * storing the result optionally in the variable name.
   *
   * @param source The date string to convert from Source Date Format to Target Date Format. A date
   *     as a epoch time could be use here if Source Date Format is empty.
   * @param sourceDateFormat The original date format. If empty, the Date String field must be a
   *     epoch time.
   * @param targetDateFormat The new date format.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String dateTimeConvert(
      @NonNull String source,
      String sourceDateFormat,
      String targetDateFormat,
      Variable variableName) {
    return FunctionsUtils.function(
        "dateTimeConvert", source, sourceDateFormat, targetDateFormat, variableName);
  }

  /**
   * The __dateTimeConvert function converts a date that is in source format to a target format
   * storing the result optionally in the variable name.
   *
   * @param source The date string to convert from Source Date Format to Target Date Format. A date
   *     as a epoch time could be use here if Source Date Format is empty.
   * @param sourceDateFormat The original date format. If empty, the Date String field must be a
   *     epoch time.
   * @param targetDateFormat The new date format.
   */
  public static String dateTimeConvert(
      @NonNull String source, String sourceDateFormat, String targetDateFormat) {
    return dateTimeConvert(source, sourceDateFormat, targetDateFormat, null);
  }

  /**
   * The __dateTimeConvert function converts a date that is in source format to a target format
   * storing the result optionally in the variable name. the Date String field must be a epoch time.
   *
   * @param source The date string to convert from Source Date Format to Target Date Format. A date
   *     as a epoch time could be use here if Source Date Format is empty.
   * @param targetDateFormat The new date format.
   */
  public static String dateTimeConvert(@NonNull String source, String targetDateFormat) {
    return dateTimeConvert(source, targetDateFormat, (Variable) null);
  }

  /**
   * The __dateTimeConvert function converts a date that is in source format to a target format
   * storing the result optionally in the variable name. the Date String field must be a epoch time.
   *
   * @param source The date string to convert from Source Date Format to Target Date Format. A date
   *     as a epoch time could be use here if Source Date Format is empty.
   * @param targetDateFormat The new date format.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String dateTimeConvert(
      @NonNull String source, String targetDateFormat, Variable variableName) {
    return dateTimeConvert(source, null, targetDateFormat, variableName);
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
    return counter(perThread, null);
  }

  /**
   * The char function returns the result of evaluating a list of numbers as Unicode characters. See
   * also __unescape(), below.
   *
   * @param input The decimal number (or hex number, if prefixed by 0x, or octal, if prefixed by 0)
   *     to be converted to a Unicode character..
   */
  public static String chars(String... input) {
    return FunctionsUtils.function("char", (Object[]) input);
  }

  /**
   * Encode strings using HTML encoding.
   *
   * @param input String to escape.
   */
  public static String escapeHtml(String input) {
    return FunctionsUtils.function(" escapeHtml", input);
  }

  /**
   * quote meta chars used by ORO regular expression .
   *
   * @param input String to escape.
   */
  public static String escapeOroRegexpChars(String input) {
    return escapeOroRegexpChars(input, null);
  }

  /**
   * quote meta chars used by ORO regular expression .
   *
   * @param input String to escape.
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String escapeOroRegexpChars(String input, Variable variableName) {
    return FunctionsUtils.function(" escapeOroRegexpChars", input, variableName);
  }

  /**
   * Encode strings using XMl encoding .
   *
   * @param input String to escape.
   */
  public static String escapeXml(String input) {
    return FunctionsUtils.function(" escapeXml", input);
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
   * The FileToString function can be used to read an entire file. Each time it is called it reads
   * the entire file. If an error occurs opening or reading the file, then the function returns the
   * string "**ERR**"
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory)
   * @param encoding The encoding to be used to read the file. If not specified, the platform
   *     default is used.
   * @param variable A reference name for reusing the value computed by this function.
   */
  public static String fileToString(@NonNull String file, String encoding, Variable variable) {
    return FunctionsUtils.function("FileToString", file, encoding, variable);
  }

  /**
   * The FileToString function can be used to read an entire file. Each time it is called it reads
   * the entire file. If an error occurs opening or reading the file, then the function returns the
   * string "**ERR**"
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory)
   * @param encoding The encoding to be used to read the file. If not specified, the platform
   *     default is used.
   */
  public static String fileToString(@NonNull String file, String encoding) {
    return fileToString(file, encoding, null);
  }

  /**
   * The FileToString function can be used to read an entire file. Each time it is called it reads
   * the entire file. If an error occurs opening or reading the file, then the function returns the
   * string "**ERR**"
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory)
   * @param variable A reference name for reusing the value computed by this function.
   */
  public static String fileToString(@NonNull String file, Variable variable) {
    return fileToString(file, null, variable);
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
   * The intSum function can be used to compute the sum of two or more integer values.
   *
   * @param values value to be logged.
   * @param variable A reference name for reusing the value computed by this function.
   * @return sum.
   */
  public static String intSum(Variable variable, String... values) {
    List<Object> params = new ArrayList<>(Arrays.asList(values));
    params.add(variable);
    return FunctionsUtils.function("intSum", params.toArray());
  }

  /**
   * The intSum function can be used to compute the sum of two or more integer values.
   *
   * @param values value to be logged.
   * @return sum.
   */
  public static String intSum(String... values) {
    return intSum(null, values);
  }

  /**
   * The intSum function can be used to compute the sum of two or more integer values.
   *
   * @param values value to be logged.
   * @param variable A reference name for reusing the value computed by this function.
   * @return sum.
   */
  public static String intSum(Variable variable, Integer... values) {
    String[] itemsArray = new String[values.length];
    return intSum(
        variable,
        Arrays.stream(values)
            .map(Object::toString)
            .collect(Collectors.toList())
            .toArray(itemsArray));
  }

  /**
   * The intSum function can be used to compute the sum of two or more integer values.
   *
   * @param values value to be logged.
   * @return sum.
   */
  public static String intSum(Integer... values) {
    return intSum(null, values);
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
   * Log Level. The OUT and ERR log level names are used to direct the output to System.out and
   * System.err respectively. In this case, the output is always printed - it does not depend on the
   * current log setting.
   */
  public enum LogLevel {
    OUT,
    ERR,
    DEBUG,
    INFO,
    WARN,
    ERROR
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel OUT, ERR, DEBUG, INFO (default), WARN or ERROR
   * @param throwable If non-empty, creates a Throwable to pass to the logger.
   * @param comment If present, it is displayed in the string. Useful for identifying what is being
   *     logged.
   * @return logged text.
   */
  public static String log(String input, LogLevel logLevel, String throwable, String comment) {
    return FunctionsUtils.function("log", input, logLevel, throwable, comment);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged.
   * @param comment If present, it is displayed in the string. Useful for identifying what is being
   *     logged.
   */
  public static String log(String input, LogLevel logLevel, String comment) {
    return log(input, logLevel, null, comment);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
   * @param logLevel String to be logged. logged.
   */
  public static String log(String input, LogLevel logLevel) {
    return log(input, logLevel, null);
  }

  /**
   * The log function logs a message, and returns its input string.
   *
   * @param input String to be logged.
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
   * The longSum function can be used to compute the sum of two or more long values, use this
   * instead of __intSum whenever you know your values will not be in the interval -2147483648 to
   * 2147483647.
   *
   * @param values value to be logged.
   * @param variable A reference name for reusing the value computed by this function.
   * @return sum.
   */
  public static String longSum(Variable variable, String... values) {
    List<Object> params = new ArrayList<>(Arrays.asList(values));
    params.add(variable);
    return FunctionsUtils.function("longSum", params.toArray());
  }

  /**
   * The longSum function can be used to compute the sum of two or more long values, use this
   * instead of __intSum whenever you know your values will not be in the interval -2147483648 to
   * 2147483647.
   *
   * @param values value to be logged.
   * @return sum.
   */
  public static String longSum(String... values) {
    return longSum(null, values);
  }

  /**
   * The longSum function can be used to compute the sum of two or more long values, use this
   * instead of __intSum whenever you know your values will not be in the interval -2147483648 to
   * 2147483647.
   *
   * @param values value to be logged.
   * @param variable A reference name for reusing the value computed by this function.
   * @return sum.
   */
  public static String longSum(Variable variable, Long... values) {
    String[] itemsArray = new String[values.length];
    return longSum(
        variable,
        Arrays.stream(values)
            .map(Object::toString)
            .collect(Collectors.toList())
            .toArray(itemsArray));
  }

  /**
   * The longSum function can be used to compute the sum of two or more long values, use this
   * instead of __intSum whenever you know your values will not be in the interval -2147483648 to
   * 2147483647.
   *
   * @param values value to be logged.
   * @return sum.
   */
  public static String longSum(Long... values) {
    return longSum(null, values);
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
    return randomDate(format, startDate, endDate, locale, null);
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
    return randomDate(format, startDate, endDate, null);
  }

  /**
   * The RandomFromMultipleVars function returns a random value based on the variable values
   * provided by Source Variables.
   *
   * @param variable A reference name for reusing the value computed by this function.
   * @param sourceVariables Source Variables.
   */
  public static String randomFromMultipleVars(
      @NonNull Variable variable, @NonNull Variable... sourceVariables) {

    String values =
        String.join(
            "|",
            Arrays.asList(sourceVariables).stream()
                .map(Variable::getName)
                .collect(Collectors.toList()));
    return FunctionsUtils.function("RandomFromMultipleVars", values, variable);
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

  /**
   * parse previous response using a regular expression.
   *
   * @param regex Regular expression used to search previous sample - or variable..
   * @param template Template for the replacement string, using groups from the regular expression.
   *     Format is $[group]$. Example $1$.
   * @param match Which match to use. An integer 1 or greater, RAND to indicate JMeter should
   *     randomly choose, A float, or ALL indicating all matches should be used ([1]).
   */
  public static String regexFunction(@NonNull String regex, String template, String match) {
    return FunctionsUtils.function("regexFunction", regex, template, match);
  }

  /**
   * parse previous response using a regular expression.
   *
   * @param regex Regular expression used to search previous sample - or variable..
   * @param template Template for the replacement string, using groups from the regular expression.
   *     Format is $[group]$. Example $1$.
   * @param match Which match to use. An integer 1 or greater, RAND to indicate JMeter should
   *     randomly choose, A float, or ALL indicating all matches should be used ([1]).
   */
  public static String regexFunction(@NonNull String regex, String template, int match) {
    return regexFunction(regex, template, String.valueOf(match));
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
   * The setProperty function sets the value of a JMeter property. The default return value from the
   * function is the empty string, so the function call can be used anywhere functions are valid.
   *
   * @param input String to split, A delimited string, e.g. "a|b|c".
   * @param variableName A reference name for reusing the value computed by this function.
   * @param separator The delimiter character, e.g. |. If omitted, , is used. Note that , would need
   *     to be specified as \,.
   */
  @SuppressWarnings("PMD.AvoidReassigningParameters")
  public static String split(
      @NonNull String input, @NonNull String variableName, String separator) {
    if (separator != null) {
      separator = separator.replace(",", "\\,");
    }
    return FunctionsUtils.function("split", input, variableName, separator);
  }

  /**
   * The setProperty function sets the value of a JMeter property. The default return value from the
   * function is the empty string, so the function call can be used anywhere functions are valid.
   *
   * @param input String to split, A delimited string, e.g. "a|b|c".
   * @param variableName A reference name for reusing the value computed by this function.
   */
  public static String split(@NonNull String input, @NonNull String variableName) {

    return split(input, variableName, null);
  }

  /**
   * The StringFromFile function can be used to read strings from a text file. This is useful for
   * running tests that require lots of variable data. For example when testing a banking
   * application, 100s or 1000s of different account numbers might be required.
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory) If
   *     using * optional sequence numbers, the path name should be suitable for passing to
   *     DecimalFormat.
   * @param variable A reference name for reusing the value computed by this function.
   * @param startSequence Initial Sequence number (if omitted, the End sequence number is treated as
   *     a loop count).
   * @param endSequence Final sequence number (if omitted, sequence numbers can increase without
   *     limit).
   */
  public static String stringFromFile(
      @NonNull String file, Variable variable, String startSequence, String endSequence) {
    return FunctionsUtils.function("StringFromFile", file, variable, startSequence, endSequence);
  }

  /**
   * The StringFromFile function can be used to read strings from a text file. This is useful for
   * running tests that require lots of variable data. For example when testing a banking
   * application, 100s or 1000s of different account numbers might be required.
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory) If
   *     using * optional sequence numbers, the path name should be suitable for passing to
   *     DecimalFormat.
   * @param variable A reference name for reusing the value computed by this function.
   * @param startSequence Initial Sequence number (if omitted, the End sequence number is treated as
   *     a loop count).
   * @param endSequence Final sequence number (if omitted, sequence numbers can increase without
   *     limit).
   */
  public static String stringFromFile(
      @NonNull String file, Variable variable, Integer startSequence, Integer endSequence) {
    return stringFromFile(
        file, variable, String.valueOf(startSequence), String.valueOf(endSequence));
  }

  /**
   * The StringFromFile function can be used to read strings from a text file. This is useful for
   * running tests that require lots of variable data. For example when testing a banking
   * application, 100s or 1000s of different account numbers might be required.
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory) If
   *     using * optional sequence numbers, the path name should be suitable for passing to
   *     DecimalFormat.
   * @param variable A reference name for reusing the value computed by this function.s
   */
  public static String stringFromFile(@NonNull String file, Variable variable) {
    return stringFromFile(file, variable, (String) null, (String) null);
  }

  /**
   * The StringFromFile function can be used to read strings from a text file. This is useful for
   * running tests that require lots of variable data. For example when testing a banking
   * application, 100s or 1000s of different account numbers might be required.
   *
   * @param file Path to the file name. (The path can be relative to the JMeter launch directory) If
   *     using * optional sequence numbers, the path name should be suitable for passing to
   *     DecimalFormat.
   */
  public static String stringFromFile(@NonNull String file) {
    return stringFromFile(file, null, (String) null, (String) null);
  }

  /**
   * The __StringToFile function can be used to write a string to a file. Each time it is called it
   * writes a string to file appending or overwriting.
   *
   * @param file Path to file.
   * @param input The string to write to the file. If you need to insert a line break in your
   *     content, use \n in your string.
   * @param append The way to write the string, true means append, false means overwrite. If not
   *     specified, the default append is true.
   * @param encoding The encoding to be used to write to the file. If not specified, the default
   *     encoding is UTF-8.
   */
  public static String stringToFile(
      @NonNull String file, @NonNull String input, Boolean append, String encoding) {
    return FunctionsUtils.function(
        "StringToFile",
        file,
        input,
        append == null ? "" : append.toString().toLowerCase(Locale.ROOT),
        encoding);
  }

  /**
   * The __StringToFile function can be used to write a string to a file. Each time it is called it
   * writes a string to file appending or overwriting.
   *
   * @param file Path to file.
   * @param input The string to write to the file. If you need to insert a line break in your
   *     content, use \n in your string.
   * @param append The way to write the string, true means append, false means overwrite. If not
   *     specified, the default append is true.
   */
  public static String stringToFile(@NonNull String file, @NonNull String input, Boolean append) {
    return stringToFile(file, input, append, null);
  }

  /**
   * The __StringToFile function can be used to write a string to a file. Each time it is called it
   * writes a string to file appending or overwriting.
   *
   * @param file Path to file.
   * @param input The string to write to the file. If you need to insert a line break in your
   *     content, use \n in your string.
   */
  public static String stringToFile(@NonNull String file, @NonNull String input) {
    return stringToFile(file, input, null, null);
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

  /**
   * Decode a application/x-www-form-urlencoded string.
   *
   * @param url String with URL encoded chars to decode.
   */
  public static String urlDecode(String url) {
    return FunctionsUtils.function(" urldecode", url);
  }

  /**
   * Process strings containing Java escapes (e.g. \n and \t).
   *
   * @param input String containing Java escapes.
   */
  public static String unescape(String input) {
    return FunctionsUtils.function(" unescape", input);
  }

  /**
   * Decode HTML-encoded strings.
   *
   * @param input String to unescape.
   */
  public static String unescapeHtml(String input) {
    return FunctionsUtils.function(" unescapeHtml", input);
  }

  /**
   * Encode a string to a application/x-www-form-urlencoded string.
   *
   * @param url String to encode in URL encoded chars.
   */
  public static String urlEncode(String url) {
    return FunctionsUtils.function(" urlencode", url);
  }

  /** The UUID function returns a pseudo random type 4 Universally Unique IDentifier (UUID).. */
  public static String uuid() {
    return FunctionsUtils.function("UUID");
  }

  /**
   * The V (variable) function returns the result of evaluating a variable name expression. This can
   * be used to evaluate nested variable references (which are not currently supported).
   *
   * <p>For example, if one has variables A1,A2 and N=1:
   *
   * <p>${A1} - works OK ${A${N}} - does not work (nested variable reference) ${__V(A${N})} - works
   * OK. A${N} becomes A1, and the __V function returns the value of A1
   *
   * @param varName The variable to be evaluated.
   * @param defaultValue The default value in case no variable found, if it's empty and no variable
   *     found function returns the variable name.
   */
  public static String variableValue(String varName, String defaultValue) {
    return FunctionsUtils.function("V", varName, defaultValue);
  }

  /**
   * The V (variable) function returns the result of evaluating a variable name expression. This can
   * be used to evaluate nested variable references (which are not currently supported).
   *
   * <p>For example, if one has variables A1,A2 and N=1:
   *
   * <p>${A1} - works OK ${A${N}} - does not work (nested variable reference) ${__V(A${N})} - works
   * OK. A${N} becomes A1, and the __V function returns the value of A1
   *
   * @param varName The variable to be evaluated.
   */
  public static String variableValue(String varName) {
    return variableValue(varName, null);
  }

  /**
   * The XPath function reads an XML file and matches the XPath. Each time the function is called,
   * the next match will be returned. At end of file, it will wrap around to the start. If no nodes
   * matched, then the function will return the empty string, and a warning message will be written
   * to the JMeter log file.
   *
   * @param file a XML file to parse.
   * @param xpath a XPath expression to match nodes in the XML file
   */
  public static String xpath(@NonNull String file, @NonNull String xpath) {
    return FunctionsUtils.function("XPath", file, xpath);
  }
}
