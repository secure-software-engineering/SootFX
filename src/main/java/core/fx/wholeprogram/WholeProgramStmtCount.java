package core.fx.wholeprogram;

import core.fx.base.Feature;
import core.fx.base.WholeProgramFeatureExtractor;
import soot.Body;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class WholeProgramStmtCount implements WholeProgramFeatureExtractor<Long> {

    @Override
    public Feature<Long> extract(CallGraph target) {
        Iterator<Edge> iterator = target.iterator();
        Set<SootMethod> methods = new HashSet<>();
        while(iterator.hasNext()){
            Edge edge = iterator.next();
            methods.add(edge.src());
            methods.add(edge.tgt());
        }
        long unitCount = methods.parallelStream().filter(SootMethod::hasActiveBody).map(SootMethod::getActiveBody).map(Body::getUnits).count();
        return new Feature<>(this.getClass().getSimpleName(), unitCount);
    }
}
