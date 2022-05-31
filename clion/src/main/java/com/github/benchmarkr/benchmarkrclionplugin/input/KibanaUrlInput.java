package com.github.benchmarkr.benchmarkrclionplugin.input;

import javax.swing.*;

import com.github.benchmarkr.benchmarkrclionplugin.BenchmarkrSettingsState;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class KibanaUrlInput {
  private static final String LABEL = "Kibana Url:";

  private final JTextField kibanaUrl;

  public KibanaUrlInput(BenchmarkrSettingsState state) {
    kibanaUrl = new JTextField();

    kibanaUrl.setText(state.kibanaUrl);
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(kibanaUrl)
        .withComment("Ex. " + BenchmarkrSettingsState.DEFAULT_KIBANA_URL)
        .withLabel(LABEL);
  }

  public String getText() {
    return kibanaUrl.getText();
  }
}
