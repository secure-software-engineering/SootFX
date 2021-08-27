package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFEU;
import soot.SootMethod;

public class MethodClassAccessModifier implements MethodFEU<String> {


    @Override
    public Feature<String> extract(SootMethod target) {
        String modifier = "UNK";
        if(target.getDeclaringClass().isPublic()){
            modifier = "public";
        }else if(target.getDeclaringClass().isProtected()){
            modifier = "protected";
        }else if(target.getDeclaringClass().isPrivate()){
            modifier = "private";
        }
        return new Feature<>(getName(), modifier);
    }
}
