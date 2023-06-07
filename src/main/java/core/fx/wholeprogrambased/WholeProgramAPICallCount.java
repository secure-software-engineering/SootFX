package core.fx.wholeprogrambased;

import com.google.common.collect.Streams;
import core.fx.base.Feature;
import core.fx.base.WholeProgramFEU;
import resource.FileConnector;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Edge;

import java.util.*;
import java.util.stream.Stream;

public class WholeProgramAPICallCount implements WholeProgramFEU<Map<String, Long>> {

    private List<String> methodSignatures;

    public WholeProgramAPICallCount(String resourcePath) {
        this.methodSignatures = FileConnector.getMethodSignatures(resourcePath);
    }

    @Override
    public Feature<Map<String, Long>> extract(CallGraph target) {
        Iterator<Edge> iterator = target.iterator();
        Map<String, Long> methodCount = new HashMap<>();
        Stream<Edge> stream = Streams.stream(iterator);
        stream.forEach(e-> {
            if(methodSignatures.contains(e.tgt().getSignature())){
                if(methodCount.containsKey(e.tgt().getSignature())){
                    Long count = methodCount.get(e.tgt().getSignature());
                    methodCount.put(e.tgt().getSignature(), ++count);
                }else{
                    methodCount.put(e.tgt().getSignature(), 1L);
                }
            }
            System.out.println(e.tgt());
        });
        return new Feature<>(this.getClass().getSimpleName(), methodCount);
    }
}