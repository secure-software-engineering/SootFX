package manager;

import core.fx.base.WholeProgramFeatureExtractor;
import core.rm.WholeProgramFeatureSet;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.Set;

public class WholeProgramFX implements SingleInstanceFX<WholeProgramFeatureSet, WholeProgramFeatureExtractor> {

    @Override
    public WholeProgramFeatureSet getFeatures(Set<WholeProgramFeatureExtractor> featureExtractors) {
        WholeProgramFeatureSet wholeProgramFeature = new WholeProgramFeatureSet();
        CallGraph cg = Scene.v().getCallGraph();
        for(WholeProgramFeatureExtractor<?> featureExtractor: featureExtractors){
            wholeProgramFeature.addFeature(featureExtractor.extract(cg));
        }
        return wholeProgramFeature;
    }
}
