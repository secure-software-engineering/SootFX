package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;
import soot.Type;

import java.util.List;

public class MethodParamTypeMatchesReturnType implements MethodFeatureExtractor<Boolean> {
    @Override
    public Feature<Boolean> extract(SootMethod target) {
        List<Type> paramList = target.getParameterTypes();
        for (Type param : paramList) {
            if (StringUtils.containsIgnoreCase(param.toString(), target.getReturnType().toString())){
                return new Feature<>(getName(), true);
            }
        }
        return new Feature<>(getName(), false);
    }
}
