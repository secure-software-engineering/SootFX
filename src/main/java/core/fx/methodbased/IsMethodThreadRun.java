package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import core.fx.FxUtil;
import soot.SootMethod;

public class IsMethodThreadRun implements MethodFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean isThreadRun = false;
        if (target.getName().equals("run")) {
            try {
                if (FxUtil.isOfType(target.getDeclaringClass().getType(), "java.lang.Runnable")) {
                    isThreadRun = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new Feature<>(getName(), isThreadRun);
    }
}
