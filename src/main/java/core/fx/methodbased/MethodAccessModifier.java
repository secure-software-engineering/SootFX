package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

public class MethodAccessModifier implements MethodFeatureExtractor<String> {

    @Override
    public Feature<String> extract(SootMethod target) {
        String modifier = "UNK";
        if(target.isPublic()){
            modifier = "public";
        }else if(target.isProtected()){
            modifier = "protected";
        }else if(target.isPrivate()){
            modifier = "private";
        }
        return new Feature<>(getName(), modifier);
    }
}
