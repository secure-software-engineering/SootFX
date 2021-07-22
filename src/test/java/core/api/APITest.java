package core.api;

import api.SootFX;
import core.fx.base.ClassFeatureExtractor;
import core.fx.base.ManifestFeatureExtractor;
import core.fx.base.MethodFeatureExtractor;
import core.fx.base.WholeProgramFeatureExtractor;
import core.fx.classbased.ClassMethodCount;
import core.fx.classbased.ClassNameContains;
import core.fx.manifestbased.ManifestActivityCount;
import core.fx.methodbased.AssignStmtCount;
import core.fx.methodbased.MethodNameContains;
import core.fx.methodbased.StmtCount;
import core.fx.wholeprogram.WholeProgramMethodCount;
import core.fx.wholeprogram.WholeProgramStmtCount;
import core.rm.ClassFeatureSet;
import core.rm.ManifestFeatureSet;
import core.rm.MethodFeatureSet;
import core.rm.WholeProgramFeatureSet;
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
        Set<MethodFeatureSet> methodFeatureSets = sootFx.extractMethodFeatures(setOfFeatures);
        Set<ClassFeatureExtractor> setOfClassFX = new HashSet<>();
        setOfClassFX.add(new ClassNameContains("main"));
        setOfClassFX.add(new ClassMethodCount());
        Set<ClassFeatureSet> classFeatures = sootFx.extractClassFeatures(setOfClassFX);
        classFeatures.forEach(f-> System.out.println(f));
        Set<WholeProgramFeatureExtractor> setOfWPFX = new HashSet<>();
        setOfWPFX.add(new WholeProgramMethodCount());
        setOfWPFX.add(new WholeProgramStmtCount());
        WholeProgramFeatureSet wholeProgramFeature = sootFx.extractWholeProgramFeatures(setOfWPFX);
        System.out.println(wholeProgramFeature);
        //methodFeatureSets.forEach(f-> System.out.println(f));
    }

    @Test
    public void testApk(){
        SootFX sootFx = new SootFX();
        Set<MethodFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new StmtCount());
        setOfFeatures.add(new AssignStmtCount());
        setOfFeatures.add(new MethodNameContains("main"));
        Set<MethodFeatureSet> methodFeatureSets = sootFx.addClassPath("src/test/resources/apks/FieldSensitivity1.apk").extractMethodFeatures(setOfFeatures);
        System.out.println(methodFeatureSets.size());
    }

    @Test
    public void testManifest(){
        SootFX sootFx = new SootFX();
        Set<ManifestFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new ManifestActivityCount());
        ManifestFeatureSet featureSet = sootFx.addClassPath("src/test/resources/apks/FieldSensitivity1.apk").extractManifestFeatures(setOfFeatures);
        System.out.println(featureSet);
    }

    @Test
    public void testApacheCommons(){
        SootFX sootFx = new SootFX();
        Set<MethodFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new StmtCount());
        setOfFeatures.add(new AssignStmtCount());
        setOfFeatures.add(new MethodNameContains("main"));
        Set<MethodFeatureSet> methodFeatureSets = sootFx.addClassPath("src/test/resources/jars/jdbc-api-1.4.jar").extractMethodFeatures(setOfFeatures);
        System.out.println(methodFeatureSets.size());
        //methodFeatureSets.forEach(f-> System.out.println(f));
    }

}
