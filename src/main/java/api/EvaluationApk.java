package api;

import com.google.common.base.Stopwatch;
import core.rm.*;

import java.io.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EvaluationApk {

    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 7; i++) {
            String apkName = "benign-" + i;
            String path = "/Users/kadiray/Workspace/drebin/benign/" + apkName + ".apk";
            String out = "/Users/kadiray/Workspace/SootFX/eval/apk-benign/" + apkName + "/";

            try {
                Stopwatch stopwatch = Stopwatch.createStarted();

                methodFeatures(path, out);
                long methodDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                classFeatures(path, out);
                long classDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                wpFeatures(path, out);
                long wpDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                manifestFeatures(path, out);
                long manifestDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                logMeta(out, methodDone, classDone - methodDone, wpDone - classDone, manifestDone - wpDone, new File(path).length());
            } catch (Exception e){
                e.printStackTrace();
                System.err.println("error in apk:" + apkName);
            }
        }
    }

    public static void logMeta(String path, long methodTime, long classTime, long wpTime, long manifestTime, long fileSizeInBytes) throws IOException {
        path += "meta.csv";
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        try (OutputStream out = new FileOutputStream(file);
             Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            writer.append("name;").append("value").append(System.lineSeparator());
            writer.append("method;").append(String.valueOf(methodTime)).append(System.lineSeparator());
            writer.append("class;").append(String.valueOf(classTime)).append(System.lineSeparator());
            writer.append("whole-program;").append(String.valueOf(wpTime)).append(System.lineSeparator());
            writer.append("manifest;").append(String.valueOf(manifestTime)).append(System.lineSeparator());
            writer.append("size;").append(String.valueOf(fileSizeInBytes));
        }
    }

    public static void classFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        sootFX.androidJars("/Users/kadiray/Library/Android/sdk/platforms");
        Set<ClassFeatureSet> featureSets = sootFX.extractAllClassFeatures();
        sootFX.printMultiSetToCSV(featureSets, out + "class.csv");
    }

    public static void methodFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        sootFX.androidJars("/Users/kadiray/Library/Android/sdk/platforms");
        Set<MethodFeatureSet> featureSets = sootFX.extractAllMethodFeatures();
        sootFX.printMultiSetToCSV(featureSets, out + "method.csv");
    }

    public static void wpFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        sootFX.androidJars("/Users/kadiray/Library/Android/sdk/platforms");
        WholeProgramFeatureSet featureSet = sootFX.extractAllWholeProgramFeatures();
        sootFX.printSingleSetToCSV(featureSet, out + "wp.csv");
    }

    public static void manifestFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        sootFX.androidJars("/Users/kadiray/Library/Android/sdk/platforms");
        ManifestFeatureSet featureSet = sootFX.extractAllManifestFeatures();
        sootFX.printSingleSetToCSV(featureSet, out + "manifest.csv");
    }

}
