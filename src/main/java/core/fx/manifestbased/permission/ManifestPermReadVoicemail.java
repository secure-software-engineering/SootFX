package core.fx.manifestbased.permission;

import core.fx.base.Feature;
import core.fx.base.ManifestFEU;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

public class ManifestPermReadVoicemail implements ManifestFEU<Boolean> {

    @Override
    public Feature<Boolean> extract(ProcessManifest target) {
        return new Feature<>(getName(), target.getPermissions().contains("com.android.voicemail.permission.READ_VOICEMAIL"));
    }
}
