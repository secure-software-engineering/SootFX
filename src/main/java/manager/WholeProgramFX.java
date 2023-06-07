package manager;

import api.FeatureDescription;
import api.FeatureResource;
import core.fx.FxUtil;
import core.fx.base.WholeProgramFEU;
import core.rm.WholeProgramFeatureSet;
import soot.Scene;
import soot.jimple.toolkits.callgraph.CallGraph;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WholeProgramFX implements SingleInstanceFX<WholeProgramFeatureSet, WholeProgramFEU> {

    @Override
    public WholeProgramFeatureSet getFeatures(Set<WholeProgramFEU> featureExtractors) {
        WholeProgramFeatureSet wholeProgramFeature = new WholeProgramFeatureSet();
        CallGraph cg = Scene.v().getCallGraph();
        for (WholeProgramFEU<?> featureExtractor : featureExtractors) {
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
        Set<WholeProgramFEU> fxSet = new HashSet<>();
        for (String str : featureExtractors) {
            Class<?> cls = null;
            WholeProgramFEU newInstance = null;
            try {
                cls = Class.forName("core.fx.wholeprogrambased." + str);
                newInstance = (WholeProgramFEU) cls.newInstance();
            } catch (InstantiationException e) {
                //  System.out.println("ignoring feature that takes an input value:" + str);
            } catch (Exception e) {
                System.err.println("feature not found:" + str);
            }
            if (newInstance != null) {
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }

    @Override
    public WholeProgramFeatureSet getFeatures(List<String> featureExtractors, List<FeatureResource> featureResources) {
        Set<WholeProgramFEU> fxSet = new HashSet<>();
        for (String str : featureExtractors) {
            Class<?> cls = null;
            WholeProgramFEU newInstance = null;
            try {
                cls = Class.forName("core.fx.wholeprogrambased." + str);
                Optional<FeatureResource> fr = getResourcePath(featureResources, str);
                if(fr.isPresent()){
                    String resourcePath = fr.get().getResourcePath();
                    newInstance = (WholeProgramFEU) cls.getConstructor(String.class).newInstance(resourcePath);
                }else{
                    newInstance = (WholeProgramFEU) cls.newInstance();
                }
            } catch (InstantiationException e) {
                //  System.out.println("ignoring feature that takes an input value:" + str);
            } catch (Exception e) {
                System.err.println("feature not found:" + str);
            }
            if (newInstance != null) {
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }

    private Optional<FeatureResource> getResourcePath(List<FeatureResource> featureResources, String featureName) {
        Optional<FeatureResource> first = featureResources.stream().filter(featureResource ->
                featureResource.getFeatureName().equals(featureName)).findFirst();
        return first;
    }

}
