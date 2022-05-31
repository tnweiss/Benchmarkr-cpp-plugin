package com.github.benchmarkr.benchmarkrclionplugin;

import javax.swing.*;

import com.github.benchmarkr.benchmarkrclionplugin.input.BenchmarkrExecutableInput;
import com.github.benchmarkr.benchmarkrclionplugin.input.ElasticsearchUrlInput;
import com.github.benchmarkr.benchmarkrclionplugin.input.KibanaUrlInput;
import com.github.benchmarkr.benchmarkrclionplugin.input.PasswordInput;
import com.github.benchmarkr.benchmarkrclionplugin.input.TestConnectionButton;
import com.github.benchmarkr.benchmarkrclionplugin.input.UsernameInput;
import com.intellij.openapi.ui.DescriptionLabel;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.ui.UI;

public class BenchmarkrSettingsComponent {

  private final BenchmarkrExecutableInput benchmarkrExecutableInput;
  private final UsernameInput usernameInput;
  private final PasswordInput passwordInput;
  private final ElasticsearchUrlInput elasticsearchUrlInput;
  private final KibanaUrlInput kibanaUrlInput;
  private final TestConnectionButton testConnectionButton;
  private final JPanel mainPanel;

  public BenchmarkrSettingsComponent() {
    // get the current state
    BenchmarkrSettingsState state = BenchmarkrSettingsState.getInstance();

    // init inputs
    benchmarkrExecutableInput = new BenchmarkrExecutableInput(state);
    usernameInput = new UsernameInput(state);
    passwordInput = new PasswordInput(state);
    elasticsearchUrlInput = new ElasticsearchUrlInput(state);
    kibanaUrlInput = new KibanaUrlInput(state);
    DescriptionLabel descriptionLabel = new DescriptionLabel("");
    testConnectionButton = new TestConnectionButton(benchmarkrExecutableInput, descriptionLabel, usernameInput, passwordInput);

    // create executable configuration panel
    JPanel benchmarkrPanel = UI.PanelFactory.grid().splitColumns()
        .add(benchmarkrExecutableInput.getComponentBuilder())
        .createPanel();
    benchmarkrPanel.setBorder(IdeBorderFactory.createTitledBorder("Executable Configuration"));

    // create remote config panel
    JPanel elkPanel = UI.PanelFactory.grid().splitColumns()
        .add(elasticsearchUrlInput.getComponentBuilder())
        .add(kibanaUrlInput.getComponentBuilder())
        .add(usernameInput.getComponentBuilder())
        .add(passwordInput.getComponentBuilder())
        .createPanel();
    elkPanel.setBorder(IdeBorderFactory.createTitledBorder("ELK Configuration"));

    // create remote config panel
    JPanel testConnection = UI.PanelFactory.grid().splitColumns()
        .add(testConnectionButton.getComponentBuilder())
        .add(UI.PanelFactory.panel(descriptionLabel))
        .createPanel();
    testConnection.setBorder(IdeBorderFactory.createTitledBorder("Test Connection"));

    // create the main panel
    mainPanel = UI.PanelFactory.grid().splitColumns()
        .add(UI.PanelFactory.panel(benchmarkrPanel))
        .add(UI.PanelFactory.panel(elkPanel))
        .add(UI.PanelFactory.panel(testConnection))
        .createPanel();
  }
  public JPanel getPanel() {
    return mainPanel;
  }

  public String getBenchmarkrExecutable() {
    return benchmarkrExecutableInput.getText();
  }

  public String getElasticUsername() {
    return usernameInput.getText();
  }

  public String getElasticPassword() {
    return passwordInput.getText();
  }

  public String getElasticUrl() {
    return elasticsearchUrlInput.getText();
  }

  public String getKibanaUrl() {
    return kibanaUrlInput.getText();
  }
}
