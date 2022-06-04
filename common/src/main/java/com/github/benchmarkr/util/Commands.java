package com.github.benchmarkr.util;

import java.util.LinkedList;
import java.util.List;


public class Commands {
  private static final String USERNAME_FLAG = "-u";
  private static final String PASSWORD_FLAG = "-p";
  private static final String KIBANA_URL_FLAG = "-k";
  private static final String ELASTIC_URL_FLAG = "-e";
  private static final String LOG_LEVEL_FLAG = "-l";

  public static class BenchmarkrCmdBuilder {
    private final List<String> components;

    private BenchmarkrCmdBuilder add(String flag, String value) {
      if (value != null && !value.isBlank()) {
        components.add(flag);
        components.add(value);
      }
      return this;
    }

    public BenchmarkrCmdBuilder(Command command) {
      this("benchmarkr", command);
    }

    public BenchmarkrCmdBuilder(String executableAbsolutePath, Command command) {
      this.components = new LinkedList<>();
      this.components.add(executableAbsolutePath);
      this.components.add(command.getAction());
    }

    public BenchmarkrCmdBuilder username(String username) {
      return add(USERNAME_FLAG, username);
    }

    public BenchmarkrCmdBuilder password(String password) {
      return add(PASSWORD_FLAG, password);
    }

    public BenchmarkrCmdBuilder elasticUrl(String elasticUrl) {
      return add(ELASTIC_URL_FLAG, elasticUrl);
    }

    public BenchmarkrCmdBuilder kibanaUrl(String kibanaUrl) {
      return add(KIBANA_URL_FLAG, kibanaUrl);
    }

    public BenchmarkrCmdBuilder logLevel(LogLevel logLevel) {
      return add(LOG_LEVEL_FLAG, logLevel.toString());
    }

    public BenchmarkrCmdBuilder debug(boolean debug) {
      if (debug) {
        return logLevel(LogLevel.DEBUG);
      }
      return this;
    }

    public String build() {
      return String.join(" ", components);
    }

    public String[] buildArray() {
      return components.toArray(new String[0]);
    }
  }

  public static BenchmarkrCmdBuilder version(String executableAbsolutePath) {
    return new BenchmarkrCmdBuilder(executableAbsolutePath, Command.VERSION);
  }

  public static BenchmarkrCmdBuilder testConnection(String executableAbsolutePath) {
    return new BenchmarkrCmdBuilder(executableAbsolutePath, Command.TEST_CONNECTION);
  }

  public static BenchmarkrCmdBuilder init(String executableAbsolutePath) {
    return new BenchmarkrCmdBuilder(executableAbsolutePath, Command.INIT);
  }

  public static BenchmarkrCmdBuilder upload(String executableAbsolutePath) {
    return new BenchmarkrCmdBuilder(executableAbsolutePath, Command.UPLOAD);
  }
}
