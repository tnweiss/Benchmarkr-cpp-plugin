package com.github.benchmarkr.benchmarkrclionplugin.input;

import javax.swing.*;

import com.github.benchmarkr.benchmarkrclionplugin.BenchmarkrSettingsState;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class BenchmarkrExecutableInput {
  private static final String TITLE = "Benchmarkr Binary";
  private static final String DESCRIPTION = "";
  private static final String LABEL = "Benchmarkr executable:";

  private final TextFieldWithBrowseButton benchmarkrExecutable;

  public BenchmarkrExecutableInput(BenchmarkrSettingsState state) {
    benchmarkrExecutable = new TextFieldWithBrowseButton();

    benchmarkrExecutable.addBrowseFolderListener(TITLE, DESCRIPTION, null,
        FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor());

    benchmarkrExecutable.setText(state.benchmarkrExecutablePath);
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(benchmarkrExecutable)
        .withLabel(LABEL);
  }

  public String getText() {
    return benchmarkrExecutable.getText();
  }
}
