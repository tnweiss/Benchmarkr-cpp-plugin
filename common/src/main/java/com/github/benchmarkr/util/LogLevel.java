package com.github.benchmarkr.util;

import java.util.Locale;

public enum LogLevel {
  INFO,
  DEBUG;

  @Override
  public String toString() {
    return this.name().toLowerCase(Locale.ROOT);
  }
}
