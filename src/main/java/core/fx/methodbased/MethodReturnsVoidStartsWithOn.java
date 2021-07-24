package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

public class MethodReturnsVoidStartsWithOn implements MethodFeatureExtractor<Boolean> {


    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean holds = target.getName().startsWith("on")
                && (target.getReturnType().toString().equals("void")
                || target.getReturnType().toString().equals("boolean"));
        return new Feature<>(getName(), holds);
    }
}
