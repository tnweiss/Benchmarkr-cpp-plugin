package com.github.benchmarkr.executable.commands.builder;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandBuilder;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.github.benchmarkr.util.LogLevel;

public class UploadWatchCommandBuilder extends BenchmarkrCommandBuilder<UploadWatchCommandBuilder> {
  public static final String ACTION = "upload-watch";

  public UploadWatchCommandBuilder(String executableAbsolutePath) {
    super(executableAbsolutePath);
  }

  public UploadWatchCommandBuilder(BenchmarkrSettingsState state) {
    super(state.getBenchmarkrExecutablePath());
    username(state.getUsername());
    password(state.getPassword());
    elasticUrl(state.getElasticsearchUrl());
    interval(state.getUploadInterval());
  }

  public UploadWatchCommandBuilder username(String username) {
    return addArgument(USERNAME_FLAG, username);
  }

  public UploadWatchCommandBuilder password(String password) {
    return addArgument(PASSWORD_FLAG, password);
  }

  public UploadWatchCommandBuilder elasticUrl(String elasticUrl) {
    return addArgument(ELASTIC_URL_FLAG, elasticUrl);
  }

  public UploadWatchCommandBuilder interval(int interval) {
    return addArgument(INTERVAL_FLAG, String.valueOf(interval));
  }

  public UploadWatchCommandBuilder logLevel(LogLevel logLevel) {
    return addArgument(LOG_LEVEL_FLAG, logLevel.toString());
  }

  @Override
  protected String action() {
    return ACTION;
  }
}
