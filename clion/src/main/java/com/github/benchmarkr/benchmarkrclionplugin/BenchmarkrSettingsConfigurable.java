package com.github.benchmarkr.benchmarkrclionplugin;

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
        || !Objects.equals(settings.benchmarkrExecutablePath, benchmarkrSettingsComponent.getBenchmarkrExecutable())
        || !Objects.equals(settings.elasticsearchUrl, benchmarkrSettingsComponent.getElasticUrl())
        || !Objects.equals(settings.kibanaUrl, benchmarkrSettingsComponent.getKibanaUrl())
        || !Objects.equals(settings.username, benchmarkrSettingsComponent.getElasticUsername())
        || !Objects.equals(settings.password, benchmarkrSettingsComponent.getElasticPassword());
  }

  @Override
  public void apply() {
    BenchmarkrSettingsState settings = BenchmarkrSettingsState.getInstance();

    settings.benchmarkrExecutablePath = benchmarkrSettingsComponent.getBenchmarkrExecutable();

    settings.elasticsearchUrl = benchmarkrSettingsComponent.getElasticUrl();
    settings.kibanaUrl = benchmarkrSettingsComponent.getKibanaUrl();

    settings.username = benchmarkrSettingsComponent.getElasticUsername();
    settings.password = benchmarkrSettingsComponent.getElasticPassword();
  }
}
