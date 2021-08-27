package eval;

import api.SootFX;
import com.google.common.base.Stopwatch;
import core.rm.ClassFeatureSet;
import core.rm.MethodFeatureSet;
import core.rm.WholeProgramFeatureSet;

import java.io.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class EvaluationJar {

    public static void main(String[] args) throws IOException {
        for (int i = 74; i <= 74; i++) {
            String jarName = "mvn-" + i;
            String path = "/Users/kadiray/Workspace/maven-top/" + jarName + ".jar";
            String out = "/Users/kadiray/Workspace/SootFX/eval/jar/" + jarName + "/";

            try {
                Stopwatch stopwatch = Stopwatch.createStarted();

                methodFeatures(path, out);
                long methodDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                classFeatures(path, out);
                long classDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                wpFeatures(path, out);
                long wpDone = stopwatch.elapsed(TimeUnit.MILLISECONDS);

                logMeta(out, methodDone, classDone - methodDone, wpDone - classDone, new File(path).length());
            } catch (Exception e){
                e.printStackTrace();
                System.err.println("error in apk:" + jarName);
            }
        }
    }

    public static void logMeta(String path, long methodTime, long classTime, long wpTime, long fileSizeInBytes) throws IOException {
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
            writer.append("size;").append(String.valueOf(fileSizeInBytes));
        }
    }

    public static void classFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        Set<ClassFeatureSet> featureSets = sootFX.extractAllClassFeatures();
        sootFX.printMultiSetToCSV(featureSets, out + "class.csv");
    }

    public static void methodFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        Set<MethodFeatureSet> featureSets = sootFX.extractAllMethodFeatures();
        sootFX.printMultiSetToCSV(featureSets, out + "method.csv");
    }

    public static void wpFeatures(String path, String out) throws IOException {
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        WholeProgramFeatureSet featureSet = sootFX.extractAllWholeProgramFeatures();
        sootFX.printSingleSetToCSV(featureSet, out + "wp.csv");
    }


}
