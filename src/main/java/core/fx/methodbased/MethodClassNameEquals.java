package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodClassNameEquals implements MethodFeatureExtractor<Boolean> {

    private String value;

    public MethodClassNameEquals(String value){
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(), StringUtils.equalsIgnoreCase(target.getDeclaringClass().getName(), value));
    }
}
