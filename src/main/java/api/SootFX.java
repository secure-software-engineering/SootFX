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
    private boolean appOnly;

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

    public SootFX appOnly(){
        appOnly = true;
        return this;
    }

    private void validate(){
        if(classPaths==null || classPaths.isEmpty()){
            throw new RuntimeException("Class Path not set. call addClassPath(/class/path/to/target/program)");
        }
    }


    public Set<MethodFeatureSet> extractMethodFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        //SootClass sc = Scene.v().forceResolve(mainClass, SootClass.BODIES);
        //Chain<SootClass> classes = Scene.v().getApplicationClasses();
        return new MethodFX().getFeatures(featureExtractors);
    }

    public Set<MethodFeatureSet> extractAllMethodFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new MethodFX().getAllFeatures();
    }

    public Set<MethodFeatureSet> extractAllMethodFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new MethodFX().getAllFeaturesExclude(exclusion);
    }

    public Set<MethodFeatureSet> extractMethodFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new MethodFX().getFeatures(featureExtractors);
    }


    public Set<ClassFeatureSet> extractClassFeatures(Set<ClassFeatureExtractor> featureExtractors){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ClassFX().getFeatures(featureExtractors);
    }

    public Set<ClassFeatureSet> extractAllClassFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ClassFX().getAllFeatures();
    }

    public Set<ClassFeatureSet> extractAllClassFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ClassFX().getAllFeaturesExclude(exclusion);
    }

    public Set<ClassFeatureSet> extractClassFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ClassFX().getFeatures(featureExtractors);
    }

    public WholeProgramFeatureSet extractWholeProgramFeatures(Set<WholeProgramFeatureExtractor> featureExtractors){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new WholeProgramFX().getFeatures(featureExtractors);
    }

    public WholeProgramFeatureSet extractAllWholeProgramFeatures(){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new WholeProgramFX().getAllFeatures();
    }

    public WholeProgramFeatureSet extractAllWholeProgramFeaturesExclude(Set<String> exclusion){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new WholeProgramFX().getAllFeaturesExclude(exclusion);
    }

    public WholeProgramFeatureSet extractWholeProgramFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new WholeProgramFX().getFeatures(featureExtractors);
    }

    public ManifestFeatureSet extractManifestFeatures(Set<ManifestFeatureExtractor> featureExtractors){
        return new ManifestFX(getApkPath()).getFeatures(featureExtractors);
    }

    public ManifestFeatureSet extractAllManifestFeatures(){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ManifestFX(getApkPath()).getAllFeatures();
    }

    public ManifestFeatureSet extractAllManifestFeaturesExclude(Set<String> exclusion){
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ManifestFX(getApkPath()).getAllFeaturesExclude(exclusion);
    }

    public ManifestFeatureSet extractManifestFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly);
        return new ManifestFX(getApkPath()).getFeatures(featureExtractors);
    }

    private String getApkPath() {
        Optional<String> apkPath = classPaths.stream().filter(c -> c.endsWith(".apk")).findFirst();
        if(!apkPath.isPresent()){
            throw new RuntimeException("classPaths do not contain any apk files");
        }
        return apkPath.get();
    }

}
