package core.rm;

import soot.SootMethod;
import java.util.TreeSet;

public class MethodFeatureSet extends AbstractFeatureSet {

    private SootMethod sootMethod;

    public MethodFeatureSet(SootMethod sootMethod){
        this.sootMethod = sootMethod;
        this.features = new TreeSet<>();
    }

    @Override
    public String getSignature() {
        return sootMethod.getSignature();
    }
}
