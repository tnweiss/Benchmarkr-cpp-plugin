package com.github.benchmarkr.executable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import com.github.benchmarkr.settings.input.TestConnectionContext;

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

  public static String testConnection(TestConnectionContext testConnectionContext) {
    return testConnection(testConnectionContext.getExecutableAbsolutePath(), testConnectionContext.getUsername(),
        testConnectionContext.getPassword(), testConnectionContext.getElasticUrl(),
        testConnectionContext.getKibanaUrl());
  }

  public static String testConnection(String benchmarkrExecutable, String username, String password,
                                      String elasticOrigin, String kibanaOrigin) {
    return testConnection(benchmarkrExecutable, username, password, elasticOrigin, kibanaOrigin, false);
  }

  public static String testConnection(String benchmarkrExecutable, String username, String password,
                                      String elasticOrigin, String kibanaOrigin, boolean debug) {
    try{
      // construct the command
      String cmd = String.format("\"%s\" test-connection -u %s -p %s -e %s -k %s",
          benchmarkrExecutable, username, password, elasticOrigin, kibanaOrigin);

      // add the debug flag if present
      cmd += debug ? " -l debug ": "";

      // run the process
      Process process = Runtime.getRuntime().exec(cmd);

      if (process.waitFor(15, TimeUnit.SECONDS)) {
        // if ran successfully, return output
        if (process.exitValue() == 0 || debug) {
          return new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        }
        return testConnection(benchmarkrExecutable, username, password, elasticOrigin, kibanaOrigin, true);
      } else {
        return "ERROR: Test Connection action resulted in a timeout.";
      }
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
