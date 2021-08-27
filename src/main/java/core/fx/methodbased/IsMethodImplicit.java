package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;

public class IsMethodImplicit implements MethodFEU<Boolean> {

    /**
     * Implicit methods (e.g. methods from bytecode for access of private fields)
     * @param target
     * @return
     */
    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(), target.getName().contains("$"));
    }
}
