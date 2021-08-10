package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.Unit;

public class MethodBranchCount implements MethodFeatureExtractor<Long> {

    @Override
    public Feature<Long> extract(SootMethod method) {
        Long count = -1l;

        if(method.hasActiveBody()){
            count = method.getActiveBody().getUnits().stream().filter(Unit::branches).count();
        }
        return new Feature<>(getName(), count);
    }
}
