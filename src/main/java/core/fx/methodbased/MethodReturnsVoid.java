package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

public class MethodReturnsVoid implements MethodFeatureExtractor<Boolean> {
    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(), target.getReturnType().toString().equals("void"));
    }
}
