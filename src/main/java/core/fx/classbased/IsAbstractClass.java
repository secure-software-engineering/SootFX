package core.fx.classbased;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import soot.SootClass;

public class IsAbstractClass implements ClassFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(SootClass target) {
        return new Feature<>(getName(), target.isAbstract());
    }
}
