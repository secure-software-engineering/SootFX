package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.Unit;

public class BranchCount implements MethodFeatureExtractor<Long> {

    @Override
    public Feature<Long> extract(SootMethod method) {
        if(method.hasActiveBody()){
            long stmtCount = method.getActiveBody().getUnits().parallelStream().filter(Unit::branches).count();
            return new Feature<>(this.getClass().getSimpleName(), stmtCount);
        }
        return new Feature<>(this.getClass().getSimpleName(), 0l);
    }
}
