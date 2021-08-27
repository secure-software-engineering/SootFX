package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;
import soot.Type;

public class MethodParamContainsTypeOrName implements MethodFEU<Boolean> {

    String value;

    public MethodParamContainsTypeOrName(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if(target.getParameterCount()>0){
            for(Type t: target.getParameterTypes()){
                return new Feature<>(getName(value), StringUtils.containsIgnoreCase(t.toString(), value));

            }

        }
        return new Feature<>(getName(value), false);
    }
}
