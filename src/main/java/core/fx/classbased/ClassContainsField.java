package core.fx.classbased;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import soot.SootClass;

public class ClassContainsField implements ClassFeatureExtractor<Integer> {

    private String value;

    public ClassContainsField(String value) {
        this.value = value;
    }

    @Override
    public Feature<Integer> extract(SootClass target) {
        target.getFields().forEach(field -> field.getName().contains(value));
        return new Feature<>(getName(), target.getMethods().size());
    }

}