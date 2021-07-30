package core.fx.classbased;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import soot.SootClass;

public class ClassContainsMethod implements ClassFeatureExtractor<Integer> {

    private String value;

    public ClassContainsMethod(String value) {
        this.value = value;
    }

    @Override
    public Feature<Integer> extract(SootClass target) {
        target.getMethods().forEach(sootMethod -> sootMethod.getName().contains(value));
        return new Feature<>(getName(), target.getMethods().size());
    }

}