package com.github.benchmarkr.executable.commands;

import java.util.concurrent.TimeUnit;

import com.intellij.openapi.diagnostic.Logger;

public class BenchmarkrCommandAsyncResult {
  private static final Logger log = Logger.getInstance(BenchmarkrCommandAsyncResult.class);
  private final Process process;
  private final String[] arguments;

  public BenchmarkrCommandAsyncResult(Process process, String[] arguments) {
    this.process = process;
    this.arguments = arguments;
  }

  public String[] arguments() {
    return arguments;
  }

  public Process process() {
    return process;
  }

  public BenchmarkrCommandResult get() throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Async Get " + process.pid());

      // wait for execution to finish
      if (process.waitFor(15, TimeUnit.SECONDS)) {
        return new BenchmarkrCommandResult(process, arguments);
      }

      log.info("Command execution exceeded timeout.");
      throw new BenchmarkrCommandExecutionException("Command execution exceeded timeout.");
    } catch (InterruptedException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }

  public void run() throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Async Run " + process.pid());

      // wait for execution to finish
      if (!process.waitFor(15, TimeUnit.SECONDS)) {
        log.info("Command execution exceeded timeout.");
        throw new BenchmarkrCommandExecutionException("Command execution exceeded timeout.");
      }
    } catch (InterruptedException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }

  public void runForever() throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Async Run Forever " + process.pid());

      process.waitFor();
    } catch (InterruptedException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }

  public boolean runFor(int seconds) throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Async Run Forever " + process.pid());

      return process.waitFor(seconds, TimeUnit.SECONDS);
    } catch (InterruptedException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }

  public void cancel() {
    process.destroy();
  }
}
