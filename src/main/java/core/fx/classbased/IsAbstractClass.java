package core.fx.classbased;

import core.fx.base.ClassFEU;
import core.fx.base.Feature;
import soot.SootClass;

public class IsAbstractClass implements ClassFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(SootClass target) {
        return new Feature<>(getName(), target.isAbstract());
    }
}
