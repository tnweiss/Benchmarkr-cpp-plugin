package com.github.benchmarkr.executable;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BenchmarkrFileVisitor extends SimpleFileVisitor<Path> {
  private final List<String> benchmarkrPaths;
  private final String executableName;

  public BenchmarkrFileVisitor(String executableName) {
    this.benchmarkrPaths = new LinkedList<>();
    this.executableName = executableName;
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
      throws IOException {
    if (file.getFileName().toString().equals(executableName)) {
      benchmarkrPaths.add(file.toAbsolutePath().toString());
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException e)
      throws IOException {
    return FileVisitResult.SKIP_SUBTREE;
  }

  @Override
  public FileVisitResult preVisitDirectory(Path dir,
                                           BasicFileAttributes attrs)
      throws IOException {
    return FileVisitResult.CONTINUE;
  }

  public Optional<String> getPath() {
    return benchmarkrPaths.isEmpty() ? Optional.empty() : Optional.of(benchmarkrPaths.get(0));
  }

  public List<String> getPaths() {
    return benchmarkrPaths;
  }

  public static List<String> discover(String searchDir, String executableName) {
    try {
      BenchmarkrFileVisitor visitor = new BenchmarkrFileVisitor(executableName);
      Files.walkFileTree(Paths.get(searchDir), visitor);
      return visitor.getPaths();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }

  public static Optional<String> discover(String[] searchDirs, String executableName) {
    return Arrays.stream(searchDirs)
        .flatMap(s -> BenchmarkrFileVisitor.discover(s, executableName).stream())
        .findFirst();
  }
}
