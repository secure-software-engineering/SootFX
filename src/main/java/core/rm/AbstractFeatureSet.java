package core.rm;

import core.fx.base.Feature;

import java.util.Set;
import java.util.TreeSet;

public abstract class AbstractFeatureSet {

    protected Set<Feature> features = new TreeSet<>();

    public void addFeature(Feature feature){
        features.add(feature);
    }

    public Set<Feature> getFeatures(){
        return features;
    }

    public abstract String getSignature();

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(getSignature());
        str.append(" - ");
        for (Feature feature : features) {
            str.append(feature.getName()).append(":").append(feature.getValue()).append(",");
        }
        return str.toString();
    }
}
