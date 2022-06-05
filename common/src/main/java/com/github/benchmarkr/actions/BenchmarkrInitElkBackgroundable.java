package com.github.benchmarkr.actions;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandAsyncResult;
import com.github.benchmarkr.executable.commands.builder.InitCommandBuilder;
import com.github.benchmarkr.notifications.BenchmarkrNotifications;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrInitElkBackgroundable extends Task.Backgroundable {
  private BenchmarkrCommandAsyncResult benchmarkrCommandAsyncResult;

  public BenchmarkrInitElkBackgroundable(Project project) {
    super(project, "Initializing ELK indices and dashboards...");
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    try {
      this.benchmarkrCommandAsyncResult = new InitCommandBuilder(BenchmarkrSettingsState.getInstance())
          .build()
          .executeAsync();

      while (!this.benchmarkrCommandAsyncResult.runFor(5) && !indicator.isCanceled());

      if (!indicator.isCanceled()) {
        BenchmarkrNotifications.info(myProject, "Initialized indices and Dashboards ");
      }
    } catch (Exception ex) {
      BenchmarkrNotifications.error(this.myProject, ex);
    }
  }

  @Override
  public void onCancel() {
    if (benchmarkrCommandAsyncResult != null) {
      benchmarkrCommandAsyncResult.cancel();
    }
  }
}
