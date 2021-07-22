package manager;

import resource.ManifestConnector;
import core.fx.base.ManifestFeatureExtractor;
import core.rm.ManifestFeatureSet;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.Set;

public class ManifestFX implements SingleInstanceFX<ManifestFeatureSet, ManifestFeatureExtractor> {

    private String apkPath;

    public ManifestFX(String apkPath){
        this.apkPath = apkPath;
    }

    @Override
    public ManifestFeatureSet getFeatures(Set<ManifestFeatureExtractor> featureExtractors) {
        ProcessManifest manifest = ManifestConnector.getManifest(apkPath);
        ManifestFeatureSet manifestFeature = new ManifestFeatureSet();
        for (ManifestFeatureExtractor<?> featureExtractor : featureExtractors) {
            manifestFeature.addFeature(featureExtractor.extract(manifest));
        }
        return manifestFeature;
    }
}
