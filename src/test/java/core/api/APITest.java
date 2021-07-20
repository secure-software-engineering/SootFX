package core.api;

import api.SootFX;
import core.fx.base.ClassFeatureExtractor;
import core.fx.base.MethodFeatureExtractor;
import core.fx.classbased.ClassNameContains;
import core.fx.methodbased.AssignStmtCount;
import core.fx.methodbased.MethodNameContains;
import core.fx.methodbased.StmtCount;
import core.rm.ClassFeature;
import core.rm.MethodFeature;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class APITest {

    @Test
    public void testAPI(){
        SootFX sootFx = new SootFX();
        Set<MethodFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new StmtCount());
        setOfFeatures.add(new AssignStmtCount());
        setOfFeatures.add(new MethodNameContains("main"));
        sootFx.addClassPath("src/test/resources/").mainClass("sourcecode.pkg.Test1");
        Set<MethodFeature> methodFeatures = sootFx.extractMethodFeatures(setOfFeatures);
        Set<ClassFeatureExtractor> setOfClassFX = new HashSet<>();
        setOfClassFX.add(new ClassNameContains("main"));
        Set<ClassFeature> classFeatures = sootFx.extractClassFeatures(setOfClassFX);
        classFeatures.forEach(f-> System.out.println(f));
        //methodFeatures.forEach(f-> System.out.println(f));
    }

    @Test
    public void testApacheCommons(){
        SootFX sootFx = new SootFX();
        Set<MethodFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new StmtCount());
        setOfFeatures.add(new AssignStmtCount());
        setOfFeatures.add(new MethodNameContains("main"));
        Set<MethodFeature> methodFeatures = sootFx.addClassPath("src/test/resources/jars/jdbc-api-1.4.jar").extractMethodFeatures(setOfFeatures);
        System.out.println(methodFeatures.size());
        //methodFeatures.forEach(f-> System.out.println(f));
    }

}
