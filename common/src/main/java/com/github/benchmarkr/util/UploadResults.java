package com.github.benchmarkr.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UploadResults {
  private static final Pattern RESULTS_ROW_PATTERN = Pattern.compile("Uploading(.*)(... DONE)");

  public static int resultsUploaded(String consoleOutput) {
    Matcher matcher = RESULTS_ROW_PATTERN.matcher(consoleOutput);

    int count = 0;
    while (matcher.find()) count ++;

    return count;
  }
}
