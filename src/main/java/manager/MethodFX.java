package manager;

import api.FeatureDescription;
import api.FeatureGroup;
import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import core.rm.MethodFeatureSet;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.Edge;
import soot.util.Chain;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MethodFX implements MultiInstanceFX<MethodFeatureSet, MethodFeatureExtractor> {


    @Override
    public Set<MethodFeatureSet> getFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        Set<MethodFeatureSet> methodFeatureSets = new HashSet<>();
        Set<SootMethod> methods = new HashSet<>();
        Set<SootClass> classes = new HashSet<>();
        Iterator<SootClass> classIter = Scene.v().getApplicationClasses().iterator();
        while(classIter.hasNext()){
            SootClass sc = classIter.next();
            classes.add(sc);
        }
        for(SootClass sc: classes){
            methods.addAll(sc.getMethods());
        }
        // callgraph didn't have app methods
        /*Iterator<Edge> cgIter = Scene.v().getCallGraph().iterator();
        while (cgIter.hasNext()){
            Edge edge = cgIter.next();
            methods.add(edge.src());
            methods.add(edge.tgt());
        }*/
        for(SootMethod method: methods){
            methodFeatureSets.add(extractMethodFeature(method, featureExtractors));
        }
        return methodFeatureSets;
    }

    @Override
    public Set<MethodFeatureSet> getAllFeatures() {
        List<FeatureDescription> features = FxUtil.listAllMethodFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        return getFeatures(names);
    }

    @Override
    public Set<MethodFeatureSet> getAllFeaturesExclude(Set<String> exclusion) {
        List<FeatureDescription> features = FxUtil.listAllMethodFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        names.removeAll(exclusion);
        return getFeatures(names);
    }

    @Override
    public Set<MethodFeatureSet> getFeatures(List<String> featureExtractors) {
        Set<MethodFeatureExtractor> fxSet = new HashSet<>();
        for(String str: featureExtractors){
            Class<?> cls = null;
            MethodFeatureExtractor newInstance = null;
            try{
                cls = Class.forName("core.fx.methodbased." + str);
                newInstance = (MethodFeatureExtractor) cls.newInstance();
            }catch (Exception e){
                System.err.println("feature not found:" + str);
            }
            if(newInstance!=null){
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }

    private static MethodFeatureSet extractMethodFeature(SootMethod sootMethod, Set<MethodFeatureExtractor> featureExtractors){
        MethodFeatureSet rm = new MethodFeatureSet(sootMethod);
        for (MethodFeatureExtractor<?> featureExtractor : featureExtractors) {
            Feature feature = featureExtractor.extract(sootMethod);
            rm.addFeature(feature);
        }
        return rm;
    }
}
