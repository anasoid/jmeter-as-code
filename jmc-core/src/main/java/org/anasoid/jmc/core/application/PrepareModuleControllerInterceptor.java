package org.anasoid.jmc.core.application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.anasoid.jmc.core.application.interceptors.PrepareInterceptor;
import org.anasoid.jmc.core.wrapper.jmeter.control.ControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.ModuleControllerWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.control.TestFragmentWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.AbstractTestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestElementWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.testelement.TestPlanWrapper;
import org.anasoid.jmc.core.wrapper.jmeter.threads.ThreadGroupWrapper;
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Preparing ModuleController. */
class PrepareModuleControllerInterceptor implements PrepareInterceptor {

  private static final Logger LOG =
      LoggerFactory.getLogger(PrepareModuleControllerInterceptor.class);

  @Override
  public boolean support(TestElementWrapper<?> testElementWrapper) {
    return testElementWrapper instanceof ModuleControllerWrapper;
  }

  @Override
  public void prepare(TestElementWrapper<?> testPlan, TestElementWrapper<?> testElementWrapper) {

    if (!(testPlan instanceof TestPlanWrapper)) {
      LOG.warn("Module Controller is using in test : {}", testPlan);
      return;
    }

    ModuleControllerWrapper moduleControllerWrapper = (ModuleControllerWrapper) testElementWrapper;

    if (moduleControllerWrapper.getModule() == null
        && CollectionUtils.isEmpty(moduleControllerWrapper.getPath())) {
      throw new ConversionException(
          "Module target can't be empty :" + moduleControllerWrapper.getName());
    }
    if (moduleControllerWrapper.getModule() != null
        && !CollectionUtils.isEmpty(moduleControllerWrapper.getPath())) {
      throw new ConversionException(
          "Module target can be set by name or by reference not both at the same time :"
              + moduleControllerWrapper.getName());
    }

    List<List<AbstractTestElementWrapper<?>>> targets =
        findTarget(
            (TestPlanWrapper) testPlan,
            moduleControllerWrapper.getRootParent(),
            (AbstractTestElementWrapper) moduleControllerWrapper.getModule(),
            moduleControllerWrapper.getPath(),
            new ArrayList<>());
    if (targets.isEmpty()) {
      throw new ConversionException(
          "Module target not found for :" + moduleControllerWrapper.getName());
    }
    if (targets.size() > 1) { // NOPMD
      throw new ConversionException("Ambiguous target for :" + moduleControllerWrapper.getName());
    }

    final int nmFound = check(testPlan, targets.get(0), 0);

    List<AbstractTestElementWrapper<?>> target = targets.get(0);

    List<String> nodePath = new ArrayList<>();
    nodePath.add("Test Plan");
    nodePath.add(((TestPlanWrapper) testPlan).getName());
    target.forEach(c -> nodePath.add(c.getName()));

    if (nmFound != 1) { // NOPMD
      throw new ConversionException(
          "Duplicate module target found ("
              + nmFound
              + ") : "
              + moduleControllerWrapper.getName()
              + "->"
              + nodePath);
    }
    moduleControllerWrapper.setNodePath(nodePath);
  }

  /** Find target. */
  @SuppressWarnings({
    "PMD.AvoidInstantiatingObjectsInLoops",
    "PMD.AvoidLiteralsInIfCondition",
    "PMD.CognitiveComplexity"
  })
  protected List<List<AbstractTestElementWrapper<?>>> findTarget(
      TestPlanWrapper testPlan,
      String parentName,
      AbstractTestElementWrapper targetController,
      List<String> path,
      List<TestElementWrapper<?>> parentList) {

    List<List<AbstractTestElementWrapper<?>>> result = new ArrayList<>();
    List<TestElementWrapper> testElementLists =
        getFilteredTestElement(testPlan, parentName, parentList);

    for (TestElementWrapper currentElement : testElementLists) {
      List<TestElementWrapper<?>> currentList = new ArrayList<>(parentList);
      currentList.add(currentElement);
      boolean end = false;
      List<String> nextPath = null;
      if (!parentList.isEmpty()) {

        if (targetController != null) {
          if (targetController == currentElement) { // NOPMD
            result.add(ListUtils.sum(currentList, Arrays.asList(currentElement)));
            end = true;
          }
        } else {
          if (path.get(0).equals(((AbstractTestElementWrapper) currentElement).getName())) {
            if (path.size() == 1) {
              result.add(ListUtils.sum(currentList, Arrays.asList(currentElement)));
              end = true;
            }
          } else {
            end = true;
          }
        }
      } else {
        nextPath = path;
      }
      if (!end) {
        if (nextPath == null) {
          nextPath =
              CollectionUtils.isNotEmpty(path) ? path.subList(1, path.size()) : new ArrayList<>();
        }
        List<List<AbstractTestElementWrapper<?>>> childList =
            findTarget(testPlan, parentName, targetController, nextPath, currentList);
        childList.stream().forEach(c -> result.add(ListUtils.sum(currentList, c)));
      }
    }

    return result;
  }

  private List<TestElementWrapper> getFilteredTestElement(
      TestPlanWrapper testPlan, String parentName, List<TestElementWrapper<?>> parentList) {
    List<TestElementWrapper> testElementLists;

    if (parentList.isEmpty()) {
      testElementLists =
          testPlan.getChildren().stream()
              .filter(c -> c instanceof TestFragmentWrapper || c instanceof ThreadGroupWrapper)
              .filter(
                  c ->
                      parentName == null
                          || StringUtils.equals(
                              parentName, ((AbstractTestElementWrapper) c).getName()))
              .collect(Collectors.toList());
    } else {
      testElementLists =
          parentList.get(parentList.size() - 1).getChildren().stream()
              .filter(
                  c -> c instanceof ControllerWrapper && !(c instanceof ModuleControllerWrapper))
              .collect(Collectors.toList());
    }
    return testElementLists;
  }

  /**
   * check.
   *
   * @return number of occurrence >1 should be in conflict.
   */
  protected int check(
      TestElementWrapper root, List<AbstractTestElementWrapper<?>> target, int level) {
    List<AbstractTestElementWrapper> children;
    AbstractTestElementWrapper node = target.get(level);
    if (node.getName() == null) {
      throw new ConversionException("Illegal Name null on :" + node);
    }
    children =
        (List<AbstractTestElementWrapper>)
            root.getChildren().stream()
                .filter(
                    c ->
                        c instanceof TestFragmentWrapper
                            || c instanceof ControllerWrapper
                            || c instanceof ThreadGroupWrapper)
                .filter(
                    c ->
                        StringUtils.equals(
                            node.getName(), ((AbstractTestElementWrapper) c).getName()))
                .collect(Collectors.toList());

    if (level + 1 == target.size()) {
      return children.size();
    }
    return children.stream().mapToInt(c -> check(c, target, level + 1)).sum();
  }
}
