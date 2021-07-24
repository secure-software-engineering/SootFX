package manager;

import core.fx.base.FeatureExtractor;
import core.rm.AbstractFeatureSet;
import core.rm.MethodFeatureSet;

import java.util.List;
import java.util.Set;

public interface MultiInstanceFX<S extends AbstractFeatureSet, E extends FeatureExtractor> {

    Set<S> getFeatures(Set<E> featureExtractors);

    Set<S> getAllFeatures();

    Set<S> getAllFeaturesExclude(Set<String> exclusion);

    Set<S> getFeatures(List<String> featureExtractors);

}
