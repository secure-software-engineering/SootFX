package core.fx.wholeprogrambased;

import com.google.common.collect.Streams;
import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.WholeProgramFEU;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

/**
 * only the reachable methods from callgraph
 */
public class WholeProgramAppMethodCount implements WholeProgramFEU<Long> {

    @Override
    public Feature<Long> extract(CallGraph target) {
        Iterator<Edge> iterator = target.iterator();
        Set<SootMethod> methods = new HashSet<>();
        Stream<Edge> stream = Streams.stream(iterator);
        stream.forEach(e-> {
            if(FxUtil.isAppMethod(e.src())){
                methods.add(e.src());
            }
            if(FxUtil.isAppMethod(e.tgt())){
                methods.add(e.tgt());
            }
        });
        long methodCount = methods.size();
        return new Feature<>(this.getClass().getSimpleName(), methodCount);
    }

}