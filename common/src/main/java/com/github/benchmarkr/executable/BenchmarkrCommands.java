package com.github.benchmarkr.executable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class BenchmarkrCommands {
  public static String version(String benchmarkrExecutable) {
    try{
      InputStream outputStream = Runtime.getRuntime().exec(String.format("%s version",
          benchmarkrExecutable)).getInputStream();
      return new String(outputStream.readAllBytes(), StandardCharsets.UTF_8).strip();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  public static String testConnection(String benchmarkrExecutable, String username, String password) {
    try{
      InputStream outputStream = Runtime.getRuntime().exec(String.format("%s test-connection -u %s -p %s",
          benchmarkrExecutable, username, password)).getInputStream();
      return new String(outputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
