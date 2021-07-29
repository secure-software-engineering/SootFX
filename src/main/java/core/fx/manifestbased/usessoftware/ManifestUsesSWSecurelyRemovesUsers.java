package core.fx.manifestbased.usessoftware;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.ManifestFeatureExtractor;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.List;


public class ManifestUsesSWSecurelyRemovesUsers implements ManifestFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        List<String> uses = FxUtil.getManifestUsesFeature(target);
        return new Feature<>(getName(), uses.contains("android.software.securely_removes_users"));
    }


}
