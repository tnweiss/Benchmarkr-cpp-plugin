package com.github.benchmarkr.actions;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandAsyncResult;
import com.github.benchmarkr.executable.commands.builder.UploadCommandBuilder;
import com.github.benchmarkr.notifications.BenchmarkrNotifications;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.github.benchmarkr.util.UploadResults;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrUploadBackgroundable extends Task.Backgroundable {
  private BenchmarkrCommandAsyncResult benchmarkrCommandAsyncResult;

  public BenchmarkrUploadBackgroundable(Project project) {
    super(project, "Uploading result sets...");
  }

  @Override
  public void run(@NotNull ProgressIndicator indicator) {
    try {
      this.benchmarkrCommandAsyncResult = new UploadCommandBuilder(BenchmarkrSettingsState.getInstance())
          .build()
          .executeAsync();

      while (!this.benchmarkrCommandAsyncResult.runFor(5) && !indicator.isCanceled());

      if (!indicator.isCanceled()) {
        int uploadedSets = UploadResults.resultsUploaded(this.benchmarkrCommandAsyncResult.get().output());
        BenchmarkrNotifications.info(myProject, "Uploaded " + uploadedSets + " result sets");
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
