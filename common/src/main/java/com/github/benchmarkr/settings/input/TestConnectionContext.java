package com.github.benchmarkr.settings.input;

public interface TestConnectionContext {
  String getExecutableAbsolutePath();
  String getUsername();
  String getPassword();
  String getElasticUrl();
  String getKibanaUrl();
}
