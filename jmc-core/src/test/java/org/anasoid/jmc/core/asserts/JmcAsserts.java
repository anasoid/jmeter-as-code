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
 * Date :   06-Feb-2021
 */

package org.anasoid.jmc.core.asserts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.xmlunit.matchers.HasXPathMatcher.hasXPath;

import org.anasoid.jmc.core.wrapper.jmeter.testelement.property.JMeterProperty;
import org.xmlunit.matchers.EvaluateXPathMatcher;

/** Groupings asserts used for Xml and non Xml validations. */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.UseObjectForClearerAPI"})
public final class JmcAsserts {

  private static JmcAsserts instance;

  private final String xpathRoot;

  private JmcAsserts() {
    this.xpathRoot = "/jmeterTestPlan/hashTree";
  }

  private JmcAsserts(String xpathRoot) {
    this.xpathRoot = xpathRoot;
  }

  /** get instance with xpathRoot = "/jmeterTestPlan/hashTree" . */
  public static JmcAsserts get() {
    if (instance == null) { // NOPMD
      instance = new JmcAsserts();
    }
    return instance;
  }

  /**
   * get instance with xpathRoot .
   *
   * @param xpathRoot xpath root.
   */
  public static JmcAsserts get(String xpathRoot) {
    return new JmcAsserts(xpathRoot);
  }

  private String getFullXpath(String xpath) {
    return xpathRoot + xpath;
  }

  /**
   * assert has Xpath.
   *
   * @param xpath relative xpath.
   */
  public void assertHasXPath(String content, String xpath) {
    assertThat(content, hasXPath(getFullXpath(xpath)));
  }

  /**
   * assert not has Xpath.
   *
   * @param xpath relative xpath.
   */
  public void assertNotHasXPath(String content, String xpath) {
    assertThat(content, not(hasXPath(getFullXpath(xpath))));
  }

  /**
   * Assert value for xpath.
   *
   * @param xpath relative xpath.
   * @param value value to be checked.
   */
  public void assertXPathValue(String content, String xpath, String value) {
    assertThat(content, EvaluateXPathMatcher.hasXPath(getFullXpath(xpath), equalTo(value)));
  }

  /**
   * find xpath value with filter attribute=attributeValue, and assert value.
   *
   * @param xpath relative xpath.
   * @param attribute attribute key.
   * @param attributeValue attribute Value.
   * @param value value to be checked.
   */
  public void assertXPathFindAttributeValue(
      String content, String xpath, String attribute, String attributeValue, String value) {
    assertThat(
        content,
        EvaluateXPathMatcher.hasXPath(
            getFullXpath(xpath) + "[@" + attribute + "='" + attributeValue + "']", equalTo(value)));
  }

  /**
   * Assert value using xpath and last node name.
   *
   * @param xpath relative xpath.
   * @param lastNode last node name, to be add to xpath.
   * @param value value to be checked.
   */
  public void assertXPathNodeValue(String content, String xpath, String lastNode, String value) {
    assertThat(
        content,
        EvaluateXPathMatcher.hasXPath(getFullXpath(xpath) + "/" + lastNode, equalTo(value)));
  }

  /**
   * Check attribute value by attribute key on last node.
   *
   * @param xpath relative xpath.
   * @param lastNode last node name, to be add to xpath.
   * @param attribute attribute key.
   * @param value attribute value to be checked.
   */
  public void assertXPathNodeAttributeValue(
      String content, String xpath, String lastNode, String attribute, String value) {
    assertXPathAttributeValue(content, xpath + "/" + lastNode, attribute, value);
  }

  /**
   * Check attribute value by attribute key.
   *
   * @param xpath relative xpath.
   * @param attribute attribute key.
   * @param value attribute value to be checked.
   */
  public void assertXPathAttributeValue(
      String content, String xpath, String attribute, String value) {
    assertThat(
        content,
        EvaluateXPathMatcher.hasXPath(getFullXpath(xpath) + "/@" + attribute, equalTo(value)));
  }

  /**
   * check value and name of stringProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropString(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.STRING.value(), name, value);
  }

  /**
   * check value and name of intProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropInt(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.INTEGER.value(), name, value);
  }

  /**
   * check value and name of boolProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropBool(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.BOOL.value(), name, value);
  }

  /**
   * check value and name of longProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropLong(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.LONG.value(), name, value);
  }

  /**
   * check value and name of floatProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropFloat(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.FLOAT.value(), name, value);
  }

  /**
   * check value and name of doubleProp.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be checked.
   * @param value value to be checked.
   */
  public void assertXPathPropDouble(String content, String xpath, String name, String value) {
    assertXPathProp(content, xpath, JMeterProperty.DOUBLE.value(), name, value);
  }

  public void assertXPathProp(
      String content, String xpath, String property, String name, String value) {
    assertXPathFindAttributeValue(content, xpath + "/" + property, "name", name, value);
  }

  /**
   * check is intProp present with name is present.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be filtred by.
   */
  public void assertHasXPathPropInt(String content, String xpath, String name) {
    assertHasXPathProp(content, xpath, JMeterProperty.INTEGER.value(), name);
  }

  /**
   * check is longProp present with name is present.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be filtred by.
   */
  public void assertHasXPathPropLong(String content, String xpath, String name) {
    assertHasXPathProp(content, xpath, JMeterProperty.LONG.value(), name);
  }

  /**
   * check is longProp not present with name is present.
   *
   * @param xpath relative xpath.
   * @param name attribute name value to be filtred by.
   */
  public void assertNotHasXPathPropLong(String content, String xpath, String name) {
    assertNotHasXPathProp(content, xpath, JMeterProperty.LONG.value(), name);
  }

  /**
   * Assert has xpath with property name.
   *
   * @param xpath relative xpath.
   * @param property property name.
   * @param name attribute name value.
   */
  public void assertHasXPathProp(String content, String xpath, String property, String name) {
    assertHasXPath(content, xpath + "/" + property + "[@name='" + name + "']");
  }

  /**
   * Assert not has xpath with property name.
   *
   * @param xpath relative xpath.
   * @param property property name.
   * @param name attribute name value.
   */
  public void assertNotHasXPathProp(String content, String xpath, String property, String name) {
    assertNotHasXPath(content, xpath + "/" + property + "[@name='" + name + "']");
  }
}
