package com.github.benchmarkr.benchmarkrclionplugin;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl;
import com.intellij.util.Function;
import com.jetbrains.cidr.lang.psi.impl.OCFileImpl;
import com.jetbrains.cidr.lang.psi.impl.OCMacroReferenceElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BenchmarkrRunLineMarkerContributor extends RunLineMarkerContributor {
    private static final Logger logger = Logger.getInstance(BenchmarkrRunLineMarkerContributor.class);
    private static final String BENCHMARK_MACRO_IDENTIFIER = "BENCHMARK";

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {
        if (element.textMatches(BENCHMARK_MACRO_IDENTIFIER) &&
                element.getParent() instanceof OCMacroReferenceElementImpl) {
            logger.info("Found Benchmarkr macro " + element);

            // Get the parent && move past the next sibling which is '('
            PsiElement argument1 = element.getParent().getNextSibling().getNextSibling();
            while (argument1 instanceof PsiWhiteSpaceImpl) {
                argument1 = argument1.getNextSibling();
            }

            // get the first arguments text to use as a hint / for the action
            String testName = argument1.getText();

            // get the executable name
            String executableName = ((OCFileImpl) element.getContainingFile()).getMainClassName();

            // the hint should print "Run Benchmark Test <EXE>:<TEST>"
            final Function<PsiElement, String> RUN_BENCHMARK_TEST_TOOLTIP_PROVIDER = it ->
                    "Run Benchmark Test" + executableName + " [" + testName + "]";

            // add both a run and debug option
            return new Info(BenchmarkrIcons.BenchmarkrRunGutterIcon,
                    RUN_BENCHMARK_TEST_TOOLTIP_PROVIDER,
                    new BenchmarkrAnAction(element.getProject(), executableName, testName, false),
                    new BenchmarkrAnAction(element.getProject(), executableName, testName, true));
        }

        return null;
    }
}
