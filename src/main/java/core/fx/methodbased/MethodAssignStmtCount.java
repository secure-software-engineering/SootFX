package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;
import soot.jimple.internal.JAssignStmt;

public class MethodAssignStmtCount implements MethodFEU<Long> {

    @Override
    public Feature<Long> extract(SootMethod target) {
        Long count = -1l; // if a method has active body but has no assign stmts it returns 0, if method has no body returns -1
        if(target.hasActiveBody()){
            count = target.getActiveBody().getUnits().stream().filter(s-> s instanceof JAssignStmt).count();
        }
        return new Feature<>(getName(), count);
    }
}
