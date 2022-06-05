package com.github.benchmarkr.executable.commands.builder;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandBuilder;
import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.github.benchmarkr.util.LogLevel;

public class TestConnectionCommandBuilder extends BenchmarkrCommandBuilder<TestConnectionCommandBuilder> {
  public static final String ACTION = "test-connection";

  public TestConnectionCommandBuilder(String executableAbsolutePath) {
    super(executableAbsolutePath);
  }

  public TestConnectionCommandBuilder(BenchmarkrSettingsState state) {
    super(state.getBenchmarkrExecutablePath());
    username(state.getUsername());
    password(state.getPassword());
    elasticUrl(state.getElasticsearchUrl());
    kibanaUrl(state.getKibanaUrl());
  }

  public TestConnectionCommandBuilder username(String username) {
    return addArgument(USERNAME_FLAG, username);
  }

  public TestConnectionCommandBuilder password(String password) {
    return addArgument(PASSWORD_FLAG, password);
  }

  public TestConnectionCommandBuilder elasticUrl(String elasticUrl) {
    return addArgument(ELASTIC_URL_FLAG, elasticUrl);
  }

  public TestConnectionCommandBuilder kibanaUrl(String kibanaUrl) {
    return addArgument(KIBANA_URL_FLAG, kibanaUrl);
  }

  public TestConnectionCommandBuilder logLevel(LogLevel logLevel) {
    return addArgument(LOG_LEVEL_FLAG, logLevel.toString());
  }

  @Override
  protected String action() {
    return ACTION;
  }
}
