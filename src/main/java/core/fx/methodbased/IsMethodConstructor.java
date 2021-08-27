package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;

public class IsMethodConstructor implements MethodFEU<Boolean> {
    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(), target.getName().equals("<init>")
                || target.getName().equals("<clinit>"));
    }
}
