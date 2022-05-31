package com.github.benchmarkr.benchmarkrclionplugin;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface BenchmarkrIcons {
    Icon BenchmarkrGutterIcon = IconLoader.getIcon("icons/pluginIcon.svg", BenchmarkrIcons.class);
    Icon BenchmarkrRunGutterIcon = AllIcons.RunConfigurations.TestState.Run;

    Icon UploadIcon = AllIcons.Actions.Upload;
}
