package core.rm;

import soot.SootClass;
import java.util.TreeSet;

public class ClassFeatureSet extends AbstractFeatureSet {

    private SootClass sootClass;

    public ClassFeatureSet(SootClass sootClass){
        this.sootClass = sootClass;
        this.features = new TreeSet<>();
    }

    @Override
    public String getSignature() {
        return sootClass.getName();
    }
}
