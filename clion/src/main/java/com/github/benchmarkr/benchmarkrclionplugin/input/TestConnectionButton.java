package com.github.benchmarkr.benchmarkrclionplugin.input;

import javax.swing.*;

import com.github.benchmarkr.executable.BenchmarkrCommands;
import com.intellij.openapi.ui.DescriptionLabel;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class TestConnectionButton {
  private final JButton button;

  public TestConnectionButton(BenchmarkrExecutableInput benchmarkrExecutableInput, DescriptionLabel descriptionLabel,
                              UsernameInput usernameInput, PasswordInput passwordInput) {
    button = new JButton("Test Connection");
    button.addActionListener(a -> descriptionLabel.setText(BenchmarkrCommands.testConnection(benchmarkrExecutableInput.getText(),
        usernameInput.getText(), passwordInput.getText())));
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(button);
  }
}
