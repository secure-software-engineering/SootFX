package core.fx;

import de.upb.swt.soot.core.model.SootMethod;

public class StmtCountExtractor implements MethodCountFeatureExtractor {


    @Override
    public Feature<Integer> extract(SootMethod method) {
        int stmtCount = method.getBody().getStmts().size();
        return new Feature<>("StmtCount", stmtCount);
    }
}
