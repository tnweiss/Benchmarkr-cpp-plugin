package com.github.benchmarkr.settings.input;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

import com.github.benchmarkr.executable.BenchmarkrCommands;
import com.github.benchmarkr.util.Html;
import com.intellij.openapi.ui.DescriptionLabel;
import com.intellij.ui.IdeBorderFactory;

public class TestConnectionButton {
  private final JButton button;
  private final JProgressBar progressBar;
  private final DescriptionLabel descriptionLabel;
  private final TestConnectionContext testConnectionContext;

  private SwingWorker<Void, Void> process;

  public TestConnectionButton(TestConnectionContext testConnectionContext) {
    // create Test connection button
    button = new JButton("Test Connection");
    button.addActionListener(this::update);

    // create description box to hold console output
    descriptionLabel = new DescriptionLabel("");

    // create loading bar
    progressBar = new JProgressBar();
    progressBar.setIndeterminate(true);
    progressBar.setVisible(false);

    this.testConnectionContext = testConnectionContext;
  }

  private void update(ActionEvent actionEvent) {
    if (process != null) {
      cancelLoading();
      return;
    }

    process = new SwingWorker<>() {
      @Override
      protected Void doInBackground() {
        // run the command to test connection
        String consoleOutput = BenchmarkrCommands.testConnection(testConnectionContext);

        // convert output to html
        String htmlOutput = Html.redDebug(Html.consoleToHtml(consoleOutput));

        // set the description component
        descriptionLabel.setText(htmlOutput);
        //descriptionLabel.updateUI();

        // cancel the loading status
        cancelLoading();

        return null;
      }
    };

    // let the user know the background process is running
    setLoading();

    // execute the background task
    process.execute();
  }

  private void setLoading() {
    // update button text
    button.setText("Cancel");

    // show the progress bar
    progressBar.setVisible(true);

    // reset the description text
    descriptionLabel.setText("");
  }

  private void cancelLoading() {
    // if the current process is running, cancel it and send it to garbage collection
    if (process != null) {
      process.cancel(true);
      process = null;
    }

    // reset the button text
    button.setText("Test Connection");

    // hide the progress bar
    progressBar.setVisible(false);
  }

  public JPanel createPanel() {
    JPanel jPanel = new JPanel();
    jPanel.setLayout(new BorderLayout());

    jPanel.add(button, BorderLayout.PAGE_START);
    jPanel.add(progressBar, BorderLayout.PAGE_END);
    jPanel.add(descriptionLabel, BorderLayout.CENTER);

    jPanel.setBorder(IdeBorderFactory.createTitledBorder("Test Connection"));

    return jPanel;
  }
}
