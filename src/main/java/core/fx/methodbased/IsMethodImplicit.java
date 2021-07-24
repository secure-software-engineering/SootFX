package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

public class IsMethodImplicit implements MethodFeatureExtractor<Boolean> {

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
