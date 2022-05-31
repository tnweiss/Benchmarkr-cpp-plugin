package com.github.benchmarkr.benchmarkrclionplugin;

import com.github.benchmarkr.executable.BenchmarkrBinaryDiscovery;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = "com.github.benchmarkr.benchmarkrclionplugin.BenchmarkrSettingsState",
    storages = @Storage("BenchmarkrSettingsPlugin.xml")
)
public class BenchmarkrSettingsState implements PersistentStateComponent<BenchmarkrSettingsState> {
  public static final String DEFAULT_ELASTICSEARCH_URL = "http://localhost:9200";
  public static final String DEFAULT_KIBANA_URL = "http://localhost:5601";
  public static final String DEFAULT_USERNAME = "";
  public static final String DEFAULT_PASSWORD = "";

  public String benchmarkrExecutablePath = BenchmarkrBinaryDiscovery.benchmarkr();
  public String elasticsearchUrl = DEFAULT_ELASTICSEARCH_URL;
  public String kibanaUrl = DEFAULT_KIBANA_URL;
  public String username = DEFAULT_USERNAME;
  public String password = DEFAULT_PASSWORD;

  public static BenchmarkrSettingsState getInstance() {
    return ApplicationManager.getApplication().getService(BenchmarkrSettingsState.class);
  }

  @Nullable
  @Override
  public BenchmarkrSettingsState getState() {
    return this;
  }

  @Override
  public void noStateLoaded() {
    PersistentStateComponent.super.noStateLoaded();
  }

  @Override
  public void initializeComponent() {
    PersistentStateComponent.super.initializeComponent();
  }

  @Override
  public void loadState(@NotNull BenchmarkrSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

}
