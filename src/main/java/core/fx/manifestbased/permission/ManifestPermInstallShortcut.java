package core.fx.manifestbased.permission;

import core.fx.base.Feature;
import core.fx.base.ManifestFeatureExtractor;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class ManifestPermInstallShortcut implements ManifestFeatureExtractor<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        return new Feature<>(getName(), target.getPermissions().contains("com.android.launcher.permission.INSTALL_SHORTCUT"));
    }
}
