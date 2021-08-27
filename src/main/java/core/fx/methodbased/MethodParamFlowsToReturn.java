package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.jimple.IdentityStmt;
import soot.jimple.ParameterRef;
import soot.jimple.ReturnStmt;

import java.util.HashSet;
import java.util.Set;

public class MethodParamFlowsToReturn implements MethodFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if (target.isConcrete()) {
            Set<Value> paramVals = new HashSet<>();
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
                if (u instanceof ReturnStmt) {
                    ReturnStmt stmt = (ReturnStmt) u;
                    return new Feature<>(getName(), paramVals.contains(stmt.getOp()));
                }
            }
        }
        return new Feature<>(getName(), false);
    }
}
