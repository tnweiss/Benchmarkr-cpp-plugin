package com.github.benchmarkr.util;

public enum Command {
  VERSION("version"),
  TEST_CONNECTION("test-connection"),
  UPLOAD("upload"),
  INIT("init");

  private final String action;

  Command(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }
}
