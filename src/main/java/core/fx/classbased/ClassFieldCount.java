package core.fx.classbased;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import soot.SootClass;

public class ClassFieldCount implements ClassFeatureExtractor<Integer> {

    @Override
    public Feature<Integer> extract(SootClass target) {
        return new Feature<>(getName(), target.getFields().size());
    }

}