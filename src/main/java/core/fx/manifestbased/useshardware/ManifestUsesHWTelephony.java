package core.fx.manifestbased.useshardware;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.ManifestFeatureExtractor;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.List;


public class ManifestUsesHWTelephony implements ManifestFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        List<String> uses = FxUtil.getManifestUsesFeature(target);
        return new Feature<>(getName(), uses.contains("android.hardware.telephony"));
    }


}
