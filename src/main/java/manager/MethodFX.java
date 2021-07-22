package manager;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import core.rm.MethodFeatureSet;
import soot.Scene;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MethodFX implements MultiInstanceFX<MethodFeatureSet, MethodFeatureExtractor> {

    @Override
    public Set<MethodFeatureSet> getFeatures(Set<MethodFeatureExtractor> featureExtractors) {
        Set<MethodFeatureSet> methodFeatureSets = new HashSet<>();
        Set<SootMethod> methods = new HashSet<>();
        Iterator<Edge> cgIter = Scene.v().getCallGraph().iterator();
        while (cgIter.hasNext()){
            Edge edge = cgIter.next();
            methods.add(edge.src());
            methods.add(edge.tgt());
        }
        for(SootMethod method: methods){
            methodFeatureSets.add(extractMethodFeature(method, featureExtractors));
        }
        return methodFeatureSets;
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
