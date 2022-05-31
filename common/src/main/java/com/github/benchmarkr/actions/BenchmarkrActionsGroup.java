package com.github.benchmarkr.actions;

import java.util.List;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;

public class BenchmarkrActionsGroup extends DefaultActionGroup {

  public BenchmarkrActionsGroup() {
    super("Benchmarkr", List.of(
        new BenchmarkrUploadAnAction()
    ));

    super.setPopup(true);
  }

  @Override
  public void update(AnActionEvent event) {
    event.getPresentation().setIcon(BenchmarkrIcons.BenchmarkrGutterIcon);
  }

}
