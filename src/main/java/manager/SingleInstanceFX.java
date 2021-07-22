package manager;

import core.fx.base.FeatureExtractor;
import core.rm.AbstractFeatureSet;

import java.util.Set;

public interface SingleInstanceFX<S extends AbstractFeatureSet, E extends FeatureExtractor> {

    S getFeatures(Set<E> featureExtractors);

}