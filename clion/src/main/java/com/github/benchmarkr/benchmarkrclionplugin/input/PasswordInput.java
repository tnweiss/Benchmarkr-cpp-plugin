package com.github.benchmarkr.benchmarkrclionplugin.input;

import javax.swing.*;

import com.github.benchmarkr.benchmarkrclionplugin.BenchmarkrSettingsState;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class PasswordInput {
  private static final String LABEL = "Password:";

  private final JPasswordField password;

  public PasswordInput(BenchmarkrSettingsState state) {
    password = new JPasswordField();

    password.setText(state.password);
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(password)
        .withLabel(LABEL);
  }

  public String getText() {
    return password.getText();
  }
}
