package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.Stmt;

public class MethodCallContains implements MethodFEU<Boolean> {

    private String value;

    public MethodCallContains(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean contains = false;
        if (target.isConcrete()) {
            for (Unit u : target.getActiveBody().getUnits()) {
                if (u instanceof Stmt) {
                    Stmt stmt = (Stmt) u;
                    if (stmt.containsInvokeExpr()) {
                        if (stmt.getInvokeExpr() instanceof InstanceInvokeExpr) {
                            InstanceInvokeExpr iinv = (InstanceInvokeExpr) stmt.getInvokeExpr();
                            if (StringUtils.containsIgnoreCase(iinv.getMethod().getName(), value)) {
                                contains = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        return new Feature<>(getName(value), contains);
    }
}
