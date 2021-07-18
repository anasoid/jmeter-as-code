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
      LOG.warn("Module Controller is using in test : " + testPlan);
      return;
    }

    ModuleControllerWrapper moduleControllerWrapper = (ModuleControllerWrapper) testElementWrapper;

    List<List<AbstractTestElementWrapper<?>>> targets =
        findTarget(
            (TestPlanWrapper) testPlan,
            moduleControllerWrapper.getRootParent(),
            moduleControllerWrapper.getModule(),
            new ArrayList<>());
    if (targets.isEmpty()) {
      throw new ConversionException(
          "Module target not found for :" + moduleControllerWrapper.getName());
    }
    if (targets.size() > 1) { // NOPMD
      throw new ConversionException("Ambiguous target for :" + moduleControllerWrapper.getName());
    }

    List<AbstractTestElementWrapper<?>> target = targets.get(0);

    check(testPlan, target, 0);
    List<String> nodePath = new ArrayList<>();
    nodePath.add("Test Plan");
    nodePath.add(((TestPlanWrapper) testPlan).getName());
    target.forEach(c -> nodePath.add(c.getName()));
    moduleControllerWrapper.setNodePath(nodePath);
  }

  protected List<List<AbstractTestElementWrapper<?>>> findTarget(
      TestPlanWrapper testPlan,
      TestFragmentWrapper testFragment,
      ControllerWrapper target,
      List<TestElementWrapper<?>> parentList) {

    List<List<AbstractTestElementWrapper<?>>> result = new ArrayList<>();
    List<TestElementWrapper> testElementLists;

    if (parentList.isEmpty()) {
      testElementLists =
          testPlan.getChilds().stream()
              .filter(c -> c instanceof TestFragmentWrapper)
              .filter(c -> testFragment == null || c.equals(testFragment))
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
      if (currentElement == target) { // NOPMD
        result.add(ListUtils.sum(currentList, Arrays.asList(currentElement)));
      } else {
        List<List<AbstractTestElementWrapper<?>>> childList =
            findTarget(testPlan, testFragment, target, currentList);
        childList.stream().forEach(c -> result.add(ListUtils.sum(currentList, c)));
      }
    }

    return result;
  }

  protected void check(
      TestElementWrapper root, List<AbstractTestElementWrapper<?>> target, int level) {
    List<AbstractTestElementWrapper> children;
    AbstractTestElementWrapper node = target.get(level);
    ControllerWrapper module = (ControllerWrapper) target.get(target.size() - 1);
    if (node.getName() == null) {
      throw new ConversionException("Illegal Name null on :" + node);
    }
    children =
        (List<AbstractTestElementWrapper>)
            root.getChilds().stream()
                .filter(c -> c instanceof TestFragmentWrapper || c instanceof ControllerWrapper)
                .filter(
                    c ->
                        StringUtils.equals(
                            node.getName(), ((AbstractTestElementWrapper) c).getName()))
                .collect(Collectors.toList());

    if (level + 1 == target.size()) {
      if (children.size() > 1) { // NOPMD
        throw new ConversionException("Duplicate module target found : " + children);
      }
      if (children.size() == 1 && !module.equals(children.get(0))) {
        throw new ConversionException("Duplicate names module target found : " + children.get(0));
      } else {
        return;
      }
    }
    children.forEach(c -> check(c, target, level + 1));
  }
}
