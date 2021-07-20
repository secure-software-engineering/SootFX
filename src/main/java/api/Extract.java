package api;

import core.rm.MethodFeature;

import java.util.Set;

public class Extract {

    Set<MethodFeature> methodFeatures;

    Extract(Set<MethodFeature> methodFeatures){
        this.methodFeatures = methodFeatures;
    }

    public Set<MethodFeature> get(){
        return methodFeatures;
    }

}
