package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.jimple.internal.JAssignStmt;

public class AssignStmtCount implements MethodFeatureExtractor<Integer> {

    @Override
    public Feature<Integer> extract(SootMethod target) {
        if(target.hasActiveBody()){
            int count = (int) target.getActiveBody().getUnits().stream().filter(s-> s instanceof JAssignStmt).count();
            return new Feature<>(this.getClass().getSimpleName(), count);
        }
        return new Feature<>(this.getClass().getSimpleName(), 0);
    }
}
