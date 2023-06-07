package api;

import core.rm.ClassFeatureSet;
import core.rm.ManifestFeatureSet;
import core.rm.MethodFeatureSet;
import core.rm.WholeProgramFeatureSet;
import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CLI {

    public static void main(String[] args) throws IOException {
        String classPath=null;
        String outPath=null;
        String configPath=null;
        String androidJars=null;
        if(args.length>=1){
            classPath = args[0];
        }
        if(args.length>=2){
            outPath = args[1];
        }
        if(args.length>=3){
            if(args[2].endsWith("yaml")){
                configPath = args[2];
            } else{
                androidJars = args[2];
            }
        }
        if(args.length==4){
            androidJars = args[3];
        }

        if(StringUtils.isEmpty(classPath) || StringUtils.isEmpty(outPath)){
            throw new RuntimeException("usage: CLI \"path/to/app\" \"path/to/out\"");
        }else if(StringUtils.isEmpty(configPath)){
            // extract all features
            methodFeatures(classPath, outPath, null, null);
            classFeatures(classPath, outPath, null, null);
            wpFeatures(classPath, outPath, androidJars,null, null, null);

            if(!StringUtils.isEmpty(androidJars) && classPath.endsWith(".apk")){
                manifestFeatures(classPath, outPath, androidJars, null, null);
            }

        }else if(!StringUtils.isEmpty(configPath)){
            Config config = getConfig(configPath);
            List<FeatureResource> featureResources = config.getFeatureResources();
            methodFeatures(classPath, outPath, config.getMethodFeatureInclusion(), config.getMethodFeatureExclusion());
            classFeatures(classPath, outPath, config.getClassFeatureInclusion(), config.getClassFeatureExclusion());
            wpFeatures(classPath, outPath, androidJars, config.getWholeProgFeatureInclusion(), config.getWholeProgFeatureExclusion(), featureResources);
            if(!StringUtils.isEmpty(androidJars) && classPath.endsWith(".apk")) {
                manifestFeatures(classPath, outPath, androidJars, config.getManifestFeatureInclusion(), config.getManifestFeatureExclusion());
            }
        }
    }


    public static void methodFeatures(String path, String out, List<String> include, List<String> exclude) throws IOException {
        if(include == null){
            return;
        }
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        Set<MethodFeatureSet> featureSets = null;
        if((include==null || include.isEmpty()) && (exclude==null || exclude.isEmpty())){
            featureSets = sootFX.extractAllMethodFeatures();
        }else if(include==null || include.isEmpty()){
            featureSets = sootFX.extractMethodFeaturesExclude(new HashSet<>(exclude));
        }else if(exclude==null || exclude.isEmpty()){
            featureSets = sootFX.extractMethodFeaturesInclude(include);
        }else if(!include.isEmpty() && !exclude.isEmpty()){
            throw new RuntimeException("You must either provide methodFeatureInclusion or methodFeatureExclusion in config.yaml. \n Inclusion list only extracts the selected features. Exclusion list extracts all but the selected features.");
        }
        sootFX.printMultiSetToCSV(featureSets, out + "method.csv");
    }

    public static void classFeatures(String path, String out, List<String> include, List<String> exclude) throws IOException {
        if(include == null){
            return;
        }
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        Set<ClassFeatureSet> featureSets = null;
        if((include==null || include.isEmpty()) && (exclude==null || exclude.isEmpty())){
            featureSets = sootFX.extractAllClassFeatures();
        }else if(include==null || include.isEmpty()){
            featureSets = sootFX.extractClassFeaturesExclude(new HashSet<>(exclude));
        }else if(exclude==null || exclude.isEmpty()){
            featureSets = sootFX.extractClassFeaturesInclude(include);
        }else if(!include.isEmpty() && !exclude.isEmpty()){
            throw new RuntimeException("You must either provide classFeatureInclusion or classFeatureExclusion in config.yaml. \n Inclusion list only extracts the selected features. Exclusion list extracts all but the selected features.");
        }
        sootFX.printMultiSetToCSV(featureSets, out + "class.csv");
    }

    public static void wpFeatures(String path, String out, String androidJars, List<String> include, List<String> exclude, List<FeatureResource> featureResources) throws IOException {
        if(include == null){
            return;
        }
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.androidJars(androidJars);
        sootFX.appOnly();
        WholeProgramFeatureSet featureSet = null;
        if((include==null || include.isEmpty()) && (exclude==null || exclude.isEmpty())){
            featureSet = sootFX.extractAllWholeProgramFeatures();
        } else if(include==null || include.isEmpty()){
            featureSet = sootFX.extractWholeProgramFeaturesExclude(new HashSet<>(exclude));
        } else if(exclude==null || exclude.isEmpty()){
            featureSet = sootFX.extractWholeProgramFeaturesInclude(include, featureResources);
        }else if(!include.isEmpty() && !exclude.isEmpty()){
            throw new RuntimeException("You must either provide wholeProgFeatureInclusion or wholeProgFeatureExclusion  in config.yaml. \n Inclusion list only extracts the selected features. Exclusion list extracts all but the selected features.");
        }
        sootFX.printSingleSetToCSV(featureSet, out + "wp.csv");
    }

    public static void manifestFeatures(String path, String out, String androidJars, List<String> include, List<String> exclude) throws IOException {
        if(include == null){
            return;
        }
        SootFX sootFX = new SootFX();
        sootFX.addClassPath(path);
        sootFX.appOnly();
        sootFX.androidJars(androidJars); // "/Users/user/Library/Android/sdk/platforms"
        ManifestFeatureSet featureSet = null;
        if((include==null || include.isEmpty()) && (exclude==null || exclude.isEmpty())){
            featureSet = sootFX.extractAllManifestFeatures();
        } else if(include==null || include.isEmpty()){
            featureSet = sootFX.extractManifestFeaturesExclude(new HashSet<>(exclude));
        } else if(exclude==null || exclude.isEmpty()){
            featureSet = sootFX.extractManifestFeaturesInclude(include);
        } else if(!include.isEmpty() && !exclude.isEmpty()){
            throw new RuntimeException("You must either provide manifestFeatureInclusion or manifestFeatureExclusion in config.yaml. \n Inclusion list only extracts the selected features. Exclusion list extracts all but the selected features.");
        }
        sootFX.printSingleSetToCSV(featureSet, out + "manifest.csv");
    }

    public static Config getConfig(String configPath) throws FileNotFoundException {
        Yaml yaml = new Yaml(new Constructor(Config.class));
        InputStream inputStream = new FileInputStream(configPath);
        Config config = yaml.load(inputStream);
        return config==null ? new Config() : config;
    }

}
