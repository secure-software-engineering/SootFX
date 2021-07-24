package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.*;

import java.util.HashSet;
import java.util.Set;


public class MethodParamToSink implements MethodFeatureExtractor<Boolean> {

    private String value;

    public MethodParamToSink(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if (target.isConcrete()) {
            Set<Value> paramVals = new HashSet<Value>();
            for (Unit u : target.retrieveActiveBody().getUnits()) {
                // Collect the parameters
                if (u instanceof IdentityStmt) {
                    IdentityStmt id = (IdentityStmt) u;
                    if (id.getRightOp() instanceof ParameterRef)
                        paramVals.add(id.getLeftOp());
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
                if (((Stmt) u).containsInvokeExpr()) {
                    InvokeExpr invokeExpr = ((Stmt) u).getInvokeExpr();
                    if (invokeExpr.getMethod().getName().toLowerCase()
                            .contains(value.toLowerCase())) {
                        for (Value arg : invokeExpr.getArgs()) {
                            if (paramVals.contains(arg)) {
                                return new Feature<>(getName(value), true);
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
