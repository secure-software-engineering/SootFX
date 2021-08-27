package core.fx.manifestbased.permission;

import core.fx.base.Feature;
import core.fx.base.ManifestFEU;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class ManifestPermUseIccAuthWithDeviceIdentifier implements ManifestFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        return new Feature<>(getName(), target.getPermissions().contains("android.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER"));
    }
}
