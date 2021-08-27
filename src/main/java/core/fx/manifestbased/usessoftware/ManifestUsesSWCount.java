package core.fx.manifestbased.usessoftware;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.ManifestFEU;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class ManifestUsesSWCount implements ManifestFEU<Long> {

    @Override
    public Feature<Long> extract(ProcessManifest target) {
        Long count = FxUtil.getManifestUsesFeature(target).stream().filter(s->s.startsWith("android.software")).count();
        return new Feature<>(getName(), count);
    }


}