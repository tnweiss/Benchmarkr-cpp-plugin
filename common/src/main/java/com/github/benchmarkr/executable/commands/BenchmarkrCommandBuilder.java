package com.github.benchmarkr.executable.commands;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import com.github.benchmarkr.actions.BenchmarkrActionsGroup;
import com.intellij.openapi.diagnostic.Logger;


public abstract class BenchmarkrCommandBuilder <T extends BenchmarkrCommandBuilder<T>> {
  private static final Logger log = Logger.getInstance(BenchmarkrActionsGroup.class);

  protected static final String USERNAME_FLAG = "-u";
  protected static final String PASSWORD_FLAG = "-p";
  protected static final String KIBANA_URL_FLAG = "-k";
  protected static final String ELASTIC_URL_FLAG = "-e";
  protected static final String LOG_LEVEL_FLAG = "-l";
  protected static final String INTERVAL_FLAG = "-i";

  private final List<String> arguments;

  /**
   * commands are formatted as "benchmarkr [ACTION] [FLAGS]". This method
   * determines the action the builder is responsible for handling.
   * @return action string
   */
  protected abstract String action();

  public BenchmarkrCommandBuilder(String executableAbsolutePath) {
    // used linked list because there will be more writes than reads
    this.arguments = new LinkedList<>();

    if (!Files.exists(Paths.get(executableAbsolutePath))) {
      log.error("Target Benchmarkr executable path " + executableAbsolutePath + " does not exist");
      throw new IllegalArgumentException("Target Benchmarkr executable path " + executableAbsolutePath + " does not exist");
    }

    this.arguments.add(executableAbsolutePath);
    this.arguments.add(action());
  }

  /**
   * Add the argument to the list of args if there is a valid value present
   * @param flag the key
   * @param value the value
   * @return the builder instance
   */
  @SuppressWarnings("unchecked")
  protected T addArgument(String flag, String value) {
    if (value != null && !value.isBlank()) {
      log.trace("Adding argument " + flag + "=" + value);
      arguments.add(flag);
      arguments.add(value);
    } else {
      log.trace("Skipping flag " + flag);
    }
    return (T)this;
  }

  /**
   * Build the benchmarkr command
   * @return benchmarkr command
   */
  public BenchmarkrCommand build() {
    return new BenchmarkrCommand(arguments.toArray(new String[0]));
  }
}
