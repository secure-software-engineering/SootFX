package core.fx.wholeprogrambased;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.WholeProgramFEU;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * all methods from scene
 */
public class WholeProgramAllAppMethodCount implements WholeProgramFEU<Long> {

    @Override
    public Feature<Long> extract(CallGraph target) {
        Set<SootMethod> methods = new HashSet<>();
        Set<SootClass> classes = new HashSet<>();
        Iterator<SootClass> classIter = Scene.v().getApplicationClasses().iterator();
        while(classIter.hasNext()){
            SootClass sc = classIter.next();
            if(FxUtil.isAppClass(sc)){
                classes.add(sc);
            }
        }
        for(SootClass sc: classes){
            methods.addAll(sc.getMethods());
        }
        long methodCount = methods.size();
        return new Feature<>(this.getClass().getSimpleName(), methodCount);
    }

}