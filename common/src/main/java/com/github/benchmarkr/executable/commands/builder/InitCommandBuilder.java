package com.github.benchmarkr.executable.commands.builder;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandBuilder;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.github.benchmarkr.util.LogLevel;

public class InitCommandBuilder extends BenchmarkrCommandBuilder<InitCommandBuilder> {
  public static final String ACTION = "init";

  public InitCommandBuilder(String executableAbsolutePath) {
    super(executableAbsolutePath);
  }

  public InitCommandBuilder(BenchmarkrSettingsState state) {
    super(state.getBenchmarkrExecutablePath());
    username(state.getUsername());
    password(state.getPassword());
    elasticUrl(state.getElasticsearchUrl());
    kibanaUrl(state.getKibanaUrl());
  }

  public InitCommandBuilder username(String username) {
    return addArgument(USERNAME_FLAG, username);
  }

  public InitCommandBuilder password(String password) {
    return addArgument(PASSWORD_FLAG, password);
  }

  public InitCommandBuilder elasticUrl(String elasticUrl) {
    return addArgument(ELASTIC_URL_FLAG, elasticUrl);
  }

  public InitCommandBuilder kibanaUrl(String kibanaUrl) {
    return addArgument(KIBANA_URL_FLAG, kibanaUrl);
  }

  public InitCommandBuilder logLevel(LogLevel logLevel) {
    return addArgument(LOG_LEVEL_FLAG, logLevel.toString());
  }

  @Override
  protected String action() {
    return ACTION;
  }
}
