package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.IdentityStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.ParameterRef;
import soot.jimple.Stmt;

import java.util.HashSet;
import java.util.Set;

public class MethodCallOnParameterContains implements MethodFeatureExtractor<Boolean> {

    private String value;

    public MethodCallOnParameterContains(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean contains = false;
        if (target.isConcrete()) {
            Set<Value> paramVals = new HashSet<Value>();
            for (Unit u : target.retrieveActiveBody().getUnits()) {
                // Collect the parameters
                if (u instanceof IdentityStmt) {
                    IdentityStmt id = (IdentityStmt) u;
                    if (id.getRightOp() instanceof ParameterRef)
                        paramVals.add(id.getLeftOp());
                }
                // Check for invocations
                if (u instanceof Stmt) {
                    Stmt stmt = (Stmt) u;
                    if (stmt.containsInvokeExpr()) {
                        if (stmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
                            InstanceInvokeExpr iinv = (InstanceInvokeExpr) stmt.getInvokeExpr();
                            if (paramVals.contains(iinv.getBase())) {
                                if (StringUtils.containsIgnoreCase(iinv.getMethod().getName(), value)) {
                                    contains = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return new Feature<>(getName(value), contains);
    }
}
