package com.github.benchmarkr.settings;

import com.github.benchmarkr.executable.BenchmarkrBinaryDiscovery;
import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.credentialStore.CredentialAttributesKt;
import com.intellij.credentialStore.Credentials;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.intellij.util.xmlb.annotations.Property;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = "com.github.benchmarkr.benchmarkrclionplugin.BenchmarkrSettingsState",
    storages = @Storage("BenchmarkrSettingsPlugin.xml")
)
public class BenchmarkrSettingsState implements PersistentStateComponent<BenchmarkrSettingsState> {
  private static final CredentialAttributes credentialAttributes =
      new CredentialAttributes(CredentialAttributesKt.generateServiceName("Benchmarkr", "elk"));

  public static final String DEFAULT_ELASTICSEARCH_URL = "http://localhost:9200";
  public static final String DEFAULT_KIBANA_URL = "http://localhost:5601";
  public static final String DEFAULT_USERNAME = "";
  public static final String DEFAULT_PASSWORD = "";

  public static final int DEFAULT_UPLOAD_INTERVAL = 60;


  @Property
  private String benchmarkrExecutablePath = BenchmarkrBinaryDiscovery.benchmarkr();

  @Property
  private String elasticsearchUrl = DEFAULT_ELASTICSEARCH_URL;

  @Property
  private String kibanaUrl = DEFAULT_KIBANA_URL;

  @Property
  private int uploadInterval = DEFAULT_UPLOAD_INTERVAL;

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

  public void setBenchmarkrExecutablePath(String benchmarkrExecutablePath) {
    this.benchmarkrExecutablePath = benchmarkrExecutablePath;
  }

  public void setElasticsearchUrl(String elasticsearchUrl) {
    this.elasticsearchUrl = elasticsearchUrl;
  }

  public void setKibanaUrl(String kibanaUrl) {
    this.kibanaUrl = kibanaUrl;
  }

  public String getBenchmarkrExecutablePath() {
    return benchmarkrExecutablePath;
  }

  public String getElasticsearchUrl() {
    return elasticsearchUrl;
  }

  public String getKibanaUrl() {
    return kibanaUrl;
  }

  public int getUploadInterval() {
    return uploadInterval;
  }

  public void setUploadInterval(int uploadInterval) {
    this.uploadInterval = uploadInterval;
  }

  public String getUsername() {
    Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);

    if (credentials != null) {
      return credentials.getUserName();
    }

    return DEFAULT_USERNAME;
  }

  public String getPassword() {
    Credentials credentials = PasswordSafe.getInstance().get(credentialAttributes);

    if (credentials != null) {
      return credentials.getPasswordAsString();
    }

    return DEFAULT_PASSWORD;
  }

  public void setCredentials(String username, String password) {
    PasswordSafe.getInstance().set(credentialAttributes, new Credentials(username, password));
  }

}
