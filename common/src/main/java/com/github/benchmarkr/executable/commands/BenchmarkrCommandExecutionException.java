package com.github.benchmarkr.executable.commands;

public class BenchmarkrCommandExecutionException extends Exception {
  public BenchmarkrCommandExecutionException(Throwable t) {
    super(t);
  }

  public BenchmarkrCommandExecutionException(String s) {
    super(s);
  }

}
