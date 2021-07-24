package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.HashSet;
import java.util.Set;

public class IsMethodRealSetter implements MethodFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if (target.getName().startsWith("set") && target.isConcrete()){
            Set<Value> paramVals = new HashSet<Value>();
            for (Unit u : target.retrieveActiveBody().getUnits()) {
                if (u instanceof IdentityStmt) {
                    IdentityStmt id = (IdentityStmt) u;
                    if (id.getRightOp() instanceof ParameterRef){
                        paramVals.add(id.getLeftOp());
                    }
                } else if (u instanceof AssignStmt) {
                    AssignStmt assign = (AssignStmt) u;
                    if (paramVals.contains(assign.getRightOp())){
                        if (assign.getLeftOp() instanceof InstanceFieldRef){
                            return new Feature<>(getName(), true);
                        }
                    }
                }
                if (u instanceof Stmt) {
                    Stmt stmt = (Stmt) u;
                    if (stmt.containsInvokeExpr()) {
                        if (stmt.getInvokeExpr().getMethod().getName().startsWith("get")){
                            for (Value arg : stmt.getInvokeExpr().getArgs()){
                                if (paramVals.contains(arg)){
                                    return new Feature<>(getName(), true);
                                }
                            }
                        }
                    }
                }
            }
            return new Feature<>(getName(), false);
        }
        return new Feature<>(getName(), false);
    }
}
