package com.github.benchmarkr.executable;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.benchmarkr.settings.input.TestConnectionContext;
import com.github.benchmarkr.util.Commands;
import com.google.common.collect.Streams;

public class BenchmarkrCommands {
  private static Pattern resultRowPattern = Pattern.compile("Uploading(.*)(... DONE)");

  public static String version(String benchmarkrExecutable) {
    try{
      // build the command
      String cmd = Commands.version(benchmarkrExecutable).build();

      // execute the command
      InputStream outputStream = Runtime.getRuntime()
          .exec(cmd)
          .getInputStream();

      // return the output
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
      String cmd = Commands.testConnection(benchmarkrExecutable)
          .username(username)
          .password(password)
          .elasticUrl(elasticOrigin)
          .kibanaUrl(kibanaOrigin)
          .debug(debug)
          .build();

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

  public static int uploadResults(String benchmarkrExecutable, String username, String password,
                                   String elasticOrigin) throws IllegalStateException {
    try {
      // construct the command
      String[] cmd = Commands.upload(benchmarkrExecutable)
          .username(username)
          .password(password)
          .elasticUrl(elasticOrigin)
          .buildArray();

      // run the process
      Process process = Runtime.getRuntime().exec(cmd);

      // if ran successfully, return output
      if (process.waitFor(15, TimeUnit.SECONDS) && process.exitValue() == 0) {
        Matcher matcher = resultRowPattern.matcher(new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8));

        int count = 0;
        while (matcher.find()) count ++;
        return count;
      }

      throw new IllegalStateException("Error uploading results, try running...\n" + String.join(" ", cmd));
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
