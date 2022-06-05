package com.github.benchmarkr.executable.commands.builder;

import com.github.benchmarkr.executable.commands.BenchmarkrCommandBuilder;

public class VersionCommandBuilder extends BenchmarkrCommandBuilder<VersionCommandBuilder> {
  public static final String ACTION = "version";

  public VersionCommandBuilder(String executableAbsolutePath) {
    super(executableAbsolutePath);
  }

  @Override
  protected String action() {
    return ACTION;
  }
}
