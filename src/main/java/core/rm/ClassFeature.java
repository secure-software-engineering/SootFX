package core.rm;

import core.fx.base.Feature;
import soot.SootClass;

import java.util.Set;
import java.util.TreeSet;

public class ClassFeature {

    private SootClass sootClass;
    private Set<Feature> features;

    public ClassFeature(SootClass sootClass){
        this.sootClass = sootClass;
        this.features = new TreeSet<>();
    }

    public void addFeature(Feature feature){
        features.add(feature);
    }


    public Set<Feature> getFeatures(){
        return features;
    }

    public String getSignature(){
        return sootClass.getJavaStyleName();
    }

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
