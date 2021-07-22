package manager;

import core.fx.base.FeatureExtractor;
import core.rm.AbstractFeatureSet;

import java.util.Set;

public interface MultiInstanceFX<S extends AbstractFeatureSet, E extends FeatureExtractor> {

    Set<S> getFeatures(Set<E> featureExtractors);

}
