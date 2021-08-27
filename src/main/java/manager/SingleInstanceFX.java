package manager;

import core.fx.base.FeatureExtractionUnit;
import core.rm.AbstractFeatureSet;

import java.util.List;
import java.util.Set;

public interface SingleInstanceFX<S extends AbstractFeatureSet, E extends FeatureExtractionUnit> {

    S getFeatures(Set<E> featureExtractors);

    S getAllFeatures();

    S getAllFeaturesExclude(Set<String> exclusion);

    S getFeatures(List<String> featureExtractors);

}