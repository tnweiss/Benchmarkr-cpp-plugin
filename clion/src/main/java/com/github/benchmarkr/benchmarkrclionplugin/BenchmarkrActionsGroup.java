package com.github.benchmarkr.benchmarkrclionplugin;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class BenchmarkrActionsGroup extends DefaultActionGroup {

  @Override
  public void update(AnActionEvent event) {
    System.out.println("Updating Group");
    System.out.println(BenchmarkrIcons.BenchmarkrGutterIcon);
    event.getPresentation().setIcon(BenchmarkrIcons.BenchmarkrGutterIcon);
  }

}
