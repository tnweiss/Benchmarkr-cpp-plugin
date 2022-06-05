package com.github.benchmarkr.executable.commands;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.github.benchmarkr.actions.BenchmarkrActionsGroup;
import com.intellij.openapi.diagnostic.Logger;

public class BenchmarkrCommand {
  private static final Logger log = Logger.getInstance(BenchmarkrActionsGroup.class);

  private final String[] arguments;

  public BenchmarkrCommand(String[] arguments) {
    this.arguments = arguments;
  }

  /**
   * Execute the command
   * @return return a result object
   */
  public BenchmarkrCommandResult execute() throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Executing" + String.join(" ", arguments));

      // run the process
      Process process = Runtime.getRuntime().exec(arguments);

      // wait for execution to finish
      if (process.waitFor(15, TimeUnit.SECONDS)) {
        return new BenchmarkrCommandResult(process, arguments);
      }

      log.info("Command execution exceeded timeout.");
      throw new BenchmarkrCommandExecutionException("Command execution exceeded timeout.");
    } catch (InterruptedException | IOException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }

  /**
   * Execute the command
   * @return return a result object
   */
  public BenchmarkrCommandAsyncResult executeAsync() throws BenchmarkrCommandExecutionException {
    try {
      // notify user of command
      log.trace("Executing Async " + String.join(" ", arguments));

      // run the process
      Process process = Runtime.getRuntime().exec(arguments);

      return new BenchmarkrCommandAsyncResult(process, arguments);
    } catch (IOException ex) {
      log.info(ex);
      throw new BenchmarkrCommandExecutionException(ex);
    }
  }
}
