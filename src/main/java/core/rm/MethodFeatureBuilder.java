package core.rm;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * takes as input a SootMethod, and a number of featureExtractor to build a MethodFeature
 */
public class MethodFeatureBuilder {

    private SootMethod sootMethod;
    private Set<MethodFeatureExtractor> featureExtractors;

    public MethodFeatureBuilder(SootMethod sootMethod){
        this.sootMethod = sootMethod;
        this.featureExtractors = new HashSet<>();
    }

    public MethodFeatureBuilder add(MethodFeatureExtractor featureExtractor){
        featureExtractors.add(featureExtractor);
        return this;
    }

    public MethodFeature build(){
        MethodFeature rm = new MethodFeature(sootMethod);
        for (MethodFeatureExtractor<Integer> featureExtractor : featureExtractors) {
            Feature<Integer> feature = featureExtractor.extract(sootMethod);
            rm.addFeature(feature);
        }
        return rm;
    }

}
