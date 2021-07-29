package core.fx;

import api.SootFX;
import core.fx.base.MethodFeatureExtractor;
import core.fx.methodbased.analysis.MethodAnalysisNullPointer;
import core.rm.MethodFeatureSet;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class MethodAnalysisNullPointerTest {

    @Test
    public void test(){
        SootFX sootFX = new SootFX();
        sootFX.addClassPath("src/test/resources/").mainClass("sourcecode.pkg.NullPointerClass").appOnly();
        Set<MethodFeatureExtractor> methodFeatureSets = new HashSet<>();
        methodFeatureSets.add(new MethodAnalysisNullPointer());
        Set<MethodFeatureSet> extractMethodFeatures = sootFX.extractMethodFeatures(methodFeatureSets);
        extractMethodFeatures.stream().filter(m->m.getMethod().getDeclaringClass().getName().contains("NullPointerClass")).forEach(System.out::println);

    }

}
