package com.github.benchmarkr.executable;

import java.io.File;
import java.util.Optional;

public class BenchmarkrBinaryDiscovery {
  private static final String OS_PROPERTY = "os.name";
  private static final String PATH_ENV = "PATH";
  private static final String OS_PROPERTY_WINDOWS_IDENTIFIER = "Windows";

  private static final String WINDOWS_BENCHMARKR_EXE = "benchmarkr.exe";
  private static final String[] WINDOWS_SEARCH_PATHS = {
      "C:\\Program Files (x86)",
      "C:\\Program Files"
  };

  private static final String LINUX_BENCHMARKR_EXE = "benchmarkr";
  private static final String[] LINUX_SEARCH_PATHS = {
      "/bin",
      "/usr/bin"
  };

  private static Optional<String> findExecutableOnPath(String name) {
    for (String dirname : System.getenv(PATH_ENV).split(File.pathSeparator)) {
      File file = new File(dirname, name);
      if (file.isFile() && file.canExecute()) {
        return Optional.of(file.getAbsolutePath());
      }
    }
    return Optional.empty();
  }

  private static boolean isWindows() {
    return System.getProperty(OS_PROPERTY)
        .contains(OS_PROPERTY_WINDOWS_IDENTIFIER);
  }

  public static String benchmarkr() {
    String benchmarkrExe = isWindows() ? WINDOWS_BENCHMARKR_EXE : LINUX_BENCHMARKR_EXE;
    String[] searchDirs = isWindows() ? WINDOWS_SEARCH_PATHS : LINUX_SEARCH_PATHS;

    return findExecutableOnPath(isWindows()? WINDOWS_BENCHMARKR_EXE : LINUX_BENCHMARKR_EXE)
        .orElse(BenchmarkrFileVisitor.discover(searchDirs, benchmarkrExe)
            .orElse(""));
  }
}
