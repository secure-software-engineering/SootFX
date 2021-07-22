package core.rm;


public class ManifestFeatureSet extends AbstractFeatureSet {

    @Override
    public String getSignature() {
        return this.getClass().getSimpleName();
    }
}
