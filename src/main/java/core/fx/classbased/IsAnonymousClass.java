package core.fx.classbased;

import core.fx.base.ClassFEU;
import core.fx.base.Feature;
import soot.SootClass;

import java.util.regex.Pattern;

public class IsAnonymousClass implements ClassFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(SootClass target) {
        boolean isAnonymous = false;
        int index = target.getName().lastIndexOf("$");
        if(index != -1){
            String subclassName = target.getName().substring(index + 1);
            isAnonymous = Pattern.matches("^\\d+$", subclassName);
        }
        return new Feature<>(getName(), isAnonymous);
    }

}
