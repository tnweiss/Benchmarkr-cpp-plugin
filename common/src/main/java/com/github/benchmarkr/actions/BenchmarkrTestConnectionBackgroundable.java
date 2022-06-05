package com.github.benchmarkr.actions;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandAsyncResult;
import com.github.benchmarkr.executable.commands.builder.InitCommandBuilder;
import com.github.benchmarkr.executable.commands.builder.TestConnectionCommandBuilder;
import com.github.benchmarkr.notifications.BenchmarkrNotifications;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrTestConnectionBackgroundable extends Task.Backgroundable {
  private BenchmarkrCommandAsyncResult benchmarkrCommandAsyncResult;

  public BenchmarkrTestConnectionBackgroundable(Project project) {
    super(project, "Testing ELK connection...");
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    try {
      this.benchmarkrCommandAsyncResult = new TestConnectionCommandBuilder(BenchmarkrSettingsState.getInstance())
          .build()
          .executeAsync();

      while (!this.benchmarkrCommandAsyncResult.runFor(5) && !indicator.isCanceled());

      if (indicator.isCanceled()) return;

      if (this.benchmarkrCommandAsyncResult.process().exitValue() == 0) {
        BenchmarkrNotifications.info(myProject, "Successfully connected to ELK stack");
      } else {
        BenchmarkrNotifications.error(myProject, "Error connecting to ELK stack");
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
