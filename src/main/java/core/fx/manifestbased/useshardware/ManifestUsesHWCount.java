package core.fx.manifestbased.useshardware;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.ManifestFeatureExtractor;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class ManifestUsesHWCount implements ManifestFeatureExtractor<Long> {

    @Override
    public Feature<Long> extract(ProcessManifest target) {
        Long count = FxUtil.getManifestUsesFeature(target).stream().filter(s->s.startsWith("android.hardware")).count();
        return new Feature<>(getName(), count);
    }


}