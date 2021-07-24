package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodClassPackageNameEquals implements MethodFeatureExtractor<Boolean> {

    String value;

    public MethodClassPackageNameEquals(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(value), StringUtils.equalsIgnoreCase(target.getDeclaringClass().getPackageName(), value));
    }
}
