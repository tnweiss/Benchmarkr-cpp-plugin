package com.github.benchmarkr.actions;

import java.util.List;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrActionsGroup extends DefaultActionGroup {
  public static final String NAME = "Benchmarkr";

  public BenchmarkrActionsGroup() {
    super(NAME, List.of(
        new BenchmarkrUploadAnAction()
    ));

    super.setPopup(true);
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    Presentation presentation = event.getPresentation();

    presentation.setIcon(BenchmarkrIcons.BENCHMARKR_TOOL_WINDOW_ICON);
    presentation.setText(NAME);
  }

}
