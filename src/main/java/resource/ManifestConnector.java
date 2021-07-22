package resource;

import org.xmlpull.v1.XmlPullParserException;
import soot.jimple.infoflow.android.manifest.ProcessManifest;

import java.io.IOException;

public class ManifestConnector {

    public static ProcessManifest getManifest(String apkPath) {
        ProcessManifest manifest = null;
        try {
            manifest = new ProcessManifest(apkPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return manifest;
    }
}
