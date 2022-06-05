package com.github.benchmarkr.executable.commands.builder;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandBuilder;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.github.benchmarkr.util.LogLevel;

public class UploadCommandBuilder extends BenchmarkrCommandBuilder<UploadCommandBuilder> {
  public static final String ACTION = "upload";

  public UploadCommandBuilder(String executableAbsolutePath) {
    super(executableAbsolutePath);
  }

  public UploadCommandBuilder(BenchmarkrSettingsState state) {
    super(state.getBenchmarkrExecutablePath());
    username(state.getUsername());
    password(state.getPassword());
    elasticUrl(state.getElasticsearchUrl());
  }

  public UploadCommandBuilder username(String username) {
    return addArgument(USERNAME_FLAG, username);
  }

  public UploadCommandBuilder password(String password) {
    return addArgument(PASSWORD_FLAG, password);
  }

  public UploadCommandBuilder elasticUrl(String elasticUrl) {
    return addArgument(ELASTIC_URL_FLAG, elasticUrl);
  }

  public UploadCommandBuilder logLevel(LogLevel logLevel) {
    return addArgument(LOG_LEVEL_FLAG, logLevel.toString());
  }

  @Override
  protected String action() {
    return ACTION;
  }
}
