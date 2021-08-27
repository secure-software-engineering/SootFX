package core.fx.classbased;

import core.fx.base.ClassFEU;
import core.fx.base.Feature;
import soot.SootClass;

public class IsStaticClass implements ClassFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(SootClass target) {
        return new Feature<>(getName(), target.isStatic());
    }
}
