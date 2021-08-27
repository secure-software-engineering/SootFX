package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;

public class MethodReturnsVoidStartsWithOn implements MethodFEU<Boolean> {


    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean holds = target.getName().startsWith("on")
                && (target.getReturnType().toString().equals("void")
                || target.getReturnType().toString().equals("boolean"));
        return new Feature<>(getName(), holds);
    }
}
