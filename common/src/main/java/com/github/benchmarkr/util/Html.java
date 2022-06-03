package com.github.benchmarkr.util;

public class Html {
  public static String consoleToHtml(String consoleOutput) {
    return
        "<html>"
          + consoleOutput
            .replaceAll("<","&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll("\n", "<br/>")
        + "</html>";
  }

  public static String redDebug(String htmlOutput) {
    return htmlOutput.replaceAll("\\[error]", "<font color='red'>[error]</font>");
  }
}
