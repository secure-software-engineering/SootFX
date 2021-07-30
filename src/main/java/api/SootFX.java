package api;

import core.rm.*;
import manager.ClassFX;
import manager.ManifestFX;
import manager.MethodFX;
import manager.WholeProgramFX;
import resource.SootConnector;
import core.fx.base.*;

import java.io.*;
import java.util.*;

public class SootFX {

    private String mainClass;
    private List<String> classPaths;
    private boolean appOnly;
    private String androidJars;

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

    public SootFX androidJars(String androidJars) {
        this.androidJars = androidJars;
        return this;
    }

    public SootFX appOnly() {
        appOnly = true;
        return this;
    }

    private void validate() {
        if (classPaths == null || classPaths.isEmpty()) {
            throw new RuntimeException("Class Path not set. call addClassPath(/class/path/to/target/program)");
        }
    }


    public Set<MethodFeatureSet> extractMethodFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        //SootClass sc = Scene.v().forceResolve(mainClass, SootClass.BODIES);
        //Chain<SootClass> classes = Scene.v().getApplicationClasses();
        return new MethodFX().getFeatures(featureExtractors);
    }

    public Set<MethodFeatureSet> extractAllMethodFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new MethodFX().getAllFeatures();
    }

    public Set<MethodFeatureSet> extractAllMethodFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new MethodFX().getAllFeaturesExclude(exclusion);
    }

    public Set<MethodFeatureSet> extractMethodFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new MethodFX().getFeatures(featureExtractors);
    }


    public Set<ClassFeatureSet> extractClassFeatures(Set<ClassFeatureExtractor> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ClassFX().getFeatures(featureExtractors);
    }

    public Set<ClassFeatureSet> extractAllClassFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ClassFX().getAllFeatures();
    }

    public Set<ClassFeatureSet> extractAllClassFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ClassFX().getAllFeaturesExclude(exclusion);
    }

    public Set<ClassFeatureSet> extractClassFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ClassFX().getFeatures(featureExtractors);
    }

    public WholeProgramFeatureSet extractWholeProgramFeatures(Set<WholeProgramFeatureExtractor> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new WholeProgramFX().getFeatures(featureExtractors);
    }

    public WholeProgramFeatureSet extractAllWholeProgramFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new WholeProgramFX().getAllFeatures();
    }

    public WholeProgramFeatureSet extractAllWholeProgramFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new WholeProgramFX().getAllFeaturesExclude(exclusion);
    }

    public WholeProgramFeatureSet extractWholeProgramFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new WholeProgramFX().getFeatures(featureExtractors);
    }

    public ManifestFeatureSet extractManifestFeatures(Set<ManifestFeatureExtractor> featureExtractors) {
        return new ManifestFX(getApkPath()).getFeatures(featureExtractors);
    }

    public ManifestFeatureSet extractAllManifestFeatures() {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ManifestFX(getApkPath()).getAllFeatures();
    }

    public ManifestFeatureSet extractAllManifestFeaturesExclude(Set<String> exclusion) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ManifestFX(getApkPath()).getAllFeaturesExclude(exclusion);
    }

    public ManifestFeatureSet extractManifestFeatures(List<String> featureExtractors) {
        validate();
        SootConnector.setupSoot(mainClass, classPaths, appOnly, androidJars);
        return new ManifestFX(getApkPath()).getFeatures(featureExtractors);
    }

    public void printMethodToCSV(Set<MethodFeatureSet> set, String path) {
        boolean isFirst = true;
        try (OutputStream out = new FileOutputStream(path);
             Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            for (MethodFeatureSet featureSet : set) {
                String instanceName = featureSet.getMethod().getName();
                List<String> values = new ArrayList<>();
                if (isFirst) {
                    writer.append("name,");
                    for (Feature feature : featureSet.getFeatures()) {
                        if (isFirst) {
                            writer.append(feature.getName() + ",");
                            isFirst = false;
                        }
                    }
                    writer.append(System.lineSeparator());
                }
                writer.append(instanceName + ",");
                for (Feature feature : featureSet.getFeatures()) {
                    if (isFirst) {
                        writer.append(feature.getName() + ",");
                        isFirst = false;
                    }
                }
                writer.append(System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void printMultiSetToCSV(Set<? extends AbstractFeatureSet> set, String path) throws IOException {
        boolean isFirst = true;
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (OutputStream out = new FileOutputStream(file);
             Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            for (AbstractFeatureSet featureSet : set) {
                String instanceName = featureSet.getSignature();
                if (isFirst) {
                    writer.append("name;");
                    for (Feature feature : featureSet.getFeatures()) {
                        writer.append(feature.getName() + ";");
                    }
                    writer.append(System.lineSeparator());
                    isFirst = false;
                }
                writer.append(instanceName + ";");
                for (Feature feature : featureSet.getFeatures()) {
                    writer.append(feature.getValue().toString() + ";");
                }
                writer.append(System.lineSeparator());
            }
        }
    }

    public void printSingleSetToCSV(AbstractFeatureSet set, String path) throws IOException {
        boolean isFirst = true;
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (OutputStream out = new FileOutputStream(file);
             Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            String instanceName = set.getSignature();
            writer.append("name;");
            for (Feature feature : set.getFeatures()) {
                writer.append(feature.getName() + ";");
            }
            writer.append(System.lineSeparator());
            writer.append(instanceName + ";");
            for (Feature feature : set.getFeatures()) {
                writer.append(feature.getValue().toString() + ";");
            }
        }
    }

    private String getApkPath() {
        Optional<String> apkPath = classPaths.stream().filter(c -> c.endsWith(".apk")).findFirst();
        if (!apkPath.isPresent()) {
            throw new RuntimeException("classPaths do not contain any apk files");
        }
        return apkPath.get();
    }


}
