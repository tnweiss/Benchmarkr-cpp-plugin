package com.github.benchmarkr.settings.input;

import javax.swing.*;

import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class UsernameInput {
  private static final String LABEL = "Username:";

  private final JTextField username;

  public UsernameInput(BenchmarkrSettingsState state) {
    username = new JTextField();

    username.setText(state.username);
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(username)
        .withLabel(LABEL);
  }

  public String getText() {
    return username.getText();
  }
}
