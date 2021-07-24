package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodClassNameContains implements MethodFeatureExtractor<Boolean> {

    String value;

    public MethodClassNameContains(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(value), StringUtils.containsIgnoreCase(target.getDeclaringClass().getName(), value));
    }
}
