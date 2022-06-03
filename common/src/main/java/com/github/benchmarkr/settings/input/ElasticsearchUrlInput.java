package com.github.benchmarkr.settings.input;

import javax.swing.*;

import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.util.ui.UI;

public class ElasticsearchUrlInput {
  private static final String LABEL = "Elasticsearch Url:";

  private final JTextField elasticsearchUrl;

  public ElasticsearchUrlInput(BenchmarkrSettingsState state) {
    elasticsearchUrl = new JTextField();

    elasticsearchUrl.setText(state.getElasticsearchUrl());
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(elasticsearchUrl)
        .withComment("Ex. " + BenchmarkrSettingsState.DEFAULT_ELASTICSEARCH_URL)
        .withLabel(LABEL);
  }

  public String getText() {
    return elasticsearchUrl.getText();
  }
}
