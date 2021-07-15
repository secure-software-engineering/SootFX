package core.rm;

import core.fx.Feature;
import core.fx.MethodCountFeatureExtractor;
import de.upb.swt.soot.core.model.SootMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * takes as input a SootMethod, and a number of featureExtractor to build a RichMethod
 */
public class RichMethodBuilder {

    private SootMethod sootMethod;
    private Set<MethodCountFeatureExtractor> featureExtractors;

    public RichMethodBuilder(SootMethod sootMethod){
        this.sootMethod = sootMethod;
        this.featureExtractors = new HashSet<>();
    }

    public RichMethodBuilder add(MethodCountFeatureExtractor featureExtractor){
        featureExtractors.add(featureExtractor);
        return this;
    }

    public RichMethod build(){
        RichMethod rm = new RichMethod(sootMethod);
        for (MethodCountFeatureExtractor featureExtractor : featureExtractors) {
            Feature<Integer> feature = featureExtractor.extract(sootMethod);
            rm.addFeature(feature);
        }
        return rm;
    }

}
