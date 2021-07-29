package core.fx.methodbased.analysis;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.*;
import soot.jimple.AssignStmt;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

import java.util.HashSet;
import java.util.Set;

public class MethodAnalysisNullPointer implements MethodFeatureExtractor<Boolean> {

    static class NullPointerAnalysis extends ForwardFlowAnalysis<Unit, Set<Value>>{

        public NullPointerAnalysis(Body body) {
            super(new ExceptionalUnitGraph(body));
        }

        @Override
        protected Set<Value> newInitialFlow() {
            return new HashSet<>();
        }

        @Override
        protected void merge(Set<Value> in1, Set<Value> in2, Set<Value> out) {
            out.addAll(in1);
            out.addAll(in2);
        }

        @Override
        protected void copy(Set<Value> src, Set<Value> dst) {
            dst.addAll(src);
        }

        @Override
        protected void flowThrough(Set<Value> in, Unit unit, Set<Value> out) {
            copy(in, out);
            if(unit instanceof AssignStmt){
                AssignStmt stmt = (AssignStmt) unit;
                if(stmt.getRightOp() instanceof NullType){
                    out.add(stmt.getLeftOp());
                }else if(in.contains(stmt.getRightOp())){
                    out.add(stmt.getLeftOp());
                }else{
                    out.remove(stmt.getLeftOp());
                }
            }
        }

        @Override
        public void doAnalysis() {
            super.doAnalysis();
        }
    }

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        if(target.hasActiveBody()){
            if(target.getDeclaringClass().getName().contains("NullPointerClass")){
                System.out.println("yo");
            }
            NullPointerAnalysis analysis = new NullPointerAnalysis(target.getActiveBody());
            analysis.doAnalysis();
            Set<Value> factsAtEnd = analysis.getFlowAfter(target.getActiveBody().getUnits().getLast());
            return new Feature<>(getName(), !factsAtEnd.isEmpty());
        }
        return new Feature<>(getName(), false);
    }
}
