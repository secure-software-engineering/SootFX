package core.rm;

import core.fx.Feature;
import de.upb.swt.soot.core.model.SootMethod;

import java.util.HashSet;
import java.util.Set;

/**
 * A RichMethod is a wrapper around sootMethod that is enriched with a set of features
 */
public class RichMethod {

    private SootMethod sootMethod;
    private Set<Feature<Integer>> features;

    RichMethod(SootMethod sootMethod){
        this.sootMethod = sootMethod;
        this.features = new HashSet<>();
    }

    void addFeature(Feature<Integer> feature){
        features.add(feature);
    }

    public Set<Feature<Integer>> getFeatures() {
        return features;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(sootMethod.getSignature().toString());
        str.append(" - ");
        for (Feature<Integer> feature : features) {
            str.append(feature.getName()).append(":").append(feature.getValue()).append(",");
        }
        return str.toString();
    }
}
