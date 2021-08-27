package manager;

import core.fx.base.FeatureExtractionUnit;
import core.rm.AbstractFeatureSet;

import java.util.List;
import java.util.Set;

public interface MultiInstanceFX<S extends AbstractFeatureSet, E extends FeatureExtractionUnit> {

    Set<S> getFeatures(Set<E> featureExtractors);

    Set<S> getAllFeatures();

    Set<S> getAllFeaturesExclude(Set<String> exclusion);

    Set<S> getFeatures(List<String> featureExtractors);

}
