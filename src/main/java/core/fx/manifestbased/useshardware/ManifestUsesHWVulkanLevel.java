package core.fx.manifestbased.useshardware;

import core.fx.FxUtil;
import core.fx.base.Feature;
import core.fx.base.ManifestFEU;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.util.List;


public class ManifestUsesHWVulkanLevel implements ManifestFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        List<String> uses = FxUtil.getManifestUsesFeature(target);
        return new Feature<>(getName(), uses.contains("android.hardware.vulkan.level"));
    }


}
