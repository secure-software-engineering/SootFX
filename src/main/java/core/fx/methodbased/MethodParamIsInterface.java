package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.Type;

public class MethodParamIsInterface implements MethodFeatureExtractor<Boolean> {
    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if(target.getParameterCount()>0){
            for(Type t: target.getParameterTypes()){
                SootClass sc = Scene.v().forceResolve(t.toString(), SootClass.HIERARCHY);
                if(sc!=null){
                    return new Feature<>(getName(), sc.isInterface());
                }
            }
        }
        return new Feature<>(getName(), false);
    }
}
