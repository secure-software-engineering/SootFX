package core.fx.methodbased;

import core.fx.base.Feature;
import core.fx.base.MethodFeatureExtractor;
import soot.SootMethod;

import java.util.regex.Pattern;

public class IsMethodClassAnonymous implements MethodFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(SootMethod target) {
        boolean isAnonymous = false;
        int index = target.getDeclaringClass().getName().lastIndexOf("$");
        if(index != -1){
            try {
                String subclassName = target.getName().substring(index + 1);
                isAnonymous = Pattern.matches("^\\d+$", subclassName);
            }catch (Exception e){
                isAnonymous = false;
            }
        }
        return new Feature<>(getName(), isAnonymous);
    }
}
