package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

public class MethodStmtCount implements MethodFeatureExtractor<Integer> {


    @Override
    public Feature<Integer> extract(SootMethod method) {
        if(method.hasActiveBody()){
            int stmtCount = method.getActiveBody().getUnits().size();
            return new Feature<>(this.getClass().getSimpleName(), stmtCount);
        }
        return new Feature<>(this.getClass().getSimpleName(), 0);
    }
}
