package com.github.benchmarkr.settings.input;

import com.github.benchmarkr.settings.BenchmarkrSettingsState;
import com.intellij.openapi.ui.panel.ComponentPanelBuilder;
import com.intellij.ui.components.fields.IntegerField;
import com.intellij.util.ui.UI;

public class UploadIntervalInput {
  private static final String LABEL = "Upload watch delay:";

  private static final int MIN = 10;
  private static final int MAX = 6000;
  private static final String comment = "Upload watch sleep time in seconds. " + MIN + " &lt value &lt " + MAX;
  private final IntegerField interval;

  public UploadIntervalInput(BenchmarkrSettingsState state) {
    interval = new IntegerField("uploadWatchDelay", 0, 1000);
    interval.setValue(state.getUploadInterval());
  }

  public ComponentPanelBuilder getComponentBuilder() {
    return UI.PanelFactory
        .panel(interval)
        .withComment(comment)
        .withLabel(LABEL);
  }

  public int getInt() {
    return interval.getValue();
  }
}
