package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.Body;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;

import java.util.ArrayList;
import java.util.List;

public class MethodCallsMethod implements MethodFeatureExtractor<Boolean> {

    private String className;
    private String methodName;


    public MethodCallsMethod(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean calls = false;

        calls = checkMethod(target, new ArrayList<>());

        return new Feature<>(getName(className + "." + methodName), calls);
    }

    public boolean checkMethod(SootMethod method, List<SootMethod> doneList) {
        if (doneList.contains(method))
            return false;
        if (!method.isConcrete())
            return false;
        doneList.add(method);

        try {
            Body body = method.getActiveBody();
            for (Unit u : body.getUnits()) {
                if (!(u instanceof Stmt))
                    continue;
                Stmt stmt = (Stmt) u;
                if (!stmt.containsInvokeExpr())
                    continue;

                InvokeExpr inv = stmt.getInvokeExpr();
                if (StringUtils.startsWithIgnoreCase(inv.getMethod().getName(), this.methodName)) {
                    if (this.className.isEmpty() || this.className
                            .equals(inv.getMethod().getDeclaringClass().getName()))
                        return true;
                } else if (checkMethod(inv.getMethod(), doneList) == true)
                    return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
