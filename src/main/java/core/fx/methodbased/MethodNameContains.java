package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodNameContains implements MethodFEU<Boolean> {

    private String value;

    public MethodNameContains(String value){
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        return new Feature<>(getName(value), StringUtils.containsIgnoreCase(target.getName(), value));
    }
}
