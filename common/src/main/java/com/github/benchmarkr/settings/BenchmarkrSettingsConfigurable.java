package com.github.benchmarkr.settings;

import javax.swing.*;

import java.util.Objects;

import com.intellij.openapi.options.Configurable;

import org.jetbrains.annotations.Nullable;

public class BenchmarkrSettingsConfigurable implements Configurable {
  private BenchmarkrSettingsComponent benchmarkrSettingsComponent;

  public static final String DISPLAY_NAME = "Benchmarkr";

  @Override
  public String getDisplayName() {
    return DISPLAY_NAME;
  }

  @Override
  public @Nullable JComponent createComponent() {
    benchmarkrSettingsComponent = new BenchmarkrSettingsComponent();
    return benchmarkrSettingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    BenchmarkrSettingsState settings = BenchmarkrSettingsState.getInstance();

    return settings == null || benchmarkrSettingsComponent == null
        || !Objects.equals(settings.getBenchmarkrExecutablePath(), benchmarkrSettingsComponent.getExecutableAbsolutePath())
        || !Objects.equals(settings.getElasticsearchUrl(), benchmarkrSettingsComponent.getElasticUrl())
        || !Objects.equals(settings.getKibanaUrl(), benchmarkrSettingsComponent.getKibanaUrl())
        || !Objects.equals(settings.getUsername(), benchmarkrSettingsComponent.getUsername())
        || !Objects.equals(settings.getPassword(), benchmarkrSettingsComponent.getPassword());
  }

  @Override
  public void apply() {
    BenchmarkrSettingsState settings = BenchmarkrSettingsState.getInstance();

    settings.setBenchmarkrExecutablePath(benchmarkrSettingsComponent.getExecutableAbsolutePath());

    settings.setElasticsearchUrl(benchmarkrSettingsComponent.getElasticUrl());
    settings.setKibanaUrl(benchmarkrSettingsComponent.getKibanaUrl());

    settings.setCredentials(benchmarkrSettingsComponent.getUsername(), benchmarkrSettingsComponent.getPassword());
  }
}
