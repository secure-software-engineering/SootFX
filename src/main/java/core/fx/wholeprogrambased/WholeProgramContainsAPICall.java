package core.fx.wholeprogrambased;

import com.google.common.collect.Streams;
import core.fx.base.Feature;
import core.fx.base.WholeProgramFEU;
import org.apache.commons.lang3.StringUtils;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

public class WholeProgramContainsAPICall implements WholeProgramFEU<Boolean> {

    private String value;

    public WholeProgramContainsAPICall(String value) {
        this.value = value;
    }

    @Override
    public Feature<Boolean> extract(CallGraph target) {
        Iterator<Edge> iterator = target.iterator();
        Set<SootMethod> methods = new HashSet<>();
        Stream<Edge> stream = Streams.stream(iterator);
        stream.forEach(e-> {
            if(StringUtils.containsIgnoreCase(e.tgt().getName(), value)){
                methods.add(e.tgt());
            }
        });
        return new Feature<>(this.getClass().getSimpleName(), !methods.isEmpty());
    }
}