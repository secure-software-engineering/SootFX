package core.fx;

import de.upb.swt.soot.core.jimple.common.stmt.JAssignStmt;
import de.upb.swt.soot.core.model.SootMethod;

public class AssignStmtCountExtractor implements MethodCountFeatureExtractor{

    @Override
    public Feature<Integer> extract(SootMethod target) {
        int count = (int) target.getBody().getStmts().stream().filter(s-> s instanceof JAssignStmt).count();
        return new Feature<>("AssignStmtCount", count);
    }
}
