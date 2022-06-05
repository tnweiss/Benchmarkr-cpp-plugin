package com.github.benchmarkr.actions;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandAsyncResult;
import com.github.benchmarkr.executable.commands.builder.UploadWatchCommandBuilder;
import com.github.benchmarkr.notifications.BenchmarkrNotifications;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrUploadWatchBackgroundable extends Task.Backgroundable {
  private static final int PAUSE_TIME = 10;

  private BenchmarkrCommandAsyncResult benchmarkrCommandAsyncResult;

  public BenchmarkrUploadWatchBackgroundable(Project project) {
    super(project, "Watching for benchmarkr results...");
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    try {
      this.benchmarkrCommandAsyncResult = new UploadWatchCommandBuilder(BenchmarkrSettingsState.getInstance())
          .build()
          .executeAsync();

      while (!this.benchmarkrCommandAsyncResult.runFor(PAUSE_TIME) && !indicator.isCanceled());
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
