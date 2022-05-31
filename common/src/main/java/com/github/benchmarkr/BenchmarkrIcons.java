package com.github.benchmarkr;

import javax.swing.*;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

public interface BenchmarkrIcons {
    Icon BenchmarkrGutterIcon = IconLoader.getIcon("icons/pluginIcon.svg", BenchmarkrIcons.class);
    Icon BenchmarkrRunGutterIcon = AllIcons.RunConfigurations.TestState.Run;

    Icon UploadIcon = AllIcons.Actions.Upload;
}
