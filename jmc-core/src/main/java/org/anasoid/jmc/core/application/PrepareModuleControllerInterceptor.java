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
import org.anasoid.jmc.core.xstream.exceptions.ConversionException;
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
        && moduleControllerWrapper.getModuleName() == null) {
      throw new ConversionException(
          "Module target can't be empty :" + moduleControllerWrapper.getName());
    }
    if (moduleControllerWrapper.getModule() != null
        && moduleControllerWrapper.getModuleName() != null) {
      throw new ConversionException(
          "Module target can be set by name or by reference not both at the same time :"
              + moduleControllerWrapper.getName());
    }

    List<List<AbstractTestElementWrapper<?>>> targets =
        findTarget(
            (TestPlanWrapper) testPlan,
            moduleControllerWrapper.getRootParent(),
            moduleControllerWrapper.getModuleName() == null
                ? ((AbstractTestElementWrapper) moduleControllerWrapper.getModule()).getName()
                : moduleControllerWrapper.getModuleName(),
            new ArrayList<>());
    if (targets.isEmpty()) {
      throw new ConversionException(
          "Module target not found for :" + moduleControllerWrapper.getName());
    }
    if (targets.size() > 1) { // NOPMD
      throw new ConversionException("Ambiguous target for :" + moduleControllerWrapper.getName());
    }

    List<AbstractTestElementWrapper<?>> target = targets.get(0);

    List<String> nodePath = new ArrayList<>();
    nodePath.add("Test Plan");
    nodePath.add(((TestPlanWrapper) testPlan).getName());
    target.forEach(c -> nodePath.add(c.getName()));

    moduleControllerWrapper.setNodePath(nodePath);
  }

  /** Find target. */
  protected List<List<AbstractTestElementWrapper<?>>> findTarget(
      TestPlanWrapper testPlan,
      TestFragmentWrapper testFragment,
      String targetName,
      List<TestElementWrapper<?>> parentList) {

    List<List<AbstractTestElementWrapper<?>>> result = new ArrayList<>();
    List<TestElementWrapper> testElementLists;

    if (parentList.isEmpty()) {
      testElementLists =
          testPlan.getChilds().stream()
              .filter(TestFragmentWrapper.class::isInstance)
              .filter(
                  c ->
                      testFragment == null
                          || StringUtils.equals(
                              testFragment.getName(), ((TestFragmentWrapper) c).getName()))
              .collect(Collectors.toList());
    } else {
      testElementLists =
          parentList.get(parentList.size() - 1).getChilds().stream()
              .filter(
                  c -> c instanceof ControllerWrapper && !(c instanceof ModuleControllerWrapper))
              .collect(Collectors.toList());
    }

    for (TestElementWrapper currentElement : testElementLists) {
      List<TestElementWrapper<?>> currentList = new ArrayList<>(parentList); // NOPMD
      currentList.add(currentElement);
      if (targetName.equals(((AbstractTestElementWrapper) currentElement).getName())) { // NOPMD
        result.add(ListUtils.sum(currentList, Arrays.asList(currentElement)));
      } else {
        List<List<AbstractTestElementWrapper<?>>> childList =
            findTarget(testPlan, testFragment, targetName, currentList);
        childList.stream().forEach(c -> result.add(ListUtils.sum(currentList, c)));
      }
    }

    return result;
  }
}
