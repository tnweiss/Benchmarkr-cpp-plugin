package com.github.benchmarkr.benchmarkrclionplugin;

import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfiguration;
import com.jetbrains.cidr.cpp.execution.CMakeAppRunConfigurationType;
import com.jetbrains.cidr.execution.BuildTargetAndConfigurationData;
import com.jetbrains.cidr.execution.BuildTargetData;
import com.jetbrains.cidr.execution.ExecutableData;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

public class BenchmarkrAnAction extends AnAction {
    private static final Logger logger = Logger.getInstance(BenchmarkrAnAction.class);

    private static final String TEST_PREFIX = "benchmarkr";
    private static final String RUN_CONFIGURATION_FOLDER_NAME = "benchmarkr";
    private static final String RUN_CONFIGURATION_NAME = "Debug";
    private static final String DESCRIPTION = "";

    private final String configurationName;
    private final String executableName;
    private final String testName;
    private final String configurationParameters;
    private final Project project;
    private final boolean debug;

    public BenchmarkrAnAction(@NotNull Project project, @NotNull String executableName,
                              @NotNull String testName, boolean debug) {
        super(getReprString(executableName, testName, debug), DESCRIPTION, getReprIcon(debug));

        this.debug = debug;
        this.testName = testName;
        this.project = project;
        this.executableName = executableName;
        this.configurationName = String.format("%s [%s]", TEST_PREFIX, testName);
        this.configurationParameters = String.format("--test %s --console Simple", testName);
    }

    private static Icon getReprIcon (boolean debug) {
        if (debug) {
            return AllIcons.Actions.StartDebugger;
        } else {
            return AllIcons.Actions.Execute;
        }
    }

    private static @NotNull String getReprString(@NotNull String executableName,
                                                 @NotNull String testName, boolean debug) {
        StringBuilder sb = new StringBuilder();

        if (!debug) {
            sb.append("Run      ");
        } else {
            sb.append("Debug ");
        }

        sb.append(executableName);
        sb.append(" [");
        sb.append(testName);
        sb.append("]");

        return sb.toString();
    }

    private Executor executor() {
        return debug? DefaultDebugExecutor.getDebugExecutorInstance(): DefaultRunExecutor.getRunExecutorInstance();
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        // log the action
        logger.info(String.format("Running %s[%s] in project %s", executableName,
                testName, project.getName()));

        // get the run manager to get access to run configuration settings
        RunManager rm = RunManager.getInstance(Objects.requireNonNull(project));

        // find the configuration by name, if it does not exist a null will be returned
        RunnerAndConfigurationSettings runnerAndConfigurationSettings = rm.findConfigurationByName(configurationName);

        // if the configuration is not found create it
        if (runnerAndConfigurationSettings == null) {
            logger.debug(String.format("Configuration with name \"%s\" not found. Creating.", configurationName));

            // create the settings and get the run configuration
            runnerAndConfigurationSettings =
                    rm.createConfiguration(configurationName, CMakeAppRunConfigurationType.class);
            CMakeAppRunConfiguration bmRunConfiguration =
                    (CMakeAppRunConfiguration) runnerAndConfigurationSettings.getConfiguration();

            // put all benchmarkr configurations under a folder
            runnerAndConfigurationSettings.setFolderName(RUN_CONFIGURATION_FOLDER_NAME);

            // set the parameters to run a single test with the given name
            bmRunConfiguration.setProgramParameters(configurationParameters);

            // add the new configuration
            rm.addConfiguration(runnerAndConfigurationSettings);

            // create the build target data
            BuildTargetData btd = new BuildTargetData(project.getName(), executableName);
            ExecutableData ed = new ExecutableData(btd);
            BuildTargetAndConfigurationData btacd = new BuildTargetAndConfigurationData(btd, RUN_CONFIGURATION_NAME);

            // set all build configuration data (mainly target and executable fields)
            ((CMakeAppRunConfiguration)runnerAndConfigurationSettings.getConfiguration()).
                    setTargetAndConfigurationData(btacd);
            ((CMakeAppRunConfiguration)runnerAndConfigurationSettings.getConfiguration()).
                    setExecutableData(ed);
        }

        // set this actions settings as the active configuration
        rm.setSelectedConfiguration(runnerAndConfigurationSettings);

        // run the configuration
        ProgramRunnerUtil.executeConfiguration(runnerAndConfigurationSettings, executor());
    }
}
