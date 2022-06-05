package com.github.benchmarkr.executable.commands;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.github.benchmarkr.actions.BenchmarkrActionsGroup;
import com.intellij.openapi.diagnostic.Logger;

public class BenchmarkrCommandResult {
  private static final Logger log = Logger.getInstance(BenchmarkrActionsGroup.class);
  private final String[] arguments;
  private final int exitCode;
  private final String output;

  public BenchmarkrCommandResult(Process process, String[] arguments) {
    log.info("Process exitCode " + process.exitValue());
    log.info("Process isAlive" + process.isAlive());

    this.arguments = arguments;
    this.exitCode = process.exitValue();

    try {
      output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    } catch (IOException exception) {
      throw new IllegalArgumentException(exception);
    }

    // if still alive destroy the process
    if (process.isAlive()) {
      log.info("Process is still alive, destroying");
      process.destroy();
    }
  }

  /**
   * Get the process output
   * @return the terminal output
   */
  public String output() {
    return output;
  }

  /**
   * Get the output if the process completed successfully.
   * If the exit code is not 0, re run the command with a log level of debug to give the user more
   * context on the failure
   * @return get the terminal output
   */
  public String outputOrRerunWithDebug() throws BenchmarkrCommandExecutionException {
    if (exitCode == 0) {
      return output;
    }

    log.info("process failed on first attempt, rerunning with debug");

    // recreate modifiable args
    List<String> args = new ArrayList<>(List.of(arguments));

    if (!args.contains("-l")) {
      // if the log level is not present, add it to the list of flags
      log.debug("Adding debug flag");

      args.add("-l");
      args.add("debug");
    } else {
      // if present modify the current value
      log.debug("Modifying existing log level " + args.get(args.indexOf("-l") + 1));

      args.set(args.indexOf("-l") + 1, "debug");
    }

    // rerun execution
    return new BenchmarkrCommand(args.toArray(new String[0]))
        .execute()
        .output();
  }
}
