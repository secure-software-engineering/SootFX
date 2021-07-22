package core.fx.wholeprogram;

import com.google.common.collect.Streams;
import core.fx.base.Feature;
import core.fx.base.WholeProgramFeatureExtractor;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class WholeProgramMethodCount implements WholeProgramFeatureExtractor<Long> {

    @Override
    public Feature<Long> extract(CallGraph target) {
        Iterator<Edge> iterator = target.iterator();
        Set<SootMethod> methods = new HashSet<>();
        Stream<Edge> stream = Streams.stream(iterator);
        stream.forEach(e-> {
            methods.add(e.src());
            methods.add(e.tgt());
        });
        long methodCount = methods.parallelStream().count();
        return new Feature<>(this.getClass().getSimpleName(), methodCount);
    }
}