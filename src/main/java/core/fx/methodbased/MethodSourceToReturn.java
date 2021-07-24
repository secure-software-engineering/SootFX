package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.ReturnStmt;
import soot.jimple.Stmt;

import java.util.HashSet;
import java.util.Set;

public class MethodSourceToReturn implements MethodFeatureExtractor<Boolean> {

    String value;

    public MethodSourceToReturn(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod sm) {
        if (sm.isConcrete()){
            Set<Value> paramVals = new HashSet<Value>();

            for (Unit u : sm.retrieveActiveBody().getUnits()) {
                // Check for invocations
                if (((Stmt) u).containsInvokeExpr()) {
                    InvokeExpr invokeExpr = ((Stmt) u).getInvokeExpr();
                    Value leftOp = null;
                    if (u instanceof AssignStmt) leftOp = ((AssignStmt) u).getLeftOp();
                    if (leftOp != null) paramVals.add(leftOp);
                    // TODO: Add arguments as well? Not sure.
                    if (invokeExpr.getMethod().getName().toLowerCase()
                            .contains(value.toLowerCase())) {
                        paramVals.addAll(invokeExpr.getArgs());
                    }
                }

                if (u instanceof AssignStmt) {
                    Value leftOp = ((AssignStmt) u).getLeftOp();
                    Value rightOp = ((AssignStmt) u).getRightOp();
                    if (paramVals.contains(leftOp)) paramVals.remove(leftOp);
                    if (paramVals.contains(rightOp)) {
                        paramVals.add(leftOp);
                    }
                }

                // Check for invocations
                if (u instanceof ReturnStmt) {
                    ReturnStmt stmt = (ReturnStmt) u;
                    return new Feature<>(getName(value), paramVals.contains(stmt.getOp()));
                }
            }
        }
        return new Feature<>(getName(value), false);
    }
}
