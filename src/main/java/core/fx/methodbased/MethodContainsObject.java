package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodContainsObject implements MethodFEU<Boolean> {

    String value;

    public MethodContainsObject(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean contains = false;
        if(target.hasActiveBody()){
            contains = StringUtils.containsIgnoreCase(target.getActiveBody().toString(), value);
        }
        return new Feature<>(getName(), contains);
    }
}
