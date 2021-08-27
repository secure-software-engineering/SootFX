package manager;

import api.FeatureDescription;
import core.fx.FxUtil;
import resource.ManifestConnector;
import core.fx.base.ManifestFEU;
import core.rm.ManifestFeatureSet;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ManifestFX implements SingleInstanceFX<ManifestFeatureSet, ManifestFEU> {

    private String apkPath;

    public ManifestFX(String apkPath) {
        this.apkPath = apkPath;
    }

    @Override
    public ManifestFeatureSet getFeatures(Set<ManifestFEU> featureExtractors) {
        ProcessManifest manifest = ManifestConnector.getManifest(apkPath);
        ManifestFeatureSet manifestFeature = new ManifestFeatureSet();
        for (ManifestFEU<?> featureExtractor : featureExtractors) {
            manifestFeature.addFeature(featureExtractor.extract(manifest));
        }
        return manifestFeature;
    }

    @Override
    public ManifestFeatureSet getAllFeatures() {
        List<FeatureDescription> features = FxUtil.listAllManifestFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        return getFeatures(names);
    }

    @Override
    public ManifestFeatureSet getAllFeaturesExclude(Set<String> exclusion) {
        List<FeatureDescription> features = FxUtil.listAllManifestFeatures();
        List<String> names = features.stream().map(f -> f.getName()).collect(Collectors.toList());
        names.removeAll(exclusion);
        return getFeatures(names);
    }

    @Override
    public ManifestFeatureSet getFeatures(List<String> featureExtractors) {
        Set<ManifestFEU> fxSet = new HashSet<>();
        for (String str : featureExtractors) {
            Class<?> cls = null;
            ManifestFEU newInstance = null;
            try {
                if (str.startsWith("ManifestUsesHW")) {
                    cls = Class.forName("core.fx.manifestbased.useshardware." + str);
                } else if (str.startsWith("ManifestUsesSW")) {
                    cls = Class.forName("core.fx.manifestbased.usessoftware." + str);
                } else if (str.startsWith("ManifestPerm")) {
                    cls = Class.forName("core.fx.manifestbased.permission." + str);
                } else {
                    cls = Class.forName("core.fx.manifestbased." + str);
                }
                newInstance = (ManifestFEU) cls.newInstance();
            } catch (InstantiationException e) {
                //System.out.println("ignoring feature that takes an input value:" + str);
            } catch (Exception e) {
                System.err.println("feature not found:" + str);
            }
            if (newInstance != null) {
                fxSet.add(newInstance);
            }
        }
        return getFeatures(fxSet);
    }
}
