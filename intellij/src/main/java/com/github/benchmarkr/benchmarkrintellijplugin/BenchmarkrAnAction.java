package com.github.benchmarkr.benchmarkrintellijplugin;

import javax.swing.*;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import com.intellij.execution.Executor;
import com.intellij.execution.ProgramRunnerUtil;
import com.intellij.execution.RunManager;
import com.intellij.execution.RunnerAndConfigurationSettings;
import com.intellij.execution.application.ApplicationConfiguration;
import com.intellij.execution.application.ApplicationConfigurationType;
import com.intellij.execution.executors.DefaultDebugExecutor;
import com.intellij.execution.executors.DefaultRunExecutor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;

public class BenchmarkrAnAction extends AnAction {
    private static final String TEST_PREFIX = "benchmarkr";
    private static final String RUN_CONFIGURATION_FOLDER_NAME = "benchmarkr";
    private static final String DESCRIPTION = "";
    private static final String BENCHMARKR_MAIN_CLASS = "com.github.benchmarkr.BenchmarkrCore";

    private final String configurationName;
    private final String configurationParameters;
    private final Project project;

    private final boolean debug;
    private final Module module;
    private final String classpath;

    public BenchmarkrAnAction(@NotNull PsiMethod method, boolean debug) {
        super(getReprString(method.getName(), debug), DESCRIPTION, getReprIcon(debug));

        this.debug = debug;
        this.project = method.getProject();
        this.module = ModuleUtil.findModuleForPsiElement(method);
        assert module != null;
        this.classpath = Arrays.stream(ModuleRootManager.getInstance(module)
            .orderEntries()
            .classes()
            .getRoots())
            .map(VirtualFile::getPath)
            .collect(Collectors.joining(";"))
            .replaceAll("!/;", ";");

        String packageName = ((PsiJavaFile)method.getParent().getParent()).getPackageName();

        this.configurationName = String.format("%s [%s]", TEST_PREFIX, method.getName());
        this.configurationParameters = String.format("-p %s -b %s",
            packageName, method.getName());
    }

    private static Icon getReprIcon (boolean debug) {
        if (debug) {
            return AllIcons.Actions.StartDebugger;
        } else {
            return AllIcons.Actions.Execute;
        }
    }

    private static @NotNull String getReprString(@NotNull String testName, boolean debug) {
        StringBuilder sb = new StringBuilder();

        if (!debug) {
            sb.append("Run      ");
        } else {
            sb.append("Debug ");
        }

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
        // get the run manager to get access to run configuration settings
        RunManager rm = RunManager.getInstance(Objects.requireNonNull(this.project));

        // find the configuration by name, if it does not exist a null will be returned
        RunnerAndConfigurationSettings runnerAndConfigurationSettings = rm.findConfigurationByName(this.configurationName);

        if (runnerAndConfigurationSettings == null) {
            // create the settings and get the run configuration
            runnerAndConfigurationSettings =
                rm.createConfiguration(configurationName, ApplicationConfigurationType.class);
            ApplicationConfiguration bmRunConfiguration =
                (ApplicationConfiguration) runnerAndConfigurationSettings.getConfiguration();

            // put all benchmarkr configurations under a folder
            runnerAndConfigurationSettings.setFolderName(RUN_CONFIGURATION_FOLDER_NAME);

            // set the main class to BenchmarkrCore
            bmRunConfiguration.setMainClassName(BENCHMARKR_MAIN_CLASS);

            // set parameters that target the specific method
            bmRunConfiguration.setProgramParameters(this.configurationParameters);

            // set the current module
            bmRunConfiguration.setModule(module);

            bmRunConfiguration.setPassParentEnvs(true);
            // set the classpath of the jvm
            // bmRunConfiguration.setVMParameters("-cp $Classpath$");
            bmRunConfiguration.setVMParameters("-cp \"" + classpath + "\"");

            // add the new configuration
            rm.addConfiguration(runnerAndConfigurationSettings);
        }

        // set this actions settings as the active configuration
        rm.setSelectedConfiguration(runnerAndConfigurationSettings);

        // run the configuration
        ProgramRunnerUtil.executeConfiguration(runnerAndConfigurationSettings, executor());
    }
}
