package core.api;

import api.FeatureGroup;
import api.SootFX;
import core.fx.FxUtil;
import core.fx.base.ClassFeatureExtractor;
import core.fx.base.ManifestFeatureExtractor;
import core.fx.base.MethodFeatureExtractor;
import core.fx.base.WholeProgramFeatureExtractor;
import core.fx.classbased.ClassMethodCount;
import core.fx.classbased.ClassNameContains;
import core.fx.manifestbased.ManifestActivityCount;
import core.fx.methodbased.MethodAssignStmtCount;
import core.fx.methodbased.MethodNameContains;
import core.fx.methodbased.MethodStmtCount;
import core.fx.wholeprogrambased.WholeProgramMethodCount;
import core.fx.wholeprogrambased.WholeProgramStmtCount;
import core.rm.ClassFeatureSet;
import core.rm.ManifestFeatureSet;
import core.rm.MethodFeatureSet;
import core.rm.WholeProgramFeatureSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class APITest {

    @Test
    public void testListFeatures(){
        SootFX sootFX = new SootFX();
        List<FeatureGroup> featureGroups = FxUtil.listAllFeatures();
        List<String> features = new ArrayList<>();
        for (FeatureGroup group : featureGroups) {
            if(group.getName().equals("methodbased")){
                features.add(group.getList().get(0).getName());
                features.add(group.getList().get(1).getName());
            }
        }
        Set<MethodFeatureSet> methodFeatureSets = sootFX.extractMethodFeatures(features);
        methodFeatureSets.forEach(System.out::println);
    }

    @Test
    public void testListAllMethodFeatures(){
        SootFX sootFX = new SootFX();
        sootFX.addClassPath("src/test/resources/").mainClass("sourcecode.pkg.A");
        Set<MethodFeatureSet> methodFeatureSets = sootFX.extractAllMethodFeatures();
        methodFeatureSets.forEach(System.out::println);
    }

    @Test
    public void testListAllClassFeatures(){
        SootFX sootFX = new SootFX();
        sootFX.addClassPath("src/test/resources/apks/cl.rkstro.scoutTraductores.apk");
        sootFX.appOnly();
        Set<ClassFeatureSet> featureSets = sootFX.extractAllClassFeatures();
        featureSets.forEach(System.out::println);
    }

    @Test
    public void testListAllWPFeatures(){
        SootFX sootFX = new SootFX();
        sootFX.addClassPath("src/test/resources/apks/cl.rkstro.scoutTraductores.apk");
        WholeProgramFeatureSet featureSet = sootFX.extractAllWholeProgramFeatures();
        System.out.println(featureSet);
    }

    @Test
    public void testListAllManifestFeatures(){
        SootFX sootFX = new SootFX();
        sootFX.addClassPath("/Users/kadiray/Workspace/drebin/drebin-0/1.apk");
        ManifestFeatureSet featureSet = sootFX.extractAllManifestFeatures();
        System.out.println(featureSet);
    }

    @Test
    public void testAPI(){
        SootFX sootFx = new SootFX();
        Set<MethodFeatureExtractor> setOfFeatures = new HashSet<>();
        setOfFeatures.add(new MethodStmtCount());
        setOfFeatures.add(new MethodAssignStmtCount());
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
        setOfFeatures.add(new MethodStmtCount());
        setOfFeatures.add(new MethodAssignStmtCount());
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
        setOfFeatures.add(new MethodStmtCount());
        setOfFeatures.add(new MethodAssignStmtCount());
        setOfFeatures.add(new MethodNameContains("main"));
        Set<MethodFeatureSet> methodFeatureSets = sootFx.addClassPath("src/test/resources/jars/jdbc-api-1.4.jar").extractMethodFeatures(setOfFeatures);
        System.out.println(methodFeatureSets.size());
        //methodFeatureSets.forEach(f-> System.out.println(f));
    }

}
