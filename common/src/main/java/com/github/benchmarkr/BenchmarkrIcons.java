package com.github.benchmarkr;

import javax.swing.*;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

public interface BenchmarkrIcons {
    // See https://plugins.jetbrains.com/docs/intellij/work-with-icons-and-images.html#svg-format
    //   for sizing requirements
    Icon BENCHMARKR_TOOL_WINDOW_ICON =
        IconLoader.getIcon("icons/benchmarkrToolWindowIcon.svg", BenchmarkrIcons.class);
    Icon BENCHMARKR_NODE_ICON =
        IconLoader.getIcon("icons/benchmarkrNodeIcon.svg", BenchmarkrIcons.class);
    Icon BENCHMARKR_EDITOR_GUTTER_ICON =
        IconLoader.getIcon("icons/benchmarkrEditorGutterIcon.svg", BenchmarkrIcons.class);


    // Icon used to denote a runnable benchmarking test
    Icon BenchmarkrRunGutterIcon = AllIcons.RunConfigurations.TestState.Run;

    Icon UploadIcon = AllIcons.Actions.Upload;
}
