package com.github.benchmarkr.benchmarkrintellijplugin;

import com.github.benchmarkr.BenchmarkrIcons;
import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.util.Function;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BenchmarkrRunLineMarkerContributor extends RunLineMarkerContributor {
    private static final String BENCHMARK_ANNOTATION = "com.github.benchmarkr.annotation.Benchmark";

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement element) {
        // only traverse identifiers
        if (!(element instanceof PsiIdentifier)) return null;

        // ensure identifier is for a method
        if (!(element.getParent() instanceof PsiMethod)) return null;

        // ensure method has an annotation
        if (!((PsiMethod)element.getParent()).getModifierList()
            .hasAnnotation(BENCHMARK_ANNOTATION)) return null;

        PsiMethod method = (PsiMethod) element.getParent();

        // the hint should print "Run Benchmark Test <EXE>:<TEST>"
        final Function<PsiElement, String> RUN_BENCHMARK_TEST_TOOLTIP_PROVIDER = it ->
            "Run Benchmark Test [" + method.getName() + "]";

        // add both a run and debug option
        return new Info(BenchmarkrIcons.BenchmarkrRunGutterIcon,
            RUN_BENCHMARK_TEST_TOOLTIP_PROVIDER,
            new BenchmarkrAnAction(method, false),
            new BenchmarkrAnAction(method, true));
    }
}
