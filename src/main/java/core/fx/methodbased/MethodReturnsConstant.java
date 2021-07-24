package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.Body;
import soot.SootMethod;
import soot.Unit;
import soot.jimple.Constant;
import soot.jimple.ReturnStmt;

public class MethodReturnsConstant implements MethodFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean constant = false;

        if (target.hasActiveBody()) {
            Body body = target.getActiveBody();
            for(Unit u: body.getUnits()){
                if(u instanceof ReturnStmt){
                    ReturnStmt ret = (ReturnStmt) u;
                    if(ret.getOp() instanceof Constant){
                        constant = true;
                        break;
                    }
                }
            }
        }
        return new Feature<>(getName(), constant);
    }
}
