package com.github.benchmarkr.settings;

import javax.swing.*;

import com.github.benchmarkr.settings.input.BenchmarkrExecutableInput;
import com.github.benchmarkr.settings.input.ElasticsearchUrlInput;
import com.github.benchmarkr.settings.input.KibanaUrlInput;
import com.github.benchmarkr.settings.input.PasswordInput;
import com.github.benchmarkr.settings.input.TestConnectionButton;
import com.github.benchmarkr.settings.input.TestConnectionContext;
import com.github.benchmarkr.settings.input.UsernameInput;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.util.ui.UI;

public class BenchmarkrSettingsComponent implements TestConnectionContext {

  private final BenchmarkrExecutableInput benchmarkrExecutableInput;
  private final UsernameInput usernameInput;
  private final PasswordInput passwordInput;
  private final ElasticsearchUrlInput elasticsearchUrlInput;
  private final KibanaUrlInput kibanaUrlInput;
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

    // create the main panel
    mainPanel = UI.PanelFactory.grid().splitColumns()
        .add(UI.PanelFactory.panel(benchmarkrPanel))
        .add(UI.PanelFactory.panel(elkPanel))
        .add(UI.PanelFactory.panel(new TestConnectionButton(this).createPanel()))
        .createPanel();


  }
  public JPanel getPanel() {
    return mainPanel;
  }

  public String getExecutableAbsolutePath() {
    return benchmarkrExecutableInput.getText();
  }

  public String getUsername() {
    return usernameInput.getText();
  }

  public String getPassword() {
    return passwordInput.getText();
  }

  public String getElasticUrl() {
    return elasticsearchUrlInput.getText();
  }

  public String getKibanaUrl() {
    return kibanaUrlInput.getText();
  }
}
