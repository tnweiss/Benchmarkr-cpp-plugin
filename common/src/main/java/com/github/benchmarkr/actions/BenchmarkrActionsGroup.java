package com.github.benchmarkr.actions;

import java.util.List;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.diagnostic.Logger;

import org.jetbrains.annotations.NotNull;

/**
 * Group of Benchmarkr actions
 */
public class BenchmarkrActionsGroup extends DefaultActionGroup {
  private static final Logger log = Logger.getInstance(BenchmarkrActionsGroup.class);
  public static final String NAME = "Benchmarkr";

  /**
   * Default constructor
   */
  public BenchmarkrActionsGroup() {
    super(NAME, List.of(
        new BenchmarkrTestConnectionAction(),
        new BenchmarkrInitElkAction(),
        new BenchmarkrUploadAnAction(),
        new BenchmarkrUploadWatchAnAction()
    ));

    super.setPopup(true);
  }

  @Override
  public void update(@NotNull AnActionEvent event) {
    log.debug("Updating BenchmarkrActionsGroup");

    Presentation presentation = event.getPresentation();

    presentation.setIcon(BenchmarkrIcons.BENCHMARKR_TOOL_WINDOW_ICON);
    presentation.setText(NAME);
  }

}
