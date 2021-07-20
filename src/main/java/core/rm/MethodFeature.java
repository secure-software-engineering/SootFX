package core.rm;

import core.fx.base.Feature;
import soot.SootMethod;

import java.util.Set;
import java.util.TreeSet;

/**
 * A MethodFeature is a wrapper around sootMethod that is enriched with a set of features
 */
public class MethodFeature {

    private SootMethod sootMethod;
    private Set<Feature> features;

    public MethodFeature(SootMethod sootMethod){
        this.sootMethod = sootMethod;
        this.features = new TreeSet<>();
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }


    public Set<Feature> getFeatures(){
        return features;
    }
    
    public String getSignature(){
        return sootMethod.getSignature();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(sootMethod.getSignature());
        str.append(" - ");
        for (Feature feature : features) {
            str.append(feature.getName()).append(":").append(feature.getValue()).append(",");
        }
        return str.toString();
    }
}
