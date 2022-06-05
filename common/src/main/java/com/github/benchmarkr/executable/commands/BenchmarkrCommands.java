package com.github.benchmarkr.executable.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.benchmarkr.executable.commands.builder.TestConnectionCommandBuilder;
import com.github.benchmarkr.executable.commands.builder.UploadCommandBuilder;
import com.github.benchmarkr.executable.commands.builder.VersionCommandBuilder;
import com.github.benchmarkr.settings.input.TestConnectionContext;
import com.github.benchmarkr.util.Html;
import com.github.benchmarkr.util.UploadResults;

public class BenchmarkrCommands {

  public static String version(TestConnectionContext testConnectionContext) {
    return version(testConnectionContext.getExecutableAbsolutePath());
  }

  public static String version(String benchmarkrExecutable) {
    try {
      return new VersionCommandBuilder(benchmarkrExecutable)
          .build()
          .execute()
          .output();
    } catch (BenchmarkrCommandExecutionException ex) {
      return ex.getMessage();
    }
  }

  public static String testConnection(TestConnectionContext testConnectionContext) {
    return testConnection(testConnectionContext.getExecutableAbsolutePath(), testConnectionContext.getUsername(),
        testConnectionContext.getPassword(), testConnectionContext.getElasticUrl(),
        testConnectionContext.getKibanaUrl());
  }

  public static String testConnection(String benchmarkrExecutable, String username, String password,
                                      String elasticOrigin, String kibanaOrigin) {
    try {
      return new TestConnectionCommandBuilder(benchmarkrExecutable)
          .username(username)
          .password(password)
          .elasticUrl(elasticOrigin)
          .kibanaUrl(kibanaOrigin)
          .build()
          .execute()
          .outputOrRerunWithDebug();
    } catch (BenchmarkrCommandExecutionException ex) {
      return ex.getMessage();
    }
  }

  public static int uploadResults(TestConnectionContext testConnectionContext)
      throws BenchmarkrCommandExecutionException{
    return uploadResults(testConnectionContext.getExecutableAbsolutePath(),
        testConnectionContext.getUsername(), testConnectionContext.getPassword(),
        testConnectionContext.getElasticUrl());
  }

  public static int uploadResults(String benchmarkrExecutable, String username, String password,
                                   String elasticOrigin) throws BenchmarkrCommandExecutionException {
    String output = new UploadCommandBuilder(benchmarkrExecutable)
        .username(username)
        .password(password)
        .elasticUrl(elasticOrigin)
        .build()
        .execute()
        .output();

    return UploadResults.resultsUploaded(output);
  }
}
