package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import core.fx.FxUtil;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;

public class MethodReturnTypeEquals implements MethodFEU<Boolean> {

    private String value;

    public MethodReturnTypeEquals(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean equals = false;
        if(StringUtils.equalsIgnoreCase(target.getReturnType().toString(), value)){
            equals = true;
        }

        if(FxUtil.isOfType(target.getReturnType(), value)){
            equals = true;
        }

        return new Feature<>(getName(value), equals);
    }
}
