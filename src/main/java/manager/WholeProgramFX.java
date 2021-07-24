package manager;

import api.FeatureDescription;
import core.fx.FxUtil;
import core.fx.base.FeatureExtractor;
import core.fx.base.MethodFeatureExtractor;
import core.fx.base.WholeProgramFeatureExtractor;
import core.rm.WholeProgramFeatureSet;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Override
    public WholeProgramFeatureSet getAllFeatures() {
        List<FeatureDescription> features = FxUtil.listAllWholeProgramFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        return getFeatures(names);
    }

    @Override
    public WholeProgramFeatureSet getAllFeaturesExclude(Set<String> exclusion) {
        List<FeatureDescription> features = FxUtil.listAllWholeProgramFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        names.removeAll(exclusion);
        return getFeatures(names);
    }

    @Override
    public WholeProgramFeatureSet getFeatures(List<String> featureExtractors) {
        Set<WholeProgramFeatureExtractor> fxSet = new HashSet<>();
        for(String str: featureExtractors){
            Class<?> cls = null;
            WholeProgramFeatureExtractor newInstance = null;
            try{
                cls = Class.forName("core.fx.wholeprogrambased." + str);
                newInstance = (WholeProgramFeatureExtractor) cls.newInstance();
            }catch (Exception e){
                System.err.println("feature not found:" + str);
            }
            if(newInstance!=null){
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }
}
