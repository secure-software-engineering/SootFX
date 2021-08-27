package core.fx.classbased;

import core.fx.base.ClassFEU;
import core.fx.base.Feature;
import org.apache.commons.lang3.StringUtils;
import soot.SootClass;

public class ClassNameContains implements ClassFEU<Boolean> {

    String value;

    public ClassNameContains(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootClass target) {
        return new Feature<>(getName(value), StringUtils.containsIgnoreCase(target.getName(), value));
    }
}
