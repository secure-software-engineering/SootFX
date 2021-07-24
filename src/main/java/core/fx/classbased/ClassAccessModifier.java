package core.fx.classbased;

import core.fx.base.ClassFeatureExtractor;
import core.fx.base.Feature;
import soot.SootClass;

public class ClassAccessModifier implements ClassFeatureExtractor<String> {

    @Override
    public Feature<String> extract(SootClass target) {
        String modifier = "UNK";
        if(target.isPublic()){
            modifier = "public";
        }else if(target.isProtected()){
            modifier = "protected";
        }else if(target.isPrivate()){
            modifier = "private";
        }
        return new Feature<>(getName(), modifier);
    }
}
