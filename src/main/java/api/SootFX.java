package api;

import manager.ClassFX;
import manager.ManifestFX;
import manager.MethodFX;
import manager.WholeProgramFX;
import resource.SootConnector;
import core.fx.base.*;
import core.rm.ClassFeatureSet;
import core.rm.ManifestFeatureSet;
import core.rm.MethodFeatureSet;
import core.rm.WholeProgramFeatureSet;

import java.util.*;

public class SootFX {

    private String mainClass;
    private List<String> classPaths;

    public SootFX addClassPath(String classPath) {
        if (this.classPaths == null) {
            this.classPaths = new ArrayList<>();
        }
        this.classPaths.add(classPath);
        return this;
    }

    public SootFX mainClass(String fullyQualifiedClassName) {
        this.mainClass = fullyQualifiedClassName;
        return this;
    }

    public Set<MethodFeatureSet> extractMethodFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        SootConnector.setupSoot(mainClass, classPaths);
        //SootClass sc = Scene.v().forceResolve(mainClass, SootClass.BODIES);
        //Chain<SootClass> classes = Scene.v().getApplicationClasses();
        return new MethodFX().getFeatures(featureExtractors);
    }

    public Set<ClassFeatureSet> extractClassFeatures(Set<ClassFeatureExtractor> featureExtractors){
        SootConnector.setupSoot(mainClass, classPaths);
        return new ClassFX().getFeatures(featureExtractors);
    }

    public WholeProgramFeatureSet extractWholeProgramFeatures(Set<WholeProgramFeatureExtractor> featureExtractors){
        SootConnector.setupSoot(mainClass, classPaths);
        return new WholeProgramFX().getFeatures(featureExtractors);
    }

    public ManifestFeatureSet extractManifestFeatures(Set<ManifestFeatureExtractor> featureExtractors){
        Optional<String> apkPath = classPaths.stream().filter(c -> c.endsWith(".apk")).findFirst();
        if(!apkPath.isPresent()){
            throw new RuntimeException("classPaths do not contain any apk files");
        }
        return new ManifestFX(apkPath.get()).getFeatures(featureExtractors);
    }

}
